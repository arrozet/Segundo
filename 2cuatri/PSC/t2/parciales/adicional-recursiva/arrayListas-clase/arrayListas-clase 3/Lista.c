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
	//l es un puntero a un puntero
	*l = NULL;
}
int listaVacia(Lista l){
	return l == NULL;
}

void mostrar(Lista l){
	if (l != NULL){

	}
}

//devuelve 0 si el elemento no esta, y 1 en otro caso
int buscar(Lista l,int v){
	int exito = 0; //suponemos que el elemento no esta
	if (l!=NULL){ //el caso base l==NULL, el elemento no esta devuelve 0

	}
	return exito;
}

void insertarOrdenado(Lista *l, int v){
	Lista aux;

}

void eliminar(Lista *l, int v){
	Lista aux;
}


void crearDeFichero(Lista *l,char* nombre){
	*l = NULL;
	FILE *ptr = fopen(nombre,"r");
	if (ptr == NULL) perror("No se ha podido abrir el fichero");
	else {
		int num;
		while (fread(&num,4,1,ptr)!=0){
			insertarOrdenado(l,num);
		}
		fclose(ptr);
	}
}
