/*
 * Lista.h
 *
 *  Created on: 28/1/2020
 *      Author: usuario
 */

#ifndef LISTA_H_
#define LISTA_H_

typedef struct TNodo *TLista;
struct TNodo{
	int valor;
	TLista sig;
};

void crear(TLista *lista);
void destruir(TLista *lista);
void mostrar(TLista lista);
void insertarPrincipio(TLista *lista, int valor);
void insertarFinal(TLista *lista, int valor);
void insertarOrdenado(TLista *lista, int valor);
void borrarPrimero(TLista *lista);
void borrar(TLista *lista, int valor);

#endif /* LISTA_H_ */
