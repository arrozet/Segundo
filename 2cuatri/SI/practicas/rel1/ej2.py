"""Ejercicio 2:
Escriba un programa que lea por teclado los siguientes datos:
• El nombre de un producto
• El precio del producto por unidad (puede contener decimales)
• El número de unidades de producto (es un número entero).
A con�nuación, debe imprimir por pantalla el siguiente texto:
<nombre_producto>: <unidades_producto> unidades x <precio_unitario>€ = <total>€
siendo <unidades_producto> imprimido con cinco dígitos, <precio_unitario> el precio
por unidad con 6 dígitos enteros y 2 decimales y <total> es el coste total con 8 dígitos
enteros y 2 decimales."""


def ej2():
    nombre_producto = input("Dame una nombre de producto: ")
    precio_por_unidad = float(input("Dame el precio del producto por unidad (puede contener decimales): "))
    numero_unidades = int(input("Dame el numero de unidades del producto (es un entero): "))

    print("%s:%5d unidades x %6.2f€ = %8.2f€" % (nombre_producto, numero_unidades, precio_por_unidad, precio_por_unidad*numero_unidades))


if __name__ == '__main__':
    ej2()
