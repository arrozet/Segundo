"""Ejercicio 10:
Normaliza un array bidimensional de 4 filas y 3 columnas restando la media y dividiendo por la desviación típica en cada columna.
"""
import numpy as np

print("""Ejercicio 10:
Normaliza un array bidimensional de 4 filas y 3 columnas restando la media y dividiendo por la desviación típica en cada columna.
""")

#Array
x = np.arange(12).reshape(4,3)
print(x)

media_columna = np.mean(x,axis=0)
desviacion_tipica_columna = np.std(x,axis=0)

x = (x - media_columna) / desviacion_tipica_columna
print(x)