/*
 * Lista.h
 *
 *  Created on: 16 mar. 2020
 *      Author: mmar
 */

#ifndef LISTA_H_
#define LISTA_H_

typedef struct Nodo * Lista;

struct Nodo{
	int valor;
	Lista sig;
};

void crearDeFichero(Lista *l,char* nombre);
void crear(Lista * l);//l es un puntero a un puntero
int listaVacia(Lista l);
void mostrar(Lista l);
int buscar(Lista l,int v);
void insertarOrdenado(Lista *l, int v);
void eliminar(Lista *l, int v);

#endif /* LISTA_H_ */
