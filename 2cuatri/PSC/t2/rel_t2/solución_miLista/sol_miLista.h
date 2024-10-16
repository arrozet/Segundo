#ifndef __MILISTA_H__
#define __MILISTA_H__

#include <stdio.h>
#include <stdlib.h>

//Definición tipos para lista enlazada
typedef struct NodoLista * Lista;
struct NodoLista{
    int dato;
    Lista sig;
};

//Cabecera de las funciones para trabajar con listas enlazadas

//Dos formas distintas de crear una lista vacía
void crear(Lista *l);
Lista crearV2();

//Comprobar si la lista es una lista vacía
int estaVacia(Lista l);

//Varias operaciones para insertar
void insertar(Lista *l,int elem); //insertar principio
void insertarFinal(Lista *l, int elem); //insertar al final
void insertarOrdenado(Lista *l,int elem); //insertar en una lista ordenada
void insertarOrdenadoSinRepeticiones(Lista *l, int elem); //si el elemento está no lo inserta

//Varias operaciones para eliminar
void eliminar(Lista *l); //elimina del principio
Lista sacar(Lista *l); //elimina del principio y devuelve el nodo extraído de la lista. 
void eliminarElem(Lista *l,int elem); //eliminar de una lista no ordenada una ocurrencia de elem
Lista sacarElem(Lista *l, int elem); //elimina de una lista no ordenada y devuelve el nodo extraído de la lista
void eliminarMultiplesElem(Lista *l, int elem); //elimina de una lista no ordenada todas las ocurrencias de elem
void eliminarOrdenado(Lista *l,int elem); //eliminar de una lista ordenada
Lista sacarOrdenado(Lista *l, int elem); //elimina de una lista ordenada y devuelve el nodo extraído de la lista
void eliminarOrdenadoMultiplesElem(Lista *l, int elem); //elimina de una lista ordenada todas las ocurrencias de elem

//Varias operaciones de recorrido de la lista (la lista se pasa por valor)
void mostrar(Lista l);
int buscarElementoV1(Lista l,int elem);
Lista buscarElementoV2(Lista l, int elem); //Devuelve el nodo de la lista donde está el elemento o NULL si no está

//Liberar toda la memoria reservada
void destruir(Lista *l);

#endif