#include "Lista.h"

Lista crear(){
    return NULL;
}

//Comprobar si la lista es una lista vacía
int estaVacia(Lista l){
    return (l == NULL);
}

//función privada
Lista crearNodo(int elem){
    Lista nodo = (Lista) malloc(sizeof(struct NodoLista));
    if(nodo!=NULL){
        nodo->sig = NULL;
        nodo->dato = elem;
    }
    else{
        perror("No se pudo reservar memoria");
        fflush(stdout);
    }
    

    return nodo;

}

//Asumimos que el nodo que se pasa existe
void insertarNodoOrdenado(Lista *l, Lista elem){
    Lista current = *l;
    Lista previous = NULL;
    elem->sig=NULL; // por si acaso

    while(current!=NULL && current->dato < elem->dato){
        previous = current;
        current = current->sig;
    }

    if(current==NULL && previous==NULL){    // la lista está vacia
        *l = elem;
    }
    else if(previous == NULL){  // estoy al principio
        *l = elem;
        elem->sig = current;
    }
    else{   // si current es NULL -estoy en el final- esto también funciona (además de en el caso general)
        previous->sig = elem;
        elem->sig = current;
    }
}

//Insertar ordenado. Inserta el elemento aunque ya exista en la lista
void insertarOrdenado(Lista *l,int elem){
    Lista nodo = crearNodo(elem);
    insertarNodoOrdenado(l,nodo);
}

//Elimina un elemento de una lista ordenada
void eliminarOrdenado(Lista *l,int elem){
    Lista current = *l;
    Lista previous = NULL;

    while(current!=NULL && current->dato < elem){
        previous = current;
        current = current->sig;
    }

    // si existe el dato
    if(current != NULL && current->dato == elem){
        if(previous == NULL){   // es el primer dato
            *l = current->sig;
        }
        else{                   // no es el primer dato
            previous->sig = current->sig;
        }
        free(current);
    }

}

Lista sacar(Lista *l){
    Lista nodo = *l;
    if(*l != NULL){
        *l=(*l)->sig;
        nodo->sig = NULL;   // para no enlazar con el resto de la lista. la asignación asigna lo que hubiese en ese preciso momento
    }
    

    return nodo;
}

void mostrar(Lista l){
    printf("El contenido de la lista es:\n");
    while(l!=NULL){
        printf("%d ", l->dato);
        l=l->sig;
    }
    printf("\n");
}

void destruir(Lista *l){
    while(*l!=NULL){
        Lista temp = *l;
        *l = (*l)->sig;
        free(temp);
    }
}