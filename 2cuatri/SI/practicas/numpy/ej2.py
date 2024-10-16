"""Ejercicio 2:
Escribe un programa que cree un array bidimensional de 5 filas y 4 columnas
con los enteros desde 0 hasta 19.
"""
import numpy as np

print("""Ejercicio 2:
Escribe un programa que cree un array bidimensional de 5 filas y 4 columnas
con los enteros desde 0 hasta 19.
""")

x = np.arange(20).reshape(5,4)

print(x)