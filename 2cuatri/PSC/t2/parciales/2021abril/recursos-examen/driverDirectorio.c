/*
 ============================================================================
 Name        : ExamenAbril2021.c
 Author      : 
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include "Bloques.h"
#include "Directorio.h"

int main(void) {
	ListaBloques lb;  //Lista de bloques libres de memoria
	ListaFicheros lf; //Lista de ficheros del directorio

	//Creamos la lista de bloques libres
	printf("CREAMOS LA LISTA DE BLOQUES LIBRES\n----------------------------------\n");
	fflush(stdout);
	crear(&lb,4096);
	printf("Bloques libres: ");
	fflush(stdout);
	imprimir(lb);

	//Creamos el directorio desde un fichero de texto
	printf("\nCREAMOS EL DIRECTORIO CON LOS DATOS CONTENIDOS EN directorio.txt\n----------------------------------------------------------------\n");
	fflush(stdout);
	crearDesdeFicheroTexto("directorio.txt",&lf,&lb);
	imprimirDirectorio(lf);

	//Borramos los datos del directorio
	printf("\nBORRAMOS LOS DATOS DEL DIRECTORIO\n----------------------------------------------------------------\n");
	fflush(stdout);
	borrarDirectorio(&lf,&lb);
	imprimirDirectorio(lf);
	fflush(stdout);
	printf("Bloques libres: ");
	fflush(stdout);
	imprimir(lb);

	//Liberamos la memoria de la lista de bloques libres
	borrar(&lb);
}
