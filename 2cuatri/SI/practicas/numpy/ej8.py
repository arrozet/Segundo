"""Ejercicio 8:
Escribe un programa que, dado un array bidimensional, halle el máximo de cada fila.
"""
import numpy as np

print("""Ejercicio 8: 
Escribe un programa que, dado un array bidimensional, halle el máximo de cada fila.
""")

x = np.arange(16).reshape(4, 4)
print(x)
print()
print(np.max(x, axis=1))
