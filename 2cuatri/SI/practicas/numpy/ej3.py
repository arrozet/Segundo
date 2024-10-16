"""Ejercicio 3:
Escribe un programa que invierte el orden de los elementos de un array unidimensional.
"""
import numpy as np

def invertir(a):
    a = a[::-1]
    return a

print("""Ejercicio 3:
Escribe un programa que invierte el orden de los elementos de un array unidimensional.
""")
x = np.arange(20)
print(x)
x = invertir(x)
print(x)