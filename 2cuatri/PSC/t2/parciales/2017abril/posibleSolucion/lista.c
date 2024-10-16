#include "lista.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/*typedef struct item * Lista;

struct item {
	char palabra[TAM_PAL];
	Lista sig;
};*/

/*
 * Crea un a lista vacia
 */
void crear(Lista *l){
    *l=NULL;
}

/*
 * Compruba si un a lista esta vacía
 * Devuelve 0 si NO lo está
 */
int lista_vacia(Lista l){
    return (l == NULL);
}

/*
 * Escribe en consola el contenido de una lista de palabras separadas por coma
 * l: lista enlazada de palabras
 */
void escribir(Lista l){
    Lista ptr = l;
    while(ptr != NULL){
        if (ptr->sig == NULL){
            printf("%s",ptr->palabra);
        }else{
            printf("%s, ",ptr->palabra);
        }
        ptr=ptr->sig;
    }
    printf("\n");
}

/*
 * Escribe en un fichero de salida el contenido de una lista de palabras separadas por coma
 * fp: Puntero a un objeto FILE que identifica el stream de salida
 * l: lista enlazada de palabras
 */
void escribir_fichero(FILE * fp, Lista l){
    Lista ptr = l;
    while(ptr != NULL){
        if (ptr->sig == NULL){
            fprintf(fp,"%s",ptr->palabra);
        }else{
            fprintf(fp,"%s, ",ptr->palabra);
        }
        ptr=ptr->sig;
    }
    fprintf(fp,"\n");
}

//Función auxiliar
void crearNodo(Lista *nuevo, char*palabra){
    *nuevo = malloc(sizeof(struct item));
    strcpy((*nuevo)->palabra,palabra);
    (*nuevo)->sig = NULL;
}
/*
 * Inserta una palabra al final de una lista enlazada.
 * No comprueba si la palabra existe, si se desea no repetir palabras
 * se debe utilizar buscar_palabra() y comprobar antes de invocar esta función
 * palabra: la palabra que se desea insertar
 * l: lista enlazada de palabras
 */
void insertar(char* palabra, Lista* l){
    Lista ptr = *l, nuevo;
    crearNodo(&nuevo,palabra);
    if (ptr == NULL){
        *l = nuevo;
    }else{
        while(ptr->sig != NULL){
            ptr = ptr->sig;
        }
        ptr->sig = nuevo;
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
    Lista borrar = *l;
    while (borrar != NULL){
        *l = (*l)->sig;
        free(borrar);
        borrar = *l;
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
    Lista ptr = l;
    while (ptr!=NULL && strcmp(palabra,ptr->palabra)!=0){
        ptr = ptr->sig;
    }
    return (ptr != NULL);
}