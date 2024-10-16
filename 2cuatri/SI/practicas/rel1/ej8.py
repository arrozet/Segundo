"""Ejercicio 8:
Escribe una función para hallar el segundo menor elemento de una lista de números,
sin ordenar completamente la lista."""


def ej8(lista):
    if len(lista) < 2:
        return None
    else:
        menor = segundo_menor = float('inf')
        for n in lista:
            if n < menor:   # si n es el nuevo menor, actualizo valores
                segundo_menor = menor
                menor = n
            elif segundo_menor > n > menor:   # si n es menor que el segundo menor pero no menor que el menor, actualizo segundo_menor
                segundo_menor = n

    return segundo_menor


if __name__ == '__main__':
    example = [3, 6, 4, 7, 9]
    print(ej8(example))
