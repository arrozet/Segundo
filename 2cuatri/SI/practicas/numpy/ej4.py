"""Ejercicio 4:
Escribe un programa que invierte el orden de las filas de un array bidimensional.
"""
import numpy as np

def invertir(a):
    a = a[::-1]
    return a

print("""Ejercicio 4:
Escribe un programa que invierte el orden de las filas de un array bidimensional.
""")
x = np.arange(20).reshape(5,4)
print(x)
print()
x = invertir(x)
print(x)