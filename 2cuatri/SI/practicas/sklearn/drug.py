import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

from sklearn.model_selection import train_test_split, GridSearchCV, KFold, cross_val_predict
from sklearn.tree import DecisionTreeClassifier, plot_tree
from sklearn.neural_network import MLPClassifier
from sklearn.metrics import roc_auc_score, roc_curve, recall_score, precision_score, f1_score, accuracy_score
from sklearn.preprocessing import StandardScaler


# Defino los nombres de las columnas basándome en la descripción de los datos
column_names = ['id', 'age', 'gender', 'education', 'country', 'ethnicity', 'nscore', 'escore', 'oscore', 'ascore',
                'cscore', 'impulsive', 'ss',     # end of features
                'alcohol', 'amphet', 'amyl', 'benzos', 'caff', 'cannabis', 'choc', 'coke', 'crack', 'ecstasy', 'heroin',
                'ketamine', 'legalh', 'lsd', 'meth', 'mushrooms', 'nicotine', 'semer', 'vsa']   # end of targets

# Separo los nombres de características y objetivos
features_name = ['age', 'gender', 'education', 'country', 'ethnicity', 'nscore', 'escore', 'oscore', 'ascore',
                'cscore', 'impulsive', 'ss']
targets_name = ['alcohol', 'amphet', 'amyl', 'benzos', 'caff', 'cannabis', 'choc', 'coke', 'crack', 'ecstasy', 'heroin',
                'ketamine', 'legalh', 'lsd', 'meth', 'mushrooms', 'nicotine', 'semer', 'vsa']

# Cargo los datos
df = pd.read_csv(r'D:\!UMA\!!UMA_CODE\2\2cuatri\SI\practicas\sklearn\drug_consumption.data', names=column_names)

# Elimino la columna 'id' ya que no es útil para mi análisis
df = df.drop(columns=['id'])

# Como tengo muchos targets, me voy a quedar solo con uno. En este caso, será cocaína:
targets_to_elim = targets_name.copy()
targets_to_elim.remove('coke')
df = df.drop(columns=targets_to_elim)

# Creo un diccionario de mapeo para la columna 'coke' en el que consideraré que son cocainómanos si han usado en el
# último año o después. Si ha usado por última vez en la última decada o antes, consideraré que no es cocainómano

# Convierto la variable 'coke' a un formato binario para simplificar la clasificación
# Cocainómano: CL3, CL4, CL5, CL6 -> 0
# No cocainómano: CL0, CL1, CL2 -> 1
coke_mapping = {'CL0': 1, 'CL1': 1, 'CL2': 1, 'CL3': 0, 'CL4': 0, 'CL5': 0, 'CL6': 0}
df['coke'] = df['coke'].replace(coke_mapping).infer_objects()

# Separo características y target
features = df[features_name]
target = df['coke']

# Imprimo las características y el target para verificar los datos
print("Features:")
print(features)
print("\nTarget:")
print(target)

#####################################################################################################
print("\n-----------DECISION TREE-----------")
# Dividir los datos en conjuntos de entrenamiento y prueba
# cross_val_predict ya hace la división en train/test

# Definir el modelo de árbol de decisión
dt = DecisionTreeClassifier()

# Definir los hiperparámetros para la búsqueda en grid
param_grid_tree = {
    'criterion': ['gini', 'entropy'],
    'max_depth': [None, 10, 20, 30, 40, 50],
    'min_samples_split': [2, 5, 10],
    'min_samples_leaf': [1, 2, 4],
    'class_weight': ['balanced']    # Ajusto el peso de las clases ya que hay menos cocainómanos
}

# Realizar la búsqueda de hiperparámetros con validación cruzada, optimizando el recall
grid_search_tree = GridSearchCV(estimator=dt, param_grid=param_grid_tree, cv=10, n_jobs=-1, scoring='recall')

# Hacer la búsqueda de los mejores hiperparámetros
grid_search_tree.fit(features, target)

# Obtener los mejores hiperparámetros
best_params = grid_search_tree.best_params_
print("Mejores hiperparámetros para el árbol de decisión: ", best_params)

# Guardar la mejor estimación
best_dt = grid_search_tree.best_estimator_
print("Mejor estimación para el árbol de decisión: ", best_dt)

# Graficar el árbol de decisión
plt.figure(figsize=(20, 10))
plot_tree(best_dt, feature_names=features_name, class_names=[str(i) for i in range(7)], filled=True)
plt.savefig("dt_plot.png")

#####################################################################################################
print("\n-----------MULTILAYER PERCEPTRON-----------")
# Normalizar las características
scaler = StandardScaler()
features_scaled = scaler.fit_transform(features)

# Dividir los datos en test y train
# cross_val_predict ya hace la división en train/test

"""
# Definir el clasificador MLP
mlp = MLPClassifier(max_iter=2000, early_stopping=True, n_iter_no_change=20)

# Definir hiperparámetros para búsqueda en grid
param_grid_mlp = {
    'hidden_layer_sizes': [(50,), (100,), (50, 50), (100, 50)],
    'activation': ['relu', 'tanh', 'logistic'],
    'solver': ['adam', 'sgd', 'lbfgs'],
    'alpha': [0.0001, 0.001, 0.01],
    'learning_rate': ['constant', 'adaptive'],
    'learning_rate_init': [0.001, 0.01, 0.1]  # Added different initial learning rates
    #'max_iter': [200, 300, 400]
}

# Encontrar mejoras hiperparámetros para recall
grid_search_mlp = GridSearchCV(estimator=mlp, param_grid=param_grid_mlp, cv=3, n_jobs=-1, scoring='recall')
"""
# Definir el clasificador MLP
mlp = MLPClassifier(max_iter=500, early_stopping=True, n_iter_no_change=20)

# Definir hiperparámetros para búsqueda en grid
param_grid_mlp = {
    'hidden_layer_sizes': [(10,), (20,)],
    'activation': ['relu', 'tanh'],
    'solver': ['adam', 'sgd'],
    'alpha': [0.0001, 0.001],
    'learning_rate': ['constant', 'adaptive'],
    'learning_rate_init': [0.001, 0.01]
}

# Encontrar mejoras hiperparámetros para recall
grid_search_mlp = GridSearchCV(estimator=mlp, param_grid=param_grid_mlp, cv=10, n_jobs=-1, scoring='recall')

# Hacer la búsqueda de los mejores hiperparámetros
grid_search_mlp.fit(features, target)

# Conseguir los mejores hiperparámetros
best_params = grid_search_mlp.best_params_
print("Mejores hiperparámetros para el perceptrón: ", best_params)

# Guardar la mejor estimación
best_mlp = grid_search_mlp.best_estimator_
print("Mejor estimación para el perceptrón: ", best_mlp)

#####################################################################################################
print("\n++++++++++++++PERFORMANCE COMPARISON++++++++++++++")

# Evaluar ambos modelos con validación cruzada
kf = KFold(n_splits=10, shuffle=True, random_state=42)

# Para el árbol de decisión
# Se usa 1 pliegue para test y k-1 pliegues para train
y_pred_dt = cross_val_predict(best_dt, features, target, cv=kf, method='predict_proba')[:, 1]
roc_auc_dt = roc_auc_score(target, y_pred_dt)
fpr_dt, tpr_dt, _ = roc_curve(target, y_pred_dt)
recall_dt = recall_score(target, y_pred_dt.round())
precision_dt = precision_score(target, y_pred_dt.round())
f1_dt = f1_score(target, y_pred_dt.round())
accuracy_dt = accuracy_score(target, y_pred_dt.round())

# Para el MLP
# Se usa 1 pliegue para test y k-1 pliegues para train
y_pred_mlp = cross_val_predict(best_mlp, features_scaled, target, cv=kf, method='predict_proba')[:, 1]
roc_auc_mlp = roc_auc_score(target, y_pred_mlp)
fpr_mlp, tpr_mlp, _ = roc_curve(target, y_pred_mlp)
recall_mlp = recall_score(target, y_pred_mlp.round())
precision_mlp = precision_score(target, y_pred_mlp.round())
f1_mlp = f1_score(target, y_pred_mlp.round())
accuracy_mlp = accuracy_score(target, y_pred_mlp.round())

# Imprimir las métricas de rendimiento
print("\n-----------DECISION TREE PERFORMANCE-----------")
print(f"ROC AUC: {roc_auc_dt:.2f}")
print(f"Recall: {recall_dt:.2f}")
print(f"Precision: {precision_dt:.2f}")
print(f"F1 Score: {f1_dt:.2f}")
print(f"Accuracy: {accuracy_dt:.2f}")

print("\n-----------MULTILAYER PERCEPTRON PERFORMANCE-----------")
print(f"ROC AUC: {roc_auc_mlp:.2f}")
print(f"Recall: {recall_mlp:.2f}")
print(f"Precision: {precision_mlp:.2f}")
print(f"F1 Score: {f1_mlp:.2f}")
print(f"Accuracy: {accuracy_mlp:.2f}")

# Dibujar curva ROC
plt.figure(figsize=(12, 6))
plt.plot(fpr_dt, tpr_dt, label=f'Decision Tree (AUC = {roc_auc_dt:.2f})')
plt.plot(fpr_mlp, tpr_mlp, label=f'MLP (AUC = {roc_auc_mlp:.2f})')
plt.plot([0, 1], [0, 1], 'k--')
plt.xlim([0.0, 1.0])
plt.ylim([0.0, 1.05])
plt.xlabel('False Positive Rate')
plt.ylabel('True Positive Rate')
plt.title('Receiver Operating Characteristic')
plt.legend(loc="lower right")
plt.savefig('roc_curve.png')


