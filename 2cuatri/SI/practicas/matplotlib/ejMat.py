import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

df = pd.read_csv(r'D:\!UMA\!!UMA_CODE\2\2cuatri\SI\practicas\pandas\bmw.csv')

def ej1():
    print("""Ejercicio 1:
Representa la función f(x) = sin(x)+cos(x) en el intervalo [0,2*pi].
""")
    x = np.linspace(0, 2 * np.pi, 100)  # Genera 100 puntos equidistantes en el intervalo [0, 2*pi]

    # Calcular los valores de la función f(x) = sin(x) + cos(x)
    y = np.sin(x) + np.cos(x)

    # Trazar la función
    plt.figure(figsize=(15, 8)) # para que al hacer savefig se vea todo correctamente
    plt.plot(x, y, label='f(x) = sin(x) + cos(x)')
    plt.xlabel('x')
    plt.ylabel('f(x)')
    plt.title('Gráfica de f(x) = sin(x) + cos(x)')
    plt.legend()
    plt.grid(True)

    plt.savefig('ej1.png', dpi=1000)
    #plt.show()
    plt.clf()   # para que no se solapen cuando las ejecuto todas (clean figure)


def ej2():
    print("""Ejercicio 2:
Representa las funciones f(x) = sin(x), g(x)=log(1+x) en el intervalo [0,2*pi], tomando 100 puntos igualmente espaciados en dicho intervalo.
""")
    intervalo = np.linspace(0,2*np.pi, 100)
    f = np.sin(intervalo)
    g = np.log(1+intervalo)

    plt.figure(figsize=(15, 8))
    plt.plot(intervalo,f,label="f(x)=sin(x)")
    plt.plot(intervalo,g,label="g(x)=log(1+x)")
    plt.xlabel("x")
    plt.ylabel("y")
    plt.title("Gráfica de f(x)=sin(x) y g(x)=log(1+x)")
    plt.legend()
    plt.grid(True)

    plt.savefig('ej2.png', dpi=1000)
    #plt.show()
    plt.clf()


def ej3():
    print("""Ejercicio 3:
Crea un gráfico de barras que muestre las frecuencias de los distintos valores del atributo model de la base de datos.
""")

    # esto no calcula por si solo las frecuencias, como el histograma
    serie = df["model"].value_counts()  # serie pandas con pares (modelo,frecuencia)

    plt.figure(figsize=(15, 8))
    plt.bar(serie.index, serie.values,color='purple', edgecolor='black')
    plt.ylabel("Frecuencia")
    plt.xlabel("Modelo")
    plt.title("Frecuencia de cada modelo de BMW")
    plt.xticks(rotation=90)


    plt.savefig('ej3.png', dpi=1000)
    #plt.show()
    plt.clf()


def ej4():
    print("""Ejercicio 4:
Crea un gráfico de dispersión que muestre los precios (atributo “price”) en el eje horizontal, los kilometrajes (atributo “mileage”) en el eje vertical, y las eficiencias (atributo “mpg”) como colores.
""")
    x = df["price"]
    y = df["mileage"]
    eficiencia = df["mpg"]

    plt.figure(figsize=(15, 8))
    plt.scatter(x,y,c=eficiencia)
    plt.colorbar(label="Eficiencia")
    plt.title("Gráfico de dispersión")
    plt.xlabel("Precio")
    plt.ylabel("Kilometrajes")
    plt.grid(True)


    plt.savefig('ej4.png', dpi=1000)
    #plt.show()
    plt.clf()


def ej5():
    print("""Ejercicio 5:
Crea un histograma que muestre los precios (atributo “price”) de la base de datos.
""")
    price = df["price"]

    plt.figure(figsize=(15, 8))
    # el histograma calcula las frecuencias sin necesidad de metodos auxiliares, como en el diagrama de barras
    plt.hist(price, bins=30, color='purple', edgecolor='black')
    plt.xlabel("Precios")
    plt.ylabel("Frecuencia")
    plt.title("Histograma de precios en BMW")


    plt.savefig('ej5.png', dpi=1000)
    #plt.show()
    plt.clf()


#ESTO ES DEL EJ6
MAXIMO_ITERACIONES = 80
def mandelbrot(a,b):
    c = complex(a,b)
    z = 0
    n = 0
    while abs(z) <= 2 and n < MAXIMO_ITERACIONES:
        z = z*z + c
        n += 1
    color_pixel = 255 - int(n * 255 / MAXIMO_ITERACIONES)
    return color_pixel


def ej6():
    print("""Ejercicio 6:
Considera la siguiente función, que calcula valores enteros entre 0 y 255 para dibujar el fractal de Mandelbrot, 
siendo a y b números en coma flotante que representan la parte real y la parte imaginaria de un número complejo, 
respectivamente:

Utilizando el código anterior, dibuja un mapa de calor que represente una parte del fractal de Mandelbrot. 
El mapa debe tener 600 píxeles de ancho por 400 píxeles de alto, de manera que se consideren valores de la parte real 
entre -2 y 1, mientras que los valores de la parte imaginaria deben estar entre -1 y 1.""")
    ancho = 600
    alto = 400
    min_real, max_real = -2, 1
    min_imag, max_imag = -1, 1

    real = np.linspace(min_real, max_real, ancho)
    imaginario = np.linspace(min_imag, max_imag, alto)
    x, y = np.meshgrid(real, imaginario)

    mandelbrot_vec = np.vectorize(mandelbrot)
    color_pixel = mandelbrot_vec(x, y)

    plt.figure(figsize=(15, 8))
    plt.imshow(color_pixel, cmap='hot', extent=[min_real, max_real, min_imag, max_imag])
    plt.colorbar()
    plt.xlabel("Parte real")
    plt.ylabel("Parte imaginaria")
    plt.title("Mapa de calor que representa una parte del fractal de Mandelbrot")


    plt.savefig('ej6.png', dpi=1000)
    #plt.show()
    plt.clf()


ej1()
ej2()
ej3()
ej4()
ej5()
ej6()

