"""Ejercicio 5:
Escribe un programa que, dada una lista de tuplas, calcula una nueva lista que
contenga las sumas de los elementos de dichas tuplas y que esté ordenada en orden
decreciente"""


def ej5(lista):
    new_list = []
    for i in range(0, len(lista)):
        new_list.append(sum(lista[i]))

    new_list.sort(reverse=True)
    print(new_list)


if __name__ == '__main__':
    ejemplo = [(1, 4), (2, 3), (5, 1), (3, 2), (7, 5, 1)]
    ej5(ejemplo)
