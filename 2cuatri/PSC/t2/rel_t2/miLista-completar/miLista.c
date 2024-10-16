#include "miLista.h"

void crearLista(Lista* l){
    *l=NULL;
}

Lista crearLista2(){
    return NULL;
}

void mostrarLista(Lista l){     // se está pasando por valor, puedo iterar directamente sobre l
    while(l!=NULL){
        printf("%d ", l->num);
        l = l->sig;
    }
}

void destruir(Lista* l){
    Lista this;
    while(*l != NULL){
        this = *l;
        l = (*l)->sig;
        free(this);
    }
}

void rellenar(Lista* l){
    
}