#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Directorio.h"

/**
 * crea un directorio vac�o
 */
void crearDirectorio(ListaFicheros* lf){
	*lf = NULL;
}

void devolverBloques(ListaBloques* lDevuelve, ListaBloques* lRecibe){
	ListaBloques bloqueDevolver;
	obtenerBloque(lDevuelve, &bloqueDevolver);
	while(bloqueDevolver!=NULL){
		insertarBloque(lRecibe, bloqueDevolver);
		obtenerBloque(lDevuelve, &bloqueDevolver);
	}
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
* entonces los bloques que ya hayamos reservado para el fichero se devolver�n a la lista de bloques libres y el puntero
* a los bloques de memoria del fichero tendr� un valor NULL. El tama�o del fichero tambi�n se cambiar� para que sea 0.
*/
void nuevoFichero(ListaFicheros *lf, char *nombre, unsigned int tam, ListaBloques *lb){
	ListaBloques bloque;
	int haySuficientes = 1;

	ListaFicheros nuevo = (ListaFicheros) malloc(sizeof(struct NodoFichero));
	if(nuevo==NULL){
		perror("No se pudo reservar memoria");
	}
	else{
		// copio el tama�o
		nuevo->tam = tam;
		// copio el nombre
		strcpy(nuevo->nombre, nombre);
		int tam_aux = (int) tam;	// para que pueda ser negativo
		crear(&(nuevo->bloques), 0);
		// copio los bloques que correspondan
		while(tam_aux>0 && haySuficientes){		// tengo que ver si me quedan bloques. Si no me quedan bloques, tengo que devolverlos todos
			obtenerBloque(lb, &bloque);
			if(bloque==NULL){
				haySuficientes = 0;
			}
			else{
				insertarBloque(&(nuevo->bloques), bloque);
				tam_aux-=512;
			}
		}

		// pongo sig a NULL por si acaso
		nuevo->sig = NULL;
	}

	// Inserto al principio
	if(*lf == NULL){
		*lf = nuevo;
	}
	else{
		nuevo->sig = *lf;
		*lf = nuevo;
	}

	// si no hay suficientes, tengo que devolver todos los bloques que habia metido en nuevo->bloques
	// y poner el tama�o a 0
	if(!haySuficientes){
		devolverBloques(&(nuevo->bloques), lb);
		nuevo->tam = 0;
	}
	

}

/*
* Se leen todos los datos del directorio desde el fichero de texto cuyo nombre se indica como primer par�metro y se
* crear� la lista enlazada con todos los ficheros (segundo par�metro (lf)). El tercer par�metro es la lista de
* bloques libres.
* El fichero de entrada contendr� una l�nea por cada fichero en el directorio, con el siguiente formato:
*     <nombre_fichero> <tama�o en memoria>
*/
void crearDesdeFicheroTexto(char * nombre, ListaFicheros *lf, ListaBloques *lb){
	FILE* fent;
	unsigned int tam;
	char nombreFich[30];

	fent = fopen(nombre, "rt");
	if(fent==NULL){
		perror("No se ha podido abrir el fichero\n");
	}
	else{
		crearDirectorio(lf);
		while(fscanf(fent, "%s %u", nombreFich, &tam) == 2){
			//printf("hola\n");
			nuevoFichero(lf, nombreFich, tam, lb);
		}
	}
	fclose(fent);

}

/**
* Se muestra por pantalla el contenido del directorio, con los datos de cada
* fichero en una l�nea distinta. Cada l�nea tiene el siguiente formato:
*
*      <nombre_fichero> <tam> ( <dir1> <dir2> <dir3> � <dirN>)
*/
void imprimirDirectorio(ListaFicheros lf){
	ListaFicheros current = lf;
	while(current!=NULL){
		printf("%s %u", current->nombre, current->tam);
		imprimir(current->bloques);
		printf("\n");

		current = current->sig;
	}
}
/**
 * Se borran todos los ficheros del directorio y los bloques de memoria que ocupan los ficheros borrados se devuelven al bloque
* de memoria libre (tercer par�metro)
 */
void borrarDirectorio(ListaFicheros *lf, ListaBloques *lb){
	while(*lf != NULL){
		ListaFicheros temp = *lf;
		*lf = (*lf)->sig;
		devolverBloques(&(temp->bloques), lb);
		free(temp);
	}
}
