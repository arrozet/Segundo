/*
 * Bloques.h
 *
 *  Created on: 16 abr. 2021
 *      Author: Monica
 */

#ifndef BLOQUES_H_
#define BLOQUES_H_

typedef struct Nodo *ListaBloques;
struct Nodo {
	unsigned int dirInicio;  //Direccion de inicio del bloque
	ListaBloques sig;        //Puntero al siguiente nodo en la lista
};

/**
* Crea una lista de bloques de 512 bytes cada uno, para una cantidad de memoria
*  en bytes proporcionada como parámetro de entrada.
*  El primer bloque de la lista tendrá como dirección 0, el segundo 512, el tercero 1024
*  y así sucesivamente.
*
*  Se puede asumir que tamMemoria es un valor múltiplo de 512.
*
*  Si tamMemoria es 0 entonces se creará una lista vacia.
*/
void crear(ListaBloques *lb, unsigned int tamMemoria);

/**
* Se obtiene un bloque de la lista de bloques libres.
* El primer parámetro es la lista de bloques libres y el segundo parámetro es el bloque
* que será devuelto por la función.
*
* - Si la lista de bloques está vacía el segundo parámetro será NULL.
* - Si la lista de bloques no está vacía se devolverá el primer bloque libre, sacándolo de
*   la lista y devolviendo en el segundo parámetro
*
* ¡CUIDADO¡ Sacar el bloque de la lista no significa liberar la memoria de ese bloque, sino
* que el nodo deja de formar parte de la lista
*/
void obtenerBloque(ListaBloques *lb, ListaBloques *bloque);

/**
 * Se inserta un bloque en la lista de bloques libres
 * El primer parámetro es la lista de bloques libres y el segundo parámetro es el bloque
 * que debe volver a insertarse en la lista.
 * La inserción se realizará de forma ordenada por la dirección de inicio del bloque a devolver.
 *
 * ¡CUIDADO¡ El nodo que se quiere devolver a la lista ya existe (bloque es un puntero que
 * apunta a ese nodo). No hay que crear el nodo reservando memoria para él, solo volver a
 * incorporarlo a la lista.
*/
void insertarBloque(ListaBloques *lb, ListaBloques bloque);

/**
 * Escribe por pantalla la información de cada uno de los bloques libres
 * almacenados en la lista.
 *
 * El formato de salida debe ser: (<dir1> <dir2> <dir3> … <dirN>)
 */
void imprimir(ListaBloques lb);

/**
 * Borra todos los nodos de la lista y la deja vacía
 */
void borrar(ListaBloques *lb);


#endif /* BLOQUES_H_ */
