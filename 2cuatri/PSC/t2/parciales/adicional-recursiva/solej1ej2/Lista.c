/*
 * Lista.c
 *
 *  Created on: 16 mar. 2020
 *      Author: mmar
 */

#include "Lista.h"
#include <stdlib.h>
#include <stdio.h>


void crear(Lista * l){
	*l = NULL;
}
int listaVacia(Lista l){
	return l == NULL;
}

void mostrar(Lista l){
	if (l!=NULL){ //caso base
		printf("%d ",l->valor);
		mostrar(l->sig); //llamada recursiva
	}
}

/*void mostrar(Lista l){
	if (l!=NULL){ //caso base
			mostrar(l->sig); //llamada recursiva
			printf("%d ",l->valor);
	}
}*/

//devuelve 0 si el elemento no esta, y 1 en otro caso. La lista está ordenada
int buscar(Lista l,int v){
	int exito = 0; //suponemos que el elemento no esta
	if (l!=NULL){ //el caso base l==NULL, el elemento no esta devuelve -1
		if (l->valor == v) exito = 1;
		else if (l->valor<v){ // si no esta, pero es posible encontrarlo (la lista está ordenada)
			exito = buscar(l->sig,v); //llamada recursiva
		}
	}
	return exito;
}

void insertarOrdenado(Lista *l, int v){
	Lista aux;
	if (listaVacia(*l) || (*l)->valor>v){ //lista vacia o  el nuevo elemento es el primero
		aux = (Lista) malloc(sizeof(struct Nodo));
		aux->valor = v;
		aux->sig = *l;
		*l = aux;
	} else {
		insertarOrdenado(&((*l)->sig),v); //caso recursivo el elemento va detras
										// ojo hay que pasar un doble puntero
	}
}
void eliminar(Lista *l, int v){
	Lista aux;
	if (*l!=NULL){ // caso base 1: Lista vacÃ­a
		if ((*l)->valor == v){ // caso base 2: hemos encontrado el elemento
			aux = *l;
			*l = aux->sig;
			free(aux); //se elimina el elemento
		} else if ((*l)->valor<v){ //Si es mayor el elemento no está en la lista
			eliminar(&(*l)->sig,v); //caso recursivo
		}
	}
}

void crearDeFichero(Lista *l,char* nombre){
	*l = NULL;
	FILE *ptr = fopen(nombre,"r");
	if (ptr == NULL) perror("No se ha podido abrir el fichero");
	else {
		int num;
		while (fread(&num,sizeof(int),1,ptr)!=0){
			insertarOrdenado(l,num);
		}
		fclose(ptr);
	}
}
