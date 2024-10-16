#ifndef __MILISTA_H__
#define __MILISTA_H__

#include <stdio.h>  //Entrada-salida
#include <stdlib.h> //Memoria dinamica

//Definición tipos para lista enlazada
typedef struct NodoLista* Lista;
struct NodoLista{
    int num;
    Lista sig;
};


//Cabecera de las funciones para trabajar con listas enlazadas

//Operaciones de creacion de la lista
void crearLista(Lista* l);
Lista crearLista2();

void destruir(Lista* l);
void rellenar(Lista* l);
void escribirF(char* nombre, Lista l);
void leerDeFichero(char* nombre, Lista *l);

//Operaciones para insertar elementos en la lista

//Operaciones para borrar elementos de la lista

//Operaciones de busqueda sobre la lista
void mostrarLista(Lista l);

//Operaciones de liberacion de la memoria dinamica de la lista

#endif