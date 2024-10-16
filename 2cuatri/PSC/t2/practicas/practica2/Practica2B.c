/*
 ============================================================================
 Name        : Practica2B.c
 Author      : esc
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "arbolbb.h"

/**
 * Pide un n�mero "tam" al usuario, y
 * crea un fichero binario para escritura con el nombre "nfichero"
 * en que escribe "tam" numeros (unsigned int) aleatorios
 * Se utiliza srand(time(NULL)) para establecer la semilla (de la libreria time.h)
 * y rand()%TAM para crear un n�mero aleatorio entre 0 y TAM-1.
 */
void creafichero(char* nfichero){
	FILE* fent;
	unsigned int tam;	

	if ((fent = fopen(nfichero,"wb"))==NULL){
		perror("Error abriendo nfichero");
	}
	else{
		printf("Introduce tamaño:\n");
		scanf("%u",&tam);

		srand(time(NULL));	// time null saca el tiempo del sistema, para que las semillas sean
							// distintas y no salgan siempre los mismos numeros

		for(unsigned int i=0; i<tam; i++){
			int n = rand()%tam;
			fwrite(&n,sizeof(int),1,fent);
		}
	}
	fclose(fent);
}
/**
 * Muestra por pantalla la lista de n�meros (unsigned int) almacenada
 * en el fichero binario "nfichero"
 */
void muestrafichero(char* nfichero){
	FILE* fout;

	fout = fopen(nfichero,"rb");
	if (fout == NULL){
		perror("Error abriendo el fichero");
	}
	else{
		int n;
		while (fread(&n,sizeof(int),1,fout) == 1){
			printf("%d ", n);
		}
	}

	fclose(fout);
}

/**
 * Guarda en el arbol "*miarbol" los n�meros almacenados en el fichero binario "nfichero"
 */

void cargaFichero(char* nfichero, T_Arbol* miarbol){
	FILE* file;

	if ((file = fopen(nfichero,"rb"))==NULL){
		perror("Error abriendo nfichero");
	}
	else{
		int n;
		while (fread(&n,sizeof(int),1,file) == 1){
			Insertar(miarbol, n);
		}
		
	}
	fclose(file);
}

int main(void) {
	//setvbuf(stdout,NULL,_IONBF,0);

	char nfichero[50];
	printf ("Introduce el nombre del fichero binario:\n");
	fflush(stdout);
	scanf ("%s",nfichero);
	fflush(stdin);
	creafichero(nfichero);
	printf("\nAhora lo leemos y mostramos:\n");
	muestrafichero(nfichero);
	fflush(stdout);

	printf ("\nAhora lo cargamos en el arbol\n");
	T_Arbol miarbol;
	Crear (&miarbol);
	cargaFichero(nfichero,&miarbol);
	printf ("\nY lo mostramos ordenado\n");
	Mostrar(miarbol);
	fflush(stdout);
	printf("\nAhora lo guardamos ordenado\n");
	FILE * fich;
	fich = fopen (nfichero, "wb");
	Salvar (miarbol, fich);
	fclose (fich);
	printf("\nY lo mostramos ordenado\n");
	muestrafichero(nfichero);
	Destruir (&miarbol);

	return EXIT_SUCCESS;
}
