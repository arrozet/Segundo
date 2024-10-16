"""Ejercicio 6:
Escribe un programa en Python que, dadas dos cadenas, imprima por pantalla los
caracteres diferentes que aparecen en la primera cadena pero no en la segunda. Debe
usar conjuntos para desarrollar la solución."""


def ej6(cadena1, cadena2):
    caracteres1 = set()
    caracteres2 = set()
    for c in cadena1:
        caracteres1.add(c)

    for c in cadena2:
        caracteres2.add(c)

    print(caracteres1.difference(caracteres2))


if __name__ == '__main__':
    ejemplo1 = "El que a buen árbol se arrima"
    ejemplo2 = "buena sombra le cobija"
    ej6(ejemplo1, ejemplo2)
