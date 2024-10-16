"""Ejercicio 15:
Escribe un programa que, dado un array unidimensional y un número entero positivo k, halle los índices de los k
mayores valores del array.
"""
import numpy as np

print("""Ejercicio 15:
Escribe un programa que, dado un array unidimensional y un número entero positivo k, halle los índices de los k
mayores valores del array.
""")

#Array
x = np.array([1,7,221,22,-12,23,2,33,33,33,33,12])
k = 4
print(x)
print()
indices = np.argsort(x) # ordeno indices de menor a mayor
#print(indices)
k_mayores_indices = indices[-k:]    # saco los k ultimos indices (los k indices de los mayores valores del array)
print(k_mayores_indices)


