/*
 * cola.c
 *
 *  Created on: 22/2/2020
 *      Author: usuario
 */
#include "cola.h"
#include <string.h>

void crearCola(ColaProcesos *cola){
	cola->frente = NULL;
	cola->final = NULL;
}

int colaVacia(ColaProcesos cola){
	return cola.final == NULL;
}

void vaciarCola(ColaProcesos *cola){
	Proceso ptr;
	ptr = cola->frente;
	while (ptr != NULL){
		cola->frente = ptr->sig;
		free(ptr);
	}
	cola->final = NULL;
}

void insertarProceso(ColaProcesos *cola, char *historial){
	Proceso nuevonodo;

	//Creamos nodo nuevo para añadir al final
	nuevonodo = (Proceso)malloc(sizeof(struct DatosProceso));
	strcpy(nuevonodo->historial,historial);
	nuevonodo->sig = NULL;

	if (colaVacia(*cola)){ //la cola esta vacía
		cola->final = nuevonodo;
		cola->frente = nuevonodo;
	}else{ //la cola no está vacia
		cola->final->sig = nuevonodo;
		cola->final = nuevonodo;
	}
}

//precondicion. historial es una zona de memoria apropiada
char* extraerProceso(ColaProcesos *cola, char *historial){
	Proceso ptr;

	if (!colaVacia(*cola)){
		ptr = cola->frente;
		strcpy(historial, ptr->historial);
		cola->frente = ptr->sig;
		free(ptr);
		if (cola->frente == NULL){
			cola->final = NULL;
		}
		return historial;
	}else{
		return NULL;
	}
}

void mostrarCola(ColaProcesos cola){
	Proceso ptr;

	ptr = cola.frente;
	if (ptr == NULL){
		printf("Cola vacia\n");
	}else{
		printf("La cola de procesos es: ");
		while (ptr != NULL){
			printf("%s ",ptr->historial);
			ptr = ptr->sig;
		}
		printf("\n");
	}
}

void moverProcesos(ColaProcesos *dest, ColaProcesos *orig, int num){
	char *h;
	char historial[11];
	int i;

	if (orig->frente != NULL){
		i = 1;
		do{
			h = extraerProceso(orig,historial);
			insertarProceso(dest,historial);
			i++;
		}while (i<num && h!=NULL);
	}
}

int procesosEsperando(ColaProcesos cola){
	int num = 0;
	Proceso ptr;

	ptr = cola.frente;
	while (ptr != NULL){
		num++;
		ptr = ptr->sig;
	}
	return num;
}
