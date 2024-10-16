#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "arbolbb.h"

// Crea la estructura utilizada para gestionar la memoria disponible.
void Crear(T_Arbol* arbol){
    *arbol = NULL;
}   

// Destruye la estructura utilizada.
void Destruir(T_Arbol* arbol){
    if(*arbol!=NULL){
        Destruir(&(*arbol)->izq);
        Destruir(&(*arbol)->der);
        free(*arbol);
        *arbol = NULL;
    }
}

// Inserta num en el arbol
void Insertar(T_Arbol* arbol,unsigned num){
    if(*arbol==NULL){
        *arbol = (T_Arbol) malloc(sizeof(struct T_Nodo));
        if(*arbol==NULL){
            perror("Insertar: error al alocar memoria");
        }
        else{
            (*arbol)->dato=num;
            (*arbol)->izq = NULL;
            (*arbol)->der = NULL;
        }

    }
    else if((num<(*arbol)->dato)){
        Insertar(&(*arbol)->izq, num);
    }
    else if(num>((*arbol)->dato)){
        Insertar(&(*arbol)->der, num);
    }
}
// Muestra el contenido del �rbol en InOrden
void Mostrar(T_Arbol arbol){
    // printf("Contenido del arbol inOrder:\n");
    if(arbol!=NULL){
        Mostrar(arbol->izq);
        printf("%u ", arbol->dato);
        Mostrar(arbol->der);
    }
    
    //printf("\n");
}

// Guarda en disco el contenido del arbol
void Salvar(T_Arbol arbol, FILE* fichero){
    if(arbol!=NULL){
        Salvar(arbol->izq, fichero);
        fwrite(&(arbol->dato),sizeof(unsigned),1,fichero);
        Salvar(arbol->der, fichero);
    }   
}