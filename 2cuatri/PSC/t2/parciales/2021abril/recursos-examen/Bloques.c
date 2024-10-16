#include <stdio.h>
#include <stdlib.h>
#include "Bloques.h"

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

const unsigned int TAM_BLOQUE = 512;

ListaBloques crearNodo(unsigned int dir){
	ListaBloques nodo = (ListaBloques) malloc(sizeof(struct Nodo));
	if(nodo == NULL){
		perror("No se pudo reservar memoria\n");
	}
	else{
		nodo->dirInicio = dir;
		nodo->sig = NULL;
	}

	return nodo;
}

void crear(ListaBloques *lb, unsigned int tamMemoria){
	*lb = NULL;
	
	if(tamMemoria>0){
		unsigned int numBloques = tamMemoria / TAM_BLOQUE;
		unsigned int dir = tamMemoria-TAM_BLOQUE;
		for(unsigned int i = 0; i<numBloques; i++){
			ListaBloques nodo = crearNodo(dir);
			insertarBloque(lb, nodo);
			dir-=TAM_BLOQUE;
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
*   la lista y devolviendo en el segundo par�metro
*
* �CUIDADO� Sacar el bloque de la lista no significa liberar la memoria de ese bloque, sino
* que el nodo deja de formar parte de la lista
*/
void obtenerBloque(ListaBloques *lb, ListaBloques *bloque){
	if(*lb == NULL){
		*bloque = NULL;
	}
	else{
		*bloque = *lb;
		(*lb) = (*lb)->sig;
		(*bloque)->sig = NULL;
	}
}

/**
 * Se inserta un bloque en la lista de bloques libres
 * El primer par�metro es la lista de bloques libres y el segundo par�metro es el bloque
 * que debe volver a insertarse en la lista.
 * La inserci�n se realizar� de forma ordenada por la direcci�n de inicio del bloque a devolver.
 *
 * �CUIDADO� El nodo que se quiere devolver a la lista ya existe (bloque es un puntero que
 * apunta a ese nodo). No hay que crear el nodo reservando memoria para �l, solo volver a
 * incorporarlo a la lista.
*/
void insertarBloque(ListaBloques *lb, ListaBloques bloque){
	ListaBloques current = *lb;
	ListaBloques previous = NULL;
	while(current!=NULL && current->dirInicio < bloque->dirInicio){
		previous = current;
		current = current->sig;
	}

	if(current==NULL && previous == NULL){
		*lb = bloque;
		bloque->sig = NULL;
	}
	else if(previous==NULL){
		bloque->sig = current;
		*lb = bloque;
	}
	else{
		bloque->sig = current;
		previous->sig = bloque;
	}
}

/**
 * Escribe por pantalla la informaci�n de cada uno de los bloques libres
 * almacenados en la lista.
 *
 * El formato de salida debe ser: (<dir1> <dir2> <dir3> � <dirN>)
 */
void imprimir(ListaBloques lb){
	ListaBloques current = lb;
	printf("( ");
	while(current!=NULL){
		printf("%u ", current->dirInicio);
		current=current->sig;		
	}
	printf(")\n");
}

/**
 * Borra todos los nodos de la lista y la deja vac�a
 */
void borrar(ListaBloques *lb){
	while(*lb!=NULL){
		ListaBloques temp = *lb;
		*lb=(*lb)->sig;		
		free(temp);
	}
}
