#include "Lista.h"

Lista crear(){
    return NULL; //Se devuelve NULL para crear una lista vacía
}

//Comprobar si la lista es una lista vacía
int estaVacia(Lista l){
    return (l == NULL);
}

//función privada
Lista crearNodo(int elem){
    //Creamos memoria para un nuevo nodo
    Lista nuevo = (Lista) malloc(sizeof(struct NodoLista));
    if (nuevo != NULL){ //Comprobamos si la memoria se ha creado correctamente
        nuevo->dato = elem; 
        nuevo->sig = NULL;
    }
    return nuevo; //Si no puede reservarse memoria se devuelve NULL
}

//Insertar ordenado. Inserta el elemento aunque ya exista en la lista
void insertarOrdenado(Lista *l,int elem){
    Lista nuevo = crearNodo(elem);
    insertarNodoOrdenado(l,nuevo);
}

//Asumimos que el nodo que se pasa existe
void insertarNodoOrdenado(Lista *l, Lista elem){
    Lista ptr, ant; //punteros auxiliares

    ptr = *l;   
    ant = NULL; 

    while (ptr != NULL && ptr->dato < elem->dato){
        ant = ptr;
        ptr = ptr->sig;
    }

    if (ant == NULL){
        if (ptr == NULL){
            *l = elem; 
        }else{
            elem->sig = ptr;
            *l = elem;
        }
    }else{
        //inserta en medio o al final
        ant->sig = elem;
        elem->sig = ptr;
    }    
}

//Elimina un elemento de una lista ordenada
void eliminarOrdenado(Lista *l,int elem){
    if (*l != NULL){ //Si es vacía no hay que hacer nada
        Lista ptr, ant;

        ptr = *l;
        ant = NULL;

        //Buscamos el elemento a eliminar
        while (ptr != NULL && ptr->dato < elem){
            ant = ptr;
            ptr = ptr->sig;
        }

        /*
            ptr->dato == elem --> Elimino el elemento
            ptr->dato != elem --> El elemento no está en la lista
        */
        if (ptr != NULL && ptr->dato == elem){ //Si lo ha encontrado ptr != NULL y ptr->dato tiene el elem a eliminar
            if (ant == NULL) {
                *l = (*l) -> sig;
                free(ptr);
            }else{
                //eliminamos del medio o del final
                ant->sig = ptr->sig;
                free(ptr);
            }
        }
    }
}

Lista sacar(Lista *l){
    Lista dato = *l;

    if (*l != NULL){
        *l = (*l)->sig;
        dato->sig = NULL; //Cuidado. Hay que poner a NULL el campo sig. Sino enlaza con el resto de la lista
    }
    return dato;
}

void mostrar(Lista l){
    Lista ptr; 

    printf("El contenido es: ");
    ptr = l;
    while(ptr!=NULL){
        printf("%d ",ptr->dato);
        ptr = ptr->sig;
    }
    printf("\n");
}

void destruir(Lista *l){
    Lista ptr;

    while (*l != NULL){
        ptr = *l;
        *l = (*l) -> sig;
        free(ptr);
    }
}