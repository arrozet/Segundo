"""Ejercicio 6:
Escribe un programa que redimensione un array unidimensional a un array bidimensional con 4 filas y 3 columnas.
"""
import numpy as np

print("""Ejercicio 6:
Escribe un programa que redimensione un array unidimensional a un array bidimensional con 4 filas y 3 columnas.
""")

x = np.arange(12)
print(x)
print()
if len(x) % 4 == 0 and len(x) % 3 == 0:
    print(x.reshape(4, 3))
else:
    print("No se pudo redimensionar a una matriz de 4x3")
