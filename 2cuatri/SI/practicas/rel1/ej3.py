"""Ejercicio 3:
Escriba un programa para imprimir todos los números primos entre a y b, siendo a y b
números enteros introducidos por teclado."""


def ej3():
    a = int(input("Dime el numero primo 1: "))
    b = int(input("Dime el numero primo 2: "))
    print("Los primos entre ", a, " y ", b, "son: ")

    for n in range(a, b+1):  # el extremo superior es exclusivo, no se cuenta, por eso sumo 1, para que cuente b
        primo = True
        i = 2
        while i < n and primo:
            if n % i == 0:
                primo = False
            i += 1

        if primo:
            print(n)


if __name__ == '__main__':
    ej3()
