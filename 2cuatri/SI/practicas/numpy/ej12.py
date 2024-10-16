"""Ejercicio 12:
Escribe un programa que, dado un array bidimensional, halle los índices (filas y columnas) de los elementos mínimo y máximo del array.
"""
import numpy as np

print("""Ejercicio 12:
Escribe un programa que, dado un array bidimensional, halle los índices (filas y columnas) de los elementos mínimo y máximo del array.
""")

#Array
x = np.array([1,7,221,22,-12,23,2,33,33,33,33,12]).reshape(4,3)
print(x)

# unravel index sirve para parsear índices unidimensionales a "multidimensionales" (a tipo forma del array)
print("Índice donde está el elemento mínimo:", np.unravel_index(np.argmin(x), x.shape), "\nÍndice donde está el elemento máximo: ",
      np.unravel_index(np.argmax(x), x.shape))


