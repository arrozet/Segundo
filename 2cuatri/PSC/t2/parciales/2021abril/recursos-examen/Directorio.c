#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Directorio.h"

/**
 * crea un directorio vacío
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
* Añade un fichero al directorio, con nombre el indicado como segundo parámetro y tamaño del fichero
* el indicado en el tercer parámetro. Cada fichero nuevo se insertará el comienzo de la lista y no es
* necesario que los nombres de los ficheros estén ordenados.
*
* El tamaño del fichero podrá ser o no múltiplo de 512 bytes,
* aunque siempre se reservarán bloques de 512 bytes, aunque el último no esté completo.
*
* Los bloques necesarios para guardar el fichero se obtendrán de la lista de bloques libres que se
* proporciona como tercer parámetro.
*
* Si durante la creación de un fichero nos quedamos sin espacio en la lista de bloques libres,
* entonces los bloques que ya hayamos reservado para el fichero se devolverán a la lista de bloques libres y el puntero
* a los bloques de memoria del fichero tendrá un valor NULL. El tamaño del fichero también se cambiará para que sea 0.
*/
void nuevoFichero(ListaFicheros *lf, char *nombre, unsigned int tam, ListaBloques *lb){
	ListaBloques bloque;
	int haySuficientes = 1;

	ListaFicheros nuevo = (ListaFicheros) malloc(sizeof(struct NodoFichero));
	if(nuevo==NULL){
		perror("No se pudo reservar memoria");
	}
	else{
		// copio el tamaño
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
	// y poner el tamaño a 0
	if(!haySuficientes){
		devolverBloques(&(nuevo->bloques), lb);
		nuevo->tam = 0;
	}
	

}

/*
* Se leen todos los datos del directorio desde el fichero de texto cuyo nombre se indica como primer parámetro y se
* creará la lista enlazada con todos los ficheros (segundo parámetro (lf)). El tercer parámetro es la lista de
* bloques libres.
* El fichero de entrada contendrá una línea por cada fichero en el directorio, con el siguiente formato:
*     <nombre_fichero> <tamaño en memoria>
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
* fichero en una línea distinta. Cada línea tiene el siguiente formato:
*
*      <nombre_fichero> <tam> ( <dir1> <dir2> <dir3> … <dirN>)
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
* de memoria libre (tercer parámetro)
 */
void borrarDirectorio(ListaFicheros *lf, ListaBloques *lb){
	while(*lf != NULL){
		ListaFicheros temp = *lf;
		*lf = (*lf)->sig;
		devolverBloques(&(temp->bloques), lb);
		free(temp);
	}
}
