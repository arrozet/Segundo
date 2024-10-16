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
//crea un fichero binario con un numero aleatorios de enteros
void crearFichero(char *nombre){
	FILE *ptr = fopen(nombre,"w"); //abre un fichero de escritura (si no existe se crea)
	if (ptr==NULL) perror("No se ha podido abrir el fichero"); //perror: consola con errores
	else {
		int i;
		int num,tam = rand() % 100;
		for(i=0;i<tam;i++){
			num = rand()%100;
			fwrite(&num,4,1,ptr);
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
	   while (fread(&num,4,1,ptr)!=0){
		   printf("%d ",num);
	   }
	   printf("\n*********\n");
	   fclose(ptr);
   }
}
int main(){

	crearFichero("num.bin");
	leerFichero("num.bin");

	Lista l;

	crear(&l);
	mostrar(l);

	insertarOrdenado(&l,1);
	mostrar(l);
	insertarOrdenado(&l,3);
	mostrar(l);
	insertarOrdenado(&l,0);
	mostrar(l);
	insertarOrdenado(&l,2);
	mostrar(l);
	eliminar(&l,0);
	mostrar(l);
	eliminar(&l,2);
	mostrar(l);
	eliminar(&l,2);
	mostrar(l);
	eliminar(&l,3);
	mostrar(l);
	printf("contiene la lista el 1? %d \n",buscar(l,1));
	printf("contiene la lista el 2? %d \n",buscar(l,2));

	Lista l2;
	crearDeFichero(&l2,"num.bin");
	mostrar(l2);

}
