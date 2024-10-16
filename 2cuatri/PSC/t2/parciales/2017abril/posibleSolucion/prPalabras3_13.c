/*
 ============================================================================
 Name        : prPalabras3_13.c
 Author      : PONGA SU NOMBRE AQUI <<<<<<<<<<<<<<<<<<<<<
 Version     : 1
 Copyright   : Examen parcial abril 2017
 Description : Programa principal, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "lista.h"
#define MIN_LETRA (3)
#define MAX_LETRA (13)
#define NUM_TAMANO (MAX_LETRA - MIN_LETRA +1)


void escribir_salida(FILE * fp, Lista* lp){
	for (int i=0; i<NUM_TAMANO;i++){
		if (lista_vacia(lp[i])){
			fprintf(fp,"No hay palabras de %d letras\n",i+3);
		}else{
			fprintf(fp,"Palabras de %d letras:\n   ",i+3);
			escribir_fichero(fp,lp[i]);
		}
	}
}

int main2(void) {

	//setvbuf(stdout, NULL, _IONBF, 0);
	//setvbuf(stderr, NULL, _IONBF, 0);
	char *inputFileName = "Lorem_Ipsum.txt";
	char *outputFileName = "Palabras3_13.txt";


	/* Crear Array de Palabras de tamaño NUM_TAMANO */
	Lista arrayPalabras[NUM_TAMANO];

	/* Incializar lista palabras */
	for (int i=0; i<NUM_TAMANO; i++){
		crear(&arrayPalabras[i]);
	}

	/* Abrir Fichero de Entrada */
	FILE *fent = fopen(inputFileName,"rt");
	if (fent == NULL){
		perror("Error abriendo el fichero de entrada\n");
		exit(-1);
	}

	//Asumimos que la longitud maxima de una palabra en el fichero es 13
	char palabra[14]; //13 max + '\0'
	//char *palabra; //estaría mal porque no reservamos memoria al leer del fichero
	/* Leer palabras del fichero de entrada */
	while (fscanf(fent,"%s",palabra) > 0){
		int longitud = strlen(palabra);
		if (longitud >= 3 && longitud <=13 && !buscar_palabra(palabra,arrayPalabras[longitud-3]))
			insertar(palabra,&arrayPalabras[longitud-3]);
	}
	fclose(fent);

	/* Escribir en consola */
	for (int i=0; i<NUM_TAMANO;i++){
		if (lista_vacia(arrayPalabras[i])){
			printf("No hay palabras de %d letras\n",i+3);
		}else{
			printf("Palabras de %d letras:\n   ",i+3);
			escribir(arrayPalabras[i]);
		}
	}

	/* Escribir archivo (para el apartado B) */
	FILE *fsal = fopen(outputFileName,"wt");
	escribir_salida(fsal,arrayPalabras);
	fclose(fsal);

	/* Destruir las listas creadas */
	for (int i=0; i<NUM_TAMANO;i++){
		destruir(&arrayPalabras[i]);
	}


	return EXIT_SUCCESS;
}
