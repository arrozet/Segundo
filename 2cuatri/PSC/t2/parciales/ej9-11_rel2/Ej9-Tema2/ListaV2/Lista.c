/*
 ============================================================================
 Name        : ListaV2.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */
#include "Lista.h"
#include <stdio.h>
#include <stdlib.h>

void crear(TLista *lista){
	*lista = NULL;
}

void destruir(TLista *lista){
	TLista borrar;

	while (*lista != NULL){
		borrar = (*lista);
		(*lista) = (*lista)->sig;
		free(borrar);
	}
}

void mostrar(TLista lista){
	if (lista == NULL){
		printf("(empty)\n");
	}else{
		while (lista != NULL){
			printf("%d ",lista->valor);
			lista = lista->sig;
		}
		printf("\n");
	}
	fflush(stdout);
}

void insertarPrincipio(TLista *lista, int valor){
	TLista nuevo;

	nuevo = (TLista) malloc(sizeof(struct TNodo));
	nuevo->valor = valor;
	nuevo->sig = (*lista);
	(*lista) = nuevo;
}

void insertarFinal(TLista *lista, int valor){
	TLista nuevo;
	TLista ptr;

	nuevo = (TLista) malloc(sizeof(struct TNodo));
	nuevo->valor = valor;
	nuevo->sig = NULL;

	if ((*lista) == NULL){
		(*lista) = nuevo;
	}else{
		ptr = (*lista);
		while (ptr->sig != NULL){ //Para no salirnos de la lista
			ptr = ptr -> sig;
		}
		ptr->sig = nuevo;
	}
}

//Precondición: la lista está ordenada
/*void insertarOrdenado(TLista *lista, int valor){
	TLista nuevo, ptr, ant;

	if ((*lista) == NULL || (*lista)->valor >= valor){
		insertarPrincipio(lista,valor);
	}else{
		nuevo = (TLista) malloc(sizeof(struct TNodo));
		nuevo->valor = valor;
		//Buscar donde insertar
		ant = (*lista);
		ptr = (*lista)->sig;
		while (ptr != NULL && ptr->valor < valor){
			ant = ptr;
			ptr = ptr->sig;
		}
		nuevo->sig = ptr;
		ant->sig = nuevo;
	}
}*/

void insertarOrdenado(TLista *lista, int valor){
	TLista nuevo, ptr, ant;

	ant = NULL;
	ptr = (*lista);
	//Buscamos donde insertar
	while (ptr != NULL && ptr->valor < valor){
		ant = ptr;
		ptr = ptr->sig;
	}
	if (ant == NULL){ //Indica que hay que insertar al principio
		insertarPrincipio(lista,valor);
	}else{
		//Creamos el nuevo nodo
		nuevo = (TLista) malloc(sizeof(struct TNodo));
		nuevo->valor = valor;
		nuevo->sig = ptr;
		ant->sig = nuevo;
	}
}

void borrarPrimero(TLista *lista){
	struct TNodo *borrar;

	borrar = *lista;
	(*lista) = (*lista)->sig;
	free(borrar);
}

void borrar(TLista *lista, int valor){
	struct TNodo *ant, *borrar;

	ant = NULL;
	borrar = (*lista);

	while(borrar->valor != valor){
		ant = borrar;
		borrar = borrar -> sig;
	}

	if (ant == NULL){
		(*lista) = (*lista)->sig;
	}else{
		ant->sig = borrar->sig;
	}
	free(borrar);
}
