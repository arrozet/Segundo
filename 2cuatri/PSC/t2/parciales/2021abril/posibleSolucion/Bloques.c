/*
 * Bloques.c
 *
 *  Created on: 16 abr. 2021
 *      Author: Monica
 */
#include <stdio.h>
#include "Bloques.h"
#include <stdlib.h>

/* Funcion auxiliar privada */
void crearNodo(ListaBloques *nodo, unsigned int dir){
	*nodo = (ListaBloques) malloc(sizeof(struct Nodo));
	if (*nodo != NULL){
		(*nodo)->dirInicio = dir;
		(*nodo)->sig = NULL;
	}
}

/**
* Crea una lista de bloques de 512 bytes cada uno, para una cantidad de memoria
*  en bytes proporcionada como par�metro de entrada.
*  El primer bloque de la lista tendr� como direcci�n 0, el segundo 512, el tercero 1024
*  y as� sucesivamente.
*
*  Se puede asumir que tamMemoria es un valor m�ltiplo de 512.
*
*  Si tamMemoria es 0 entonces se crear� una lista vacia.
*/
void crear(ListaBloques *lb, unsigned int tamMemoria){
	int veces, dir;
	*lb = NULL;

	if (tamMemoria > 0){
		veces = tamMemoria/512;
		dir = tamMemoria-512;
		ListaBloques nuevo;

		*lb = NULL;
		for (int i=0; i<veces; i++){
			crearNodo(&nuevo,dir);
			insertarBloque(lb,nuevo);
			dir -= 512;
		}
	}
}

/**
* Se obtiene un bloque de la lista de bloques libres.
* El primer par�metro es la lista de bloques libres y el segundo par�metro es el bloque
* que ser� devuelto por la funci�n.
*
* - Si la lista de bloques est� vac�a el segundo par�metro ser� NULL.
* - Si la lista de bloques no est� vac�a se devolver� el primer bloque libre, sac�ndolo de
*   la lista y devolviendo en el segundo par�metro un puntero a dicho bloque
*
* �CUIDADO� Sacar el bloque de la lista no significa liberar la memoria de ese bloque, sino
* que el nodo deja de formar parte de la lista y se devuelve un puntero al nodo
*/
void obtenerBloque(ListaBloques *lb, ListaBloques *bloque){
	*bloque = *lb;
	if (*bloque != NULL){
		*lb = (*lb)->sig;
		(*bloque)->sig = NULL;
	}
}

/**
 * Se inserta un bloque en la lista de bloques libres
 * El primer par�metro es la lista de bloques libres y el segundo par�metro es el bloque
 * que debe volver a insertarse en la lista.
 * La inserci�n se realizar� de forma ordenada por la direcci�n de inicio del bloque.
 *
 * �CUIDADO� El nodo que se quiere devolver a la lista ya existe (bloque es un puntero que
 * apunta a ese nodo). No hay que crear el nodo reservando memoria para �l, solo volver a
 * incorporarlo a la lista.
*/
void insertarBloque(ListaBloques *lb, ListaBloques bloque){
	//Insertar ordenado pero sin reservar memoria
	ListaBloques ptr, ant;

	ant = NULL;
	ptr = *lb;

	while (ptr != NULL && ptr->dirInicio < bloque->dirInicio){
		ant = ptr;
		ptr = ptr->sig;
	}
	if (ant == NULL){
		//insertar al principio
		bloque->sig = ptr;
		*lb = bloque;
	}else{
		//insertar en medio
		bloque->sig = ptr;
		ant->sig = bloque;
	}
}

/**
 * Escribe por pantalla la informaci�n de cada uno de los bloques libres
 * almacenados en la lista.
 *
 * El formato de salida debe ser: (<dir1> <dir2> <dir3> � <dirN>)
 */
void imprimir(ListaBloques lb){
	printf(" ( ");
	while (lb !=NULL){
		printf("%d ", lb->dirInicio);
		lb = lb->sig;
	}
	printf(")\n");
}

/**
 * Borra todos los nodos de la lista y la deja vac�a
 */
void borrar(ListaBloques *lb){
	ListaBloques borrar;

	while (*lb != NULL){
		borrar = *lb;
		*lb = (*lb)->sig;
		free(borrar);
	}
}

