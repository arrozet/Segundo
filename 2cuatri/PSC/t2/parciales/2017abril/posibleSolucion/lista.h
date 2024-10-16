/*
 * lista.h
 *
 *  Created on: 17 abr. 2017
 *      Author: Manuel F. Bertoa
 */
#include <stdio.h>

#ifndef LISTA_H_
#define LISTA_H_

#define TAM_PAL 30

typedef struct item * Lista;

struct item {
	char palabra[TAM_PAL];
	Lista sig;
};

/*
 * Crea un a lista vacia
 */
void crear(Lista *l);

/*
 * Compruba si un a lista esta vacía
 * Devuelve 0 si NO lo está
 */
int lista_vacia(Lista l);

/*
 * Escribe en consola el contenido de una lista de palabras separadas por coma
 * l: lista enlazada de palabras
 */
void escribir(Lista l);

/*
 * Escribe en un fichero de salida el contenido de una lista de palabras separadas por coma
 * fp: Puntero a un objeto FILE que identifica el stream de salida
 * l: lista enlazada de palabras
 */
void escribir_fichero(FILE * fp, Lista l);

/*
 * Inserta una palabra al final de una lista enlazada.
 * No comprueba si la palabra existe, si se desea no repetir palabras
 * se debe utilizar buscar_palabra() y comprobar antes de invocar esta función
 * palabra: la palabra que se desea insertar
 * l: lista enlazada de palabras
 */
void insertar(char* palabra, Lista* l);

/*
 * Elimina todos los items de la lista enlazada
 * Debe delvolver la memoria dinamica utilizada para cada uno de ellos
 * Para comprobar que se eliminan los items
 * escriba un mensaje por consola indicando la palabra de item que se va a eliminar
 * l: La lista enlazada que se desea eliminar
 */
void destruir(Lista* l);

/*
 * Comprueba si una palabra esta en la lista enlazada
 * palabra: la palabra que se desea buscar
 * l: lista enlazada de palabras
 * Devuelve 0 si la palabra NO está en la lista
 * Devuelve 1 si la palabra Sí está en la lista
 */
int buscar_palabra(char* palabra, Lista l);

#endif /* LISTA_H_ */
