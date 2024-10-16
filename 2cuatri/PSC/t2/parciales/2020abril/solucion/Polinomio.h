/*
 * Polinomio.h
 *
 */
#ifndef _POLINOMIO_H_
#define _POLINOMIO_H_

typedef struct TMonomio *TPolinomio;
struct TMonomio{
	unsigned int coef;
	unsigned int exp;
	TPolinomio sig;
};

//Parte 1. PARA APROBAR

/*Crea el polinomio 0 (es decir, un polinomio vacío).*/
void polinomioCero(TPolinomio *p);

/*Devuelve el grado del polinomio, es decir, el mayor exponente de los
monomios que no son nulos. En el ejemplo, el grado es 7.
El grado del polinomio cero es 0.*/
unsigned int grado(TPolinomio p);

/* Devuelve el coeficiente del exponente exp del polinomio p.*/
unsigned int coeficiente(TPolinomio p, unsigned int exp);

/* Insertar el monomio con coeficiente coef, y exponente exp en el polinomio,
 * de manera que el polinomio quede ordenado. Asegurarse que no se insertan
 * monomios cuyo coeficiente sea 0 y tampoco dos monomios con el mismo exponente.
 * Si al insertar un monomio ya hay otro con el mismo exponente los coeficientes
 * se sumarán. Se puede asumir que el valor del coeficiente siempre será un numero
 * natural (es decir será >=0).*/
void insertar(TPolinomio *p, unsigned int coef, unsigned int exp);

/*Escribe por la pantalla el polinomio con un formato similar al siguiente:
 * [(3,7)(0,6)(2,5)(0,4)(3,3)(0,2)(5,1)(9,0)] para el polinomio ejemplo.
 * Ten en cuenta que los monomios de exponente menor al grado del polinomio
 * con coeficiente 0 también aparecen en la salida, aunque no estén almacenados
 * en el polinomio. */
void imprimir(TPolinomio p);

/* Elimina todos los monomios del polinomio haciendo que el polinomio resultante
 * sea el polinomio 0.*/
void destruir(TPolinomio *p);

//Parte 2. Notable
/* Lee los datos de un polinomio de un fichero de texto, y
 * crea la lista de monomios p. El formato del polinomio en el fichero contiene
 * una secuencia de pares de dígitos correspondientes al coeficiente y exponente
 * de cada monomio del polinomio, incluyendo los que tienen coeficiente nulo.
 * En ambos casos, suponemos que los coeficientes y exponentes son dígitos del 0 al 9
 * (no hay números superiores).
 * Por ejemplo, para el polinomio de ejemplo el fichero de texto estaría compuesto
 * por la secuencia de caracteres “0690332551370402”.
 * Observa que los monomios pueden venir desordenados en el fichero de entrada.
 *
 * La conversión de un valor de tipo ‘char’ que contenga
 * un valor númerico (ej. char c = ‘2’)
 * a su correspondiente valor entero (int valor),
 * se puede hacer de la siguiente forma: valor = c – ‘0’
*/
void crearDeFichero(TPolinomio *p, char *nombre);

//Parte 3. Sobresaliente
/* Evalúa el polinomio para el valor x y devuelve el resultado.
 * Para la evaluación del polinomio debes utilizar el método de Horner,
 * de manera que ax^4 + bx^3+ cx^2+dx+e puede evaluarse
 * en un valor cualquiera x teniendo en cuenta que es equivalente
 * a: (((ax+b)x+c)x+d)x+e.
*/
int evaluar(TPolinomio p,int x);

#endif /* _POLINOMIO_H_ */
