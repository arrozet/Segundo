"""Ejercicio 9:
Escribe un programa que, dado un array unidimensional, halle el número de veces que aparece cada uno de sus elementos únicos.
"""
import numpy as np

print("""Ejercicio 9:
Escribe un programa que, dado un array unidimensional, halle el número de veces que aparece cada uno de sus elementos únicos.
""")

x = np.array([1,1,1,2,4,12,12,12,7,12])
print(x)
print()
elementos_unicos, repes = np.unique(x, return_counts=True)
print("Los elementos ", elementos_unicos, " aparecen las siguientes veces (en orden): ", repes)
