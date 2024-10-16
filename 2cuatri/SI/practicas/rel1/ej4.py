"""Ejercicio 4:
Escribe un programa que, dada una lista de números enteros, construya una nueva lista
en la que estén los elementos iniciales de la lista original, hasta el primero que sea par."""


def ej4(lista):
    new_lista = []
    par = False
    i = 0
    while i < len(lista) and not par:
        if lista[i] % 2 == 0:
            par = True

        else:
            new_lista.append(lista[i])

        i += 1

    print(new_lista)


if __name__ == '__main__':
    ejemplo = [11, 55, 81, 47, 23, 90, 25, 93, 30, 15]
    ej4(ejemplo)
