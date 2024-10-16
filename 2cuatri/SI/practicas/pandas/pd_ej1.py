import pandas as pd
import numpy as np
df = pd.read_csv(r'D:\!UMA\!!UMA_CODE\2\2cuatri\SI\practicas\pandas\bmw.csv')

def ej1():
    print("""\nEjercicio 1:
Muestre los primeros 10 registros de la base de datos.
""")
    print(df[:10])


def ej2():
    print("""\nEjercicio 2:
Obtenga la serie correspondiente al atributo year, y a continuación obtenga el tipo de datos y el número de 
registros de dicha serie.
    """)
    serie = df["year"]
    print("Serie year: ", serie, "\n\nAunque ya sale al imprimir la serie como tal, lo pongo"
                                 "aparte también\nTamaño: ", serie.size, " Tipo: ", serie.dtype)


def ej3():
    print("""\nEjercicio 3:
Obtenga la serie correspondiente al atributo mileage, y después seleccione los registros de posición múltiplo de 7 
en dicha serie.
        """)
    serie = df["mileage"]
    print(serie[serie.index % 7 == 0])


def ej4():
    print("""\nEjercicio 4:
Obtenga la serie correspondiente al atributo mileage, y después seleccione aleatoriamente el 40% de los registros de dicha serie.
""")
    serie = df["mileage"]
    print(serie[np.random.uniform(size=serie.size) <= 0.4]) # aprox un 40% estara por debajo de 0.4, ya que los valores se
    # distribuyen entre 0 y 1
    # otra opción sería hacerlo con serie.sample(frac=0.4), que coge una muestra aleatoria con un 40% de los elementos de la serie

def ej5():
    print("""\nEjercicio 5:
Obtenga la serie correspondiente al atributo mileage, y después seleccione los registros de dicha serie con valor menor que 20000.
""")
    serie = df["mileage"]
    print(serie[serie<20000])


def ej6():
    print("""\nEjercicio 6:
Obtenga la serie correspondiente al atributo mpg, y después ordene los registros de dicha serie.
    """)
    serie = df["mpg"]
    print(serie.sort_values())

def ej7():
    print("""\nEjercicio 7:
Calcule la media, la desviación típica, el mínimo y el máximo del atributo engineSize.
""")
    serie = df["engineSize"]
    print("Media: %f, Desv. típica: %f, Mínimo: %f, Máximo: %f" % (serie.mean(), serie.std(), serie.min(), serie.max()))

def ej8():
    print("""\nEjercicio 8:
Obtenga el número de filas y columnas de la base de datos, así como el antepenúltimo registro.
""")
    print("Filas: ", df.shape[0], " Columnas: ", df.shape[1], " Antepenúltimo registro: ", df.iloc[-3])


def ej9():
    print("""\nEjercicio 9:
Obtenga los atributos mileage, price y mpg en un nuevo DataFrame, y después seleccione aleatoriamente el 20% de los registros.
""")
    new_df = df[["mileage", "price", "mpg"]]
    print(new_df.sample(frac=0.2))   # un registro es una fila


def ej10():
    print("""\nEjercicio 10:
Obtenga los registros que tengan un valor de mileage inferior a 10000 y un valor de mpg mayor que 40.
""")
    print(df[ (df["mileage"]<10000) & (df["mpg"]>40) ])


def ej11():
    print("""\nEjercicio 11:
Modifique los valores del atributo model, de tal manera que los valores " x Series" pasen a ser "Serie x", siendo x un número entre 1 y 9.
""")
    def modificar_modelo(modelo):
        if ' Series' in modelo:
            numero = modelo.split()[0]
            return "Serie {}".format(numero)
        return modelo

    # No lo he modificado como tal, ya que no he tocado el csv. Simplemente he hecho una "máscara" para ver como
    # quedaria y poder printearlo
    print("ANTES")
    print(df)
    df_copy = df.copy()
    df_copy["model"] = df_copy["model"].apply(modificar_modelo)    # aplicar funcion a los datos de df["model"]
    print("\nDESPUÉS")
    print(df_copy)

def ej12():
    print("""\nEjercicio 12:
Inserte un nuevo registro con los siguientes datos: model=" 3 Series", year=2023, price = 22572, 
transmission = "Automatic", mileage = 74120, fuelType = "Diesel", tax = 160, mpg = 58.4, engineSize = 2.0
    """)

    nuevo_registro = {
        "model": "3 Series",
        "year": 2023,
        "price": 22572,
        "transmission": "Automatic",
        "mileage": 74120,
        "fuelType": "Diesel",
        "tax": 160,
        "mpg": 58.4,
        "engineSize": 2.0
    }
    print(df)

    # append no funciona (por algún motivo no está en el módulo pandas), así que he optado por esto que sí funciona
    """
    df.loc[-1] = nuevo_registro
    df.index = df.index + 1


    print(df.sort_index())
    """
    # el código comentado es una solucion anterior. Por algún motivo, la función _append sí funciona bien (y hace lo mismo
    # que el código comentado)
    print(df._append(nuevo_registro, ignore_index=True))
    """ #Otra opción algo menos extraña es esta, que hace lo mismo
    new_df = pd.DataFrame(nuevo_registro, index=[0])
    print(pd.concat([df,new_df], ignore_index=True))
    """


def ej13():
    print("""\nEjercicio 13:
Convierta el DataFrame en un ndarray de numpy, e imprima el tipo de datos del ndarray obtenido.
""")
    nda = df.values     # esto es un ndarray de numpy (df.values)
    print(type(nda))
    print(nda.dtype)


def ej14():
    print("""\nEjercicio 14:
Calcule para cada registro el número medio de millas recorridas cada año.
""")
    years_lived = pd.Timestamp.now().year - df["year"]  # años que tiene el coche
    miles_per_year = df["mileage"] / years_lived    # millas hechas / años del coche
    print(miles_per_year)


ej1()
ej2()
ej3()
ej4()
ej5()
ej6()
ej7()
ej8()
ej9()
ej10()
ej11()
ej12()
ej13()
ej14()



