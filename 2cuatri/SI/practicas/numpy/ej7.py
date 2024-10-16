"""Ejercicio 7:
Escribe un programa que, dado un array unidimensional, compruebe si se puede redimensionar a un array bidimensional con el mismo número de filas y columnas (matriz cuadrada). En caso afirmativo, debe imprimir la matriz cuadrada. En caso negativo, debe imprimir un mensaje informativo.
"""
import numpy as np
print("""Ejercicio 7:
Escribe un programa que, dado un array unidimensional, compruebe si se puede redimensionar a un array bidimensional con el mismo número de filas y columnas (matriz cuadrada). En caso afirmativo, debe imprimir la matriz cuadrada. En caso negativo, debe imprimir un mensaje informativo.
""")

x = np.arange(16)
print(x)
dimension_matriz = np.sqrt(len(x))
if dimension_matriz == int(dimension_matriz):  # para ver si tiene decimales
    print(x.reshape(int(dimension_matriz), int(dimension_matriz)))

else:
    print("No se puede redimensionar en una matriz cuadrada")