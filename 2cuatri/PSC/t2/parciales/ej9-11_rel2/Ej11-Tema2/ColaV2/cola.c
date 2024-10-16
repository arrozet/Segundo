/*
 * cola.c
 *
 *  Created on: 22/2/2020
 *      Author: usuario
 */
#include "cola.h"
#include <string.h>

void crearCola(ColaProcesos *cola){
	*cola = NULL;
}

int colaVacia(ColaProcesos cola){
	return cola == NULL;
}

void vaciarCola(ColaProcesos *cola){
	ColaProcesos borrar, ptr;
	ptr = (*cola)->sig;
	borrar = ptr;
	while (ptr != (*cola)){
		ptr = ptr->sig;
		free(borrar);
		borrar = ptr;
	}
	free(borrar);
}

void insertarProceso(ColaProcesos *cola, char *historial){
	ColaProcesos nuevonodo;

	//Creamos nodo nuevo para añadir al final
	nuevonodo = (ColaProcesos)malloc(sizeof(struct Proceso));
	strcpy(nuevonodo->historial,historial);
	nuevonodo->sig = NULL;

	if (colaVacia(*cola)){ //la cola esta vacía
		(*cola) = nuevonodo;
		nuevonodo->sig = nuevonodo;
	}else{ //la cola no está vacia
		nuevonodo->sig = (*cola)->sig;
		(*cola)->sig = nuevonodo;
	}
}

//precondicion. historial es una zona de memoria apropiada
void extraerProceso(ColaProcesos *cola, char *historial){
	ColaProcesos ptr,ant;

	if (!colaVacia(*cola)){
		ptr = (*cola);
		strcpy(historial, ptr->historial);
		if (ptr == ptr->sig){//solo hay un nodo
			free(ptr);
			(*cola) = NULL;
		}else{
			ant = (*cola)->sig;
			while (ant->sig != (*cola)){
				ant=ant->sig;
			}
			ant->sig = (*cola)->sig;
			(*cola) = ant;
			free(ptr);
		}
	}
}

/* Es necesario para poder tener como caso base que cola == ptr->sig
 *  - cola siempre apunta al primer nodo
 *  - ptr va cambiando en cada llamada recursiva
 */
void mostrarColaRecursivo(ColaProcesos frente, ColaProcesos final){
	if (frente == final){ //Caso base 2. Solo hay un nodo
		printf("%s ",frente->historial);
	}else{
		mostrarColaRecursivo(frente,final->sig);
		printf("%s ",final->historial);
	}
}

//mostrarCola recursivo para que aparezca primero el primer proceso en ser atentido
void mostrarCola(ColaProcesos cola){
	if (cola == NULL){ //Caso base 1. La cola esta vacia
		printf("Cola Vacia\n");
	}else {
		printf("La cola de Proceso es: ");
		mostrarColaRecursivo(cola,cola->sig);
		printf("\n");
	}
}

//Se muestra en orden contrario. El ultimo en mostrarse es el primero en ser atendido
/*void mostrarCola(ColaProcesos cola){
	ColaProcesos ptr;

	if (cola == NULL){
		printf("Cola vacia\n");
	}else{
		ptr = cola->sig;
		printf("La cola de Proceso es: ");
		while (ptr != cola){
			printf("%s ",ptr->historial);
			ptr = ptr->sig;
		}
		printf("%s\n",ptr->historial);
	}
}*/

void moverProcesos(ColaProcesos *dest, ColaProcesos *orig, int num){
	char historial[11];
	int i;

	i = 0;
	while (i<num && (*orig) != NULL){
		extraerProceso(orig,historial);
		insertarProceso(dest,historial);
		i++;
	}
}

int procesosEsperando(ColaProcesos cola){
	int num = 0;
	ColaProcesos ptr;

	if (cola !=NULL){
		ptr = cola->sig;
		while (ptr != cola){
			num++;
			ptr = ptr->sig;
		}
		num++;
	}
	return num;
}
