"""Ejercicio 16:
Escribe un programa que genere una matriz bidimensional con 6 filas y 7 columnas aleatoriamente de acuerdo a la
distribución uniforme, y a continuación ponga a cero las dos primeras columnas y a uno las tres últimas columnas.
"""
import numpy as np

print("""Ejercicio 16:
Escribe un programa que genere una matriz bidimensional con 6 filas y 7 columnas aleatoriamente de acuerdo a la
distribución uniforme, y a continuación ponga a cero las dos primeras columnas y a uno las tres últimas columnas.
""")

#Array
x = np.random.uniform(size=(6,7))
print(x)
print()
x[:,[0,1]] = 0
x[:,[-1,-2,-3]] = 1
print(x)


