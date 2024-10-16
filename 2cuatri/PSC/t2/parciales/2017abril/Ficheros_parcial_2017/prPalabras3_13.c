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


void escribir_salida(FILE * fp, Lista* palabras){
	for(int i=0; i<NUM_TAMANO; i++){
		fprintf(fp, "Palabras de %d letras:\n", i+3);
		if(palabras[i]!=NULL){
			escribir_fichero(fp, palabras[i]);
		}
		else{
			fprintf(fp, "No se han encontrado palabras de %d letras", i+3);
		}
		fprintf(fp, "\n");
	}
}


int main(void) {

	setvbuf(stdout, NULL, _IONBF, 0);
	setvbuf(stderr, NULL, _IONBF, 0);
	char *inputFileName = "Lorem_Ipsum.txt";
	char *outputFileName = "Palabras3_13.txt";


	/* Crear Array de Palabras de tamaño NUM_TAMANO */
	Lista palabras[NUM_TAMANO];
	/* Incializar lista palabras */
	for(int i=0; i<NUM_TAMANO; i++){
		crear(&palabras[i]);
	}
	/* Abrir Fichero de Entrada */
	FILE* fent = fopen(inputFileName, "rt");
	if(fent==NULL){
		perror("Error abriendo fichero");
		exit(-1);
	}

	char palabra_a_meter[14]; // 13 + /0
	/* Leer palabras del fichero de entrada */
	while(fscanf(fent, "%s", palabra_a_meter)>0){
		if(strlen(palabra_a_meter)>=3 && strlen(palabra_a_meter)<=13){
			insertar(palabra_a_meter, &palabras[strlen(palabra_a_meter)-3]);
		}
	}
	fclose(fent);

	/* Escribir en consola */
	for(int i=0; i<NUM_TAMANO; i++){
		printf("Palabras de %d letras:\n", i+3);
		if(palabras[i]!=NULL){
			escribir(palabras[i]);
		}
		else{
			printf("No se han encontrado palabras de %d letras\n", i+3);
		}
		printf("\n");
	}
	
	/* Escribir archivo (para el apartado B) */
	FILE* fsal = fopen(outputFileName, "wt");
	if(fsal==NULL){
		perror("Error abriendo fichero");
		exit(-1);
	}
	escribir_salida(fsal, palabras);
	fclose(fsal);

	/* Destruir las listas creadas */
	for(int i=0; i<NUM_TAMANO; i++){
		destruir(&palabras[i]);
	}

	destruir(palabras);

	return EXIT_SUCCESS;
}
