#include "ListaCircular.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


//crea una lista circular vac�a (sin ning�n nodo)
void crear(TListaCircular *lc){
	*lc = NULL;
}

//inserta un nuevo nodo con el dato nombre al final de la lista circular
void insertar(TListaCircular *lc,char *nombre){
	TListaCircular nodo = (TListaCircular) malloc(sizeof(struct TNodo));
	if(nodo==NULL){
		perror("No se pudo reservar memoria\n");
	}
	else{
		nodo->sig = NULL;
		strcpy(nodo->nombre, nombre);
		if(*lc == NULL){
			*lc = nodo;
			nodo->sig = nodo;
			//(*lc)->sig = nodo;
		}
		else{
			TListaCircular primero = (*lc)->sig;	// antes: primero
			(*lc)->sig = nodo;						// nuevo ultimo: nodo
			nodo->sig = primero;					// siguiente al nuevo ultimo: el primero
			*lc = nodo;								// lc apunta al ultimo elemento
		}
	}
}

//recorre la lista circular escribiendo los nombres de los nodos en la
//pantalla
void recorrer(TListaCircular lc){
	if(lc!=NULL){
		TListaCircular inicio = lc->sig;
		while(inicio != lc){
			printf("%s ", inicio->nombre);
			inicio = inicio->sig;
		}
		printf("%s ", inicio->nombre);
		printf("\n");
	}
}

//devuelve el n�mero de nodos de la lista
int longitud(TListaCircular lc){
	
	if(lc == NULL){
		return 0;
	}
	else{
		TListaCircular inicio = lc->sig;
		int cont = 1;
		while(inicio != lc){
			cont++;
			inicio = inicio->sig;
		}
		return cont;
	}
	
}

//mueve el puntero exterto de la lista n nodos (siguiendo la direcci�n de la
//lista)
void mover(TListaCircular *lc,int n){
	if(*lc!=NULL){
		TListaCircular new_lc = *lc;
		while(n>0){
			new_lc = new_lc->sig;
			n--;
		}
		*lc = new_lc;
	}
	
}

//elimina el primer nodo de la lista, y devuelve el nombre que contiene
//a trav�s del par�metro nombre
void extraer(TListaCircular *lc,char *nombre){
	if(*lc!=NULL){
		TListaCircular primero = (*lc)->sig;	// extraigo el primero
		strcpy(nombre, primero->nombre);
		(*lc)->sig = primero->sig;	// ahora el primero es el siguiente al antiguo primero
		
		free(primero);
	}
	
}
