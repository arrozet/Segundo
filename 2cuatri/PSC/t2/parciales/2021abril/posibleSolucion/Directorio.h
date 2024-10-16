/*
 * Directorio.h
 *
 *  Created on: 16 abr. 2021
 *      Author: Monica
 */

#ifndef DIRECTORIO_H_
#define DIRECTORIO_H_

#include "Bloques.h"

typedef struct NodoFichero *ListaFicheros;
struct NodoFichero {
	char nombre[30];		//Nombre del fichero
	unsigned int tam;		//Tamaño del fichero
	ListaBloques bloques;	//Puntero a la lista de bloques que ocupa el fichero en memoria
	ListaFicheros sig;		//Puntero al siguiente nodo en la lista (siguiente fichero)
};

/**
 * crea un directorio vacío
 */
void crearDirectorio(ListaFicheros* lf);

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
void nuevoFichero(ListaFicheros *lf, char *nombre, unsigned int tam, ListaBloques *lb);

/*
* Se leen todos los datos del directorio desde el fichero de texto cuyo nombre se indica como primer parámetro y se
* creará la lista enlazada con todos los ficheros (segundo parámetro (lf)). El tercer parámetro es la lista de
* bloques libres.
* El fichero de entrada contendrá una línea por cada fichero en el directorio, con el siguiente formato:
*     <nombre_fichero> <tamaño en memoria>
*/
void crearDesdeFicheroTexto(char * nombre, ListaFicheros *lf, ListaBloques *lb);

/**
* Se muestra por pantalla el contenido del directorio, con los datos de cada
* fichero en una línea distinta. Cada línea tiene el siguiente formato:
*
*      <nombre_fichero> <tam> ( <dir1> <dir2> <dir3> … <dirN>)
*/
void imprimirDirectorio(ListaFicheros lf);
/**
 * Se borran todos los ficheros del directorio y los bloques de memoria que ocupan los ficheros borrados se devuelven al bloque
* de memoria libre (tercer parámetro)
 */
void borrarDirectorio(ListaFicheros *lf, ListaBloques *lb);

#endif /* DIRECTORIO_H_ */
