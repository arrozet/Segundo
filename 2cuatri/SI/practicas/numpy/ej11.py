"""Ejercicio 11:
Normaliza un array bidimensional de 4 filas y 3 columnas restando la media y dividiendo por la desviación típica en cada fila.
"""
import numpy as np

print("""Ejercicio 11:
Normaliza un array bidimensional de 4 filas y 3 columnas restando la media y dividiendo por la desviación típica en cada fila.
""")

#Array
x = np.arange(12).reshape(4,3)
print(x)

media_fila = np.mean(x,axis=1)
desviacion_tipica_fila = np.std(x,axis=1)

x = (x - np.vstack(media_fila)) / np.vstack(desviacion_tipica_fila)
print(x)