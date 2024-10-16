#ifndef __LISTA_H__
#define __LISTA_H__

#include <stdio.h>
#include <stdlib.h>

//Definición tipos para lista enlazada
typedef struct NodoLista * Lista;
struct NodoLista{
    int dato;
    Lista sig;
};

//Cabecera de las funciones para trabajar con listas enlazadas
Lista crear();
int estaVacia(Lista l);
void insertarNodoOrdenado(Lista *l, Lista elem); //insertar un nodo en una lista ordenada
void insertarOrdenado(Lista *l,int elem); //insertar en una lista ordenada. Implementar primero el insertarNodoOrdenado
void eliminarOrdenado(Lista *l,int elem); //eliminar de una lista ordenada
Lista sacar(Lista *l); //saca el primer elemento de la lista y devuelve el nodo extraído
void mostrar(Lista l);
void destruir(Lista *l);

#endif