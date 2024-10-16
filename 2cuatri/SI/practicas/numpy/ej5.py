"""Ejercicio 5:
Escribe un programa que calcule la media de los elementos de cada columna de un array bidimensional.
"""
import numpy as np

print("""Ejercicio 5:
Escribe un programa que calcule la media de los elementos de cada columna de un array bidimensional.
""")

x = np.arange(20).reshape(5,4)
print(x)
print()
print(np.mean(x, axis=0))