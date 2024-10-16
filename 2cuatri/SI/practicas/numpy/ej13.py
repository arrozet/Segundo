"""Ejercicio 13:
Escribe un programa que ordene las filas de un array bidimensional según los valores de la primera columna.
"""
import numpy as np

print("""Ejercicio 13: 
Escribe un programa que ordene las filas de un array bidimensional según los valores de la primera columna.
""")

#Array
x = np.array([1,7,221,22,-12,23,2,33,33,33,33,12]).reshape(4,3)
print(x)
print()
# saco los indices ordenados de la primera columna
indices = np.argsort(x[:,0])
print(indices, "\n")
# con esto se entiende que se debe ordenar las filas con ese criterio
print(x[indices,:])


