/*
 * lista.h
 *
 *  Created on: 17 abr. 2017
 *      Author: Manuel F. Bertoa
 */
#include <stdio.h>
#include "lista.h"
#include <stdlib.h>
#include <string.h>

/*
 * Crea un a lista vacia
 */
void crear(Lista *l){
    *l = NULL;
}

/*
 * Compruba si un a lista esta vacía
 * Devuelve 0 si NO lo está
 */
int lista_vacia(Lista l){
    return l == NULL;
}

/*
 * Escribe en consola el contenido de una lista de palabras separadas por coma
 * l: lista enlazada de palabras
 */
void escribir(Lista l){
    if(!lista_vacia(l)){
        while(l != NULL){
            printf("%s, ", l->palabra);
            l=l->sig;
        }
    }
    printf("\n");
    
}

/*
 * Escribe en un fichero de salida el contenido de una lista de palabras separadas por coma
 * fp: Puntero a un objeto FILE que identifica el stream de salida
 * l: lista enlazada de palabras
 */
void escribir_fichero(FILE * fp, Lista l){
    while(l!=NULL){
        fprintf(fp, "%s, ", l->palabra);
        l=l->sig;
    }
    fprintf(fp, "\n");
}

Lista crearNodo(char* palabra){
    Lista nodo = (Lista) malloc(sizeof(struct item));
    if(nodo!=NULL){
        nodo->sig=NULL;
        strcpy(nodo->palabra, palabra);
    }
    else{
        perror("No se pudo reservar memoria\n");
    }

    return nodo;
}

/*
 * Inserta una palabra al final de una lista enlazada.
 * No comprueba si la palabra existe, si se desea no repetir palabras
 * se debe utilizar buscar_palabra() y comprobar antes de invocar esta función
 * palabra: la palabra que se desea insertar
 * l: lista enlazada de palabras
 */
void insertar(char* palabra, Lista* l){
    if(buscar_palabra(palabra,*l) == 0){

        Lista current = *l;
        if(current!=NULL){
            while(current->sig!=NULL){
                current=current->sig;
            }

            Lista nodo = crearNodo(palabra);
            current->sig = nodo;
        }
        else{
            Lista nodo = crearNodo(palabra);
            *l = nodo;
        }
        
    }
}

/*
 * Elimina todos los items de la lista enlazada
 * Debe delvolver la memoria dinamica utilizada para cada uno de ellos
 * Para comprobar que se eliminan los items
 * escriba un mensaje por consola indicando la palabra de item que se va a eliminar
 * l: La lista enlazada que se desea eliminar
 */
void destruir(Lista* l){
    while(*l!=NULL){
        Lista temp = *l;
        printf("Item eliminado: %s\n", temp->palabra);
        *l=(*l)->sig;
        free(temp);
    }
}


/*
 * Comprueba si una palabra esta en la lista enlazada
 * palabra: la palabra que se desea buscar
 * l: lista enlazada de palabras
 * Devuelve 0 si la palabra NO está en la lista
 * Devuelve 1 si la palabra Sí está en la lista
 */
int buscar_palabra(char* palabra, Lista l){
    if(l!=NULL){
        while(l != NULL && strcmp(l->palabra,palabra) != 0){
            l=l->sig;
        }
        if(l==NULL){
            return 0;
        }
        else{
            return 1;
        }
    }
    return 0;
}
