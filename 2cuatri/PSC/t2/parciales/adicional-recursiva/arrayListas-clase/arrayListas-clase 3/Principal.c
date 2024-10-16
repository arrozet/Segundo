/*
 * Principal.c
 *
 *  Created on: 16 mar. 2020
 *      Author: mmar
 */


/*
 * HolaMundo.c
 *
 *  Created on: 1 mar. 2017
 *      Author: mmar
 */
#include <stdio.h>
#include <string.h>
#include <time.h>
#include "Lista.h"
#include <stdlib.h>

#define MAX 10
#define TAM 100

//crea un fichero binario con un numero aleatorios de enteros
void crearFichero(char *nombre){
	FILE *ptr = fopen(nombre,"w"); //abre un fichero de escritura (si no existe se crea)
	if (ptr==NULL) perror("No se ha podido abrir el fichero"); //perror: consola con errores
	else {
		int i;
		srand(time(NULL));
		int num,tam = rand()%TAM;
		for(i=0;i<tam;i++){
			num = rand()%TAM;
			fwrite(&num,sizeof(int),1,ptr);
		}
		fclose(ptr);
	}
}

/**
 * lee un fichero binario de enteros y los escribe en la pantalla
 */
void leerFichero(char * nombre){
   FILE *ptr = fopen(nombre,"r");
   if (ptr==NULL) perror("No se ha podido abrir el fichero");
   else {
	   int num;
	   while (fread(&num,sizeof(int),1,ptr)!=0){
		   printf("%d ",num);
	   }
	   printf("\n\n");
	   fclose(ptr);
   }
}
int main(){

	crearFichero("num.bin");
	leerFichero("num.bin");

	//Array de listas

	//Creamos las listas vacias

	//Leemos del fichero y guardamos en la lista correspondiente

	//Mostrar las listas

	return 0;
}
