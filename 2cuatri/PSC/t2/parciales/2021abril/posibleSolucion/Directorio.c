/*
 * Directorio.c
 *
 *  Created on: 16 abr. 2021
 *      Author: Monica
 */

#include <stdio.h>
#include "Directorio.h"
#include <stdlib.h>
#include <string.h>

/**
 * Crear un directorio vac�o
 */
void crearDirectorio(ListaFicheros* lf){
	*lf = NULL;
}

/* Funcion auxiliar privada */
void crearNodoFichero(ListaFicheros *nodo, char *nombre, unsigned int tam){
	*nodo = (ListaFicheros) malloc(sizeof(struct NodoFichero));
	if (*nodo != NULL){
		strcpy((*nodo)->nombre,nombre); //IMPORTANTE - Hay que copiar
		(*nodo)->tam = tam;
		crear(&((*nodo)->bloques),0);
		(*nodo)->sig = NULL;
	}
}

/*funcion privada para devolver los bloques de la lista lb2 a la lista lb1*/
void devolverBloques(ListaBloques *lb1, ListaBloques *lb2){
	ListaBloques bloque;
	int hayBloque;

	do {
		obtenerBloque(lb2,&bloque);
		hayBloque = (bloque!=NULL);
		if (hayBloque) insertarBloque(lb1,bloque);
	}while(hayBloque);
}

/**
* A�ade un fichero al directorio, con nombre el indicado como segundo par�metro y tama�o del fichero
* el indicado en el tercer par�metro. Cada fichero nuevo se insertar� el comienzo de la lista y no es
* necesario que los nombres de los ficheros est�n ordenados.
*
* El tama�o del fichero podr� ser o no m�ltiplo de 512 bytes,
* aunque siempre se reservar�n bloques de 512 bytes, aunque el �ltimo no est� completo.
*
* Los bloques necesarios para guardar el fichero se obtendr�n de la lista de bloques libres que se
* proporciona como tercer par�metro.
*
* Si durante la creaci�n de un fichero nos quedamos sin espacio en la lista de bloques libres,
* entonces los bloques que ya hayamos reservado se devolver�n a la lista de bloques libres y el puntero
* a los bloques de memoria tendr� un valor NULL. El tama�o del fichero tambi�n se cambiar� para que sea 0.
*/
void nuevoFichero(ListaFicheros *lf, char *nombre, unsigned int tam, ListaBloques *lb){
	ListaFicheros nuevo;
	ListaBloques bloque;

	//Creamos el nodo
	crearNodoFichero(&nuevo,nombre,tam);
	//Lo insertamos al principio de la lista de ficheros
	if (*lf == NULL){
		*lf = nuevo;
	}else{
		nuevo->sig = *lf;
		*lf = nuevo;
	}

	//Ahora obtenemos y enlazamos los bloques de memoria para el fichero
	int bloques = tam/512;
	if (tam%512 != 0){
		bloques++;
	}

	int disponibles = 1;
	while (bloques != 0 && disponibles){
		obtenerBloque(lb,&bloque);
		if (bloque != NULL){
			insertarBloque(&(nuevo->bloques), bloque);
			bloques--;
		}else{
			disponibles = 0;
		}
	}
	if (disponibles == 0){
		//Hay que devolver todos los bloques a la lista de bloques libres
		devolverBloques(lb,&(nuevo->bloques));
		//El tama�o debe ser 0
		nuevo->tam = 0;
	}
}

/*
* Se leen todos los datos del directorio desde fichero de texto cuyo nombre se indica como primer par�metro y se
* crear� la lista enlazada con todos los ficheros en el segundo par�metro (lf).
* El fichero de entrada contendr� una l�nea por cada fichero en el directorio, con el siguiente formato:
*     <nombre_fichero> <tama�o en memoria>
*/
void crearDesdeFicheroTexto(char * nombre, ListaFicheros *lf, ListaBloques *lb){
	FILE *fent;
	int nleidos;
	char nomFic[30];
	unsigned int tam;

	fent = fopen(nombre,"rt");
	if (fent == NULL){
		perror("Error creando el fichero");
	}else{
		crearDirectorio(lf);
		while ((nleidos = fscanf(fent,"%s %d",nomFic,&tam)) == 2){
			nuevoFichero(lf,nomFic,tam,lb);
		}
		fclose(fent);
	}
}

/**
 * Se muestra por pantalla el contenido del directorio, con los datos de cada fichero en una l�nea distinta, con el siguiente formato:
*      <nombre> ( <dir1> <dir2> <dir3> � <dirN>)
 */
void imprimirDirectorio(ListaFicheros lf){
	if (lf != NULL){
		while (lf != NULL){
			printf("%s %6d",lf->nombre,lf->tam);
			imprimir(lf->bloques); //Hay que reutilizar las funciones del .h. No se puede "entrar" en el módulo
			lf = lf->sig;
		}
	}else{
		printf("Directorio vacio ...\n");
	}
}

/**
* Se borran todos los ficheros del directorio y los bloques de memoria que ocupan los ficheros borrados se devuelven al bloque
* de memoria libre
*/
void borrarDirectorio(ListaFicheros *lf, ListaBloques *lb){
	ListaFicheros borrar;

	while (*lf != NULL){
		borrar = *lf;
		*lf = (*lf)->sig;
		//Se mueven los bloques a la lista de bloques
		devolverBloques(lb,&(borrar->bloques)); //Hay que reutilizar las funciones del .h. No se puede "entrar" en el módulo
		free(borrar);
	}

	*lf = NULL;
}
