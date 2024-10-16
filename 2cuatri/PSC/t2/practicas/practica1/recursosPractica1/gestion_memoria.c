/*
 * gestion_memoria.c

 *
 *  Created on: 07/03/2024
 *  Author: Rubén Oliva Zamora
 */

#include "gestion_memoria.h"
#include <stdio.h>
#include <stdlib.h>

#define MAX 1000

/* Crea la estructura utilizada para gestionar la memoria disponible.
 * Inicialmente, s�lo un nodo desde 0 a MAX
*/
void crear(T_Manejador* manejador){
	*manejador = (T_Manejador) malloc(sizeof(struct T_Nodo));	// casting pq malloc devuelve void pointer
	(*manejador)->inicio = 0;
	(*manejador)->fin = MAX-1;
	(*manejador)->sig = NULL;
}

/* Destruye la estructura utilizada (libera todos los nodos de la lista.
 * El par�metro manejador debe terminar apuntando a NULL
*/
void destruir(T_Manejador* manejador){
	/*T_Manejador current = *manejador;
	T_Manejador next = NULL;

	while(current!=NULL){
		next = current->sig;
		free(current);
		current = next;
	}

	*manejador = NULL;	// ptr apunta a null, ya que he destruido la estructura*/

	T_Manejador previous;

	while(*manejador != NULL){
		previous = *manejador;
		*manejador = previous->sig;
		free(previous);
	}
}

/* Devuelve en �dir� la direcci�n de memoria �simulada� (unsigned) donde comienza
 * el trozo de memoria continua de tama�o �tam� solicitada.
 * Si la operaci�n se pudo llevar a cabo, es decir, existe un trozo con capacidad
 * suficiente, devolvera TRUE (1) en �ok�; FALSE (0) en otro caso.
 */
void obtener(T_Manejador *manejador, unsigned tam, unsigned* dir, unsigned* ok){
	T_Manejador current = *manejador;
	T_Manejador previous = NULL;

	// Busco un nodo con suficiente espacio
	while(current!=NULL && (current->fin - current->inicio + 1) < tam){
		previous = current;
		current = current->sig;
	}

	// Si se encontró un nodo con suficiente espacio
	if(current!=NULL){
		*ok = 1;
		*dir = current->inicio;

		if((current->fin - current->inicio + 1) == tam){	// Debo borrar el nodo
			if(previous!=NULL){ // NO es el primero de la lista
				previous->sig = current->sig;
				free(current);
			}
			else{
				*manejador = current->sig;
				free(current);
			}
		}
		else{	// reducir el nodo
			current->inicio += tam; 
		}
	}
	// Si NO hay bloque con suficiente espacio
	else{
		*ok = 0;
	}
}

/* Muestra el estado actual de la memoria, bloques de memoria libre */
void mostrar (T_Manejador manejador){
	T_Manejador current = manejador;
	printf("-----\n");
	while(current!=NULL){
		printf("Desde %u a %u: Libre\n", current->inicio, current->fin);
		current = current->sig;
	}
}

/* Devuelve el trozo de memoria continua de tama�o �tam� y que
 * comienza en �dir�.
 * Se puede suponer que se trata de un trozo obtenido previamente.
 */
void devolver(T_Manejador *manejador,unsigned tam,unsigned dir){
	T_Manejador current = *manejador;
	T_Manejador previous = NULL;

	// Me quedo justo después de dir
	while(current!=NULL && (current->inicio < dir)){ 
		previous = current;
		current = current->sig;
	}	// entre previous y current se situará el trozo de memoria que necesitamos

	if(previous != NULL && previous->fin == dir-1){	// es el contiguo a previous
		previous->fin += tam;
	}
	else if(current != NULL && current->inicio <= (dir+tam)){	// es el contiguo a current - si nos piden devolver un fragmento de memoria en el cual hay una parte liberada, que no se rompa el codigo
		current->inicio = dir;
	}
	else if(previous != NULL && previous->fin == dir-1 && current != NULL && current->inicio == (dir+tam-1)){	// está justo entre ambos
		previous->fin += tam;
		previous->sig = current->sig;	// conecto previous al siguiente y borro el que tengo
		free(current);
	}
	else{	// en otro caso, hay que crear un nodo nuevo
		T_Manejador new = (T_Manejador) malloc(sizeof(struct T_Nodo));
		new->inicio = dir;
		new->fin = dir+tam-1;
		new->sig = current;

		if(previous!=NULL){ // si existe un previous
			previous->sig = new; 	// lo conecto
		}
		else{
			*manejador = new;	// sino conecto el pointer de inicio de lista
		}
	}
	
}
