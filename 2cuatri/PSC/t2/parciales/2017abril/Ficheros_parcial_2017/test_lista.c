/*
 * test_lista.c
 *
 *  Created on: 17 abr. 2017
 *      Author: Manuel F. Bertoa
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "lista.h"

int main(void){

	Lista miLista;

	setvbuf(stdout, NULL, _IONBF, 0);
	setvbuf(stderr, NULL, _IONBF, 0);

	crear(&miLista);
	printf("Lista vacia? %s\n", (lista_vacia(miLista)?"TRUE":"FALSE"));
	escribir(miLista);
	//printf("ok\n");
	insertar("UnaPalabra", &miLista);
	escribir(miLista);
	insertar("DosPalabras", &miLista);
	escribir(miLista);
	insertar("TresPalabras", &miLista);
	escribir(miLista);
	insertar("CuatroPalabras", &miLista);
	printf("Lista vacia? %s\n", (lista_vacia(miLista) ? "TRUE" : "FALSE"));
	escribir(miLista);
	printf("Esta UnaPalabra ? %s\n", (buscar_palabra("UnaPalabra", miLista) ? "TRUE" : "FALSE"));
	printf("Esta OtraPalabra ? %s\n", (buscar_palabra("OtraPalabra", miLista) ? "TRUE" : "FALSE"));
	destruir(&miLista);
	printf("Lista vacia? %s\n", (lista_vacia(miLista)?"TRUE":"FALSE"));

	return EXIT_SUCCESS;
}

