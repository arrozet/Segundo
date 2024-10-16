"""Ejercicio 14:
Escribe un programa que genere una matriz bidimensional con 7 filas y 5 columnas aleatoriamente de acuerdo a la
distribución normal, y a continuación ponga a cero todos los elementos negativos.
"""
import numpy as np

print("""Ejercicio 14: 
Escribe un programa que genere una matriz bidimensional con 7 filas y 5 columnas aleatoriamente de acuerdo a la 
distribución normal, y a continuación ponga a cero todos los elementos negativos.
""")

#Array
x = np.random.normal(size=(7,5))
print(x)
print()
# pongo a 0 los que sean <0
x[x<0]=0
print(x)


