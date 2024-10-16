"""Ejercicio 1:
Escribe un programa que lea una dirección de correo electrónico del teclado, con el
formato usuario@dominio. A con�nuación, debe imprimir el nombre de usuario en
una línea, y el dominio en otra línea"""


def ej1():
    direccion = input("Dame una dirección de correo: ")
    lista = direccion.split("@")
    usuario = lista[0]
    dominio = lista[1]
    print("usuario: " + usuario + "\ndominio: " + dominio)


if __name__ == '__main__':
    ej1()
