
#include "ListaCircular.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*typedef struct TNodo * TListaCircular;
struct TNodo{
	char nombre[30];
	TListaCircular sig;
};*/


//crea una lista circular vac�a (sin ning�n nodo)
void crear(TListaCircular *lc){
	*lc = NULL;
}

//inserta un nuevo nodo con el dato nombre al final de la lista circular
void insertar(TListaCircular *lc,char *nombre){
	TListaCircular nuevo = malloc(sizeof(struct TNodo));
	strcpy(nuevo->nombre,nombre);
	nuevo->sig = NULL;

	if (*lc == NULL){
		*lc = nuevo;
		(*lc)->sig = nuevo;
	}else{
		/*Este código inserta al principio de la lista
		nuevo->sig = (*lc)->sig;
		(*lc)->sig = nuevo;
		*/
		//Este código inserta al final de la lista
		nuevo->sig = (*lc)->sig;
		(*lc)->sig = nuevo;
		(*lc) = nuevo;
	}
}

//recorre la lista circular escribiendo los nombres de los nodos en la
//pantalla
void recorrer(TListaCircular lc){
	TListaCircular ptr;

	if (lc != NULL){
		ptr = lc->sig;
		while (ptr->sig != lc->sig){
			printf("%s ", ptr->nombre);
			ptr = ptr->sig;
		}
	}

	printf("\n");
}

//devuelve el n�mero de nodos de la lista
int longitud(TListaCircular lc){
	int cont = 0;
	TListaCircular ptr;

	if (lc != NULL){
		ptr = lc->sig;
		while (ptr->sig != lc->sig){
			cont++;
			ptr = ptr->sig;
		}
	}
	return cont;
}

//mueve el puntero exterto de la lista n nodos (siguiendo la direcci�n de la
//lista)
void mover(TListaCircular *lc,int n){
	if (*lc != NULL){
		for (int i=0; i<n; i++){
			*lc = (*lc)->sig;
		}
	}
}

//elimina el primer nodo de la lista, y devuelve el nombre que contiene
//a trav�s del par�metro nombre
void extraer(TListaCircular *lc,char *nombre){
	TListaCircular borrar;

	if (*lc != NULL){
		borrar = (*lc)->sig;
		strcpy(nombre,borrar->nombre);
		(*lc)->sig = borrar->sig;
		free(borrar);
	}
}
