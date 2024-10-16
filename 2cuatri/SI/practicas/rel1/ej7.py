"""Ejercicio 7:
Escribe un programa que, dado un diccionario y un valor, cree un nuevo diccionario con
todos los ítems del diccionario de entrada salvo los que contengan ese valor."""


def ej7(diccionario, valor_eliminar):
    new_dic = diccionario.copy()

    for clave in diccionario.keys():
        if diccionario[clave] == valor_eliminar:
            del new_dic[clave]

    print(new_dic)


if __name__ == '__main__':
    dic = {'a': 1, 'b': 2, 'c': 2, 'd': 3, 'e': 2}
    valor = 2
    ej7(dic, valor)
