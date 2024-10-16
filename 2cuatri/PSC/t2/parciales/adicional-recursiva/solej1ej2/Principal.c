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
#include "Lista.h"
#include <stdlib.h>

#define MAX 10

//crea un fichero binario con un numero aleatorios de enteros
void crearFichero(char *nombre){
	FILE *ptr = fopen(nombre,"wb"); //abre un fichero de escritura (si no existe se crea)
	if (ptr==NULL) perror("No se ha podido abrir el fichero"); //perror: consola con errores
	else {
		int i;
		int num,tam = rand() % 100;
		for(i=0;i<tam;i++){
			num = rand()%100;
			fwrite(&num,sizeof(int),1,ptr);
		}
		fclose(ptr);
	}
}

/**
 * lee un fichero binario de enteros y los escribe en la pantalla
 */
void leerFichero(char * nombre){
   FILE *ptr = fopen(nombre,"rb");
   if (ptr==NULL) perror("No se ha podido abrir el fichero");
   else {
	   int num;
	   while (fread(&num,sizeof(int),1,ptr)!=0){
		   printf("%d ",num);
	   }
	   printf("\n*********\n");
	   fclose(ptr);
   }
}
int main(){

	//Crea el fichero con los n�meros aleatorios
	crearFichero("num.bin");
	//Lee los numeros del fichero y los muestra por pantalla
	leerFichero("num.bin");

	//Array de listas
	Lista l[MAX];

	//Creamos las listas vacias
	int i;
	for (i=0;i<MAX;i++){
		crear(&(l[i]));
	}

	//Volvemos a leer los numeros del fichero y guardamos cada numero en la lista correspondiente
	//La lista donde insertar depender� del resto de dividir el numero entre 10
	FILE *ptr = fopen("num.bin","rb");
	if (ptr==NULL) perror("No se ha podido abrir el fichero");
	else {
	   int num;
	   while (fread(&num,sizeof(int),1,ptr)>0){
		   int resto = num%10;
		   //No puede haber numeros repetidos. Buscamos primero el numero en la lista
		   if (buscar(l[resto],num)==0){
			   insertarOrdenado(&(l[resto]),num);
		   }
	   }
	   fclose(ptr);
	}

	//Mostrar las listas
	for (i=0;i<MAX;i++){
		printf("Lista %d: ",i);
		mostrar(l[i]);
		printf("\n\n");
	}
	return 0;
}
