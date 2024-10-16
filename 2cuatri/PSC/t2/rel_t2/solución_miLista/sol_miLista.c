#include "sol_miLista.h"

// Dos formas distintas de crear una lista vacía
void crear(Lista *l){ //La lista se pasa como parámetro 
    *l = NULL;
}

Lista crearV2(){
    return NULL; //Se devuelve NULL para crear una lista vacía
}

//Comprobar si la lista es una lista vacía
int estaVacia(Lista l){
    return (l == NULL);
}

//Funcion privada para crear un nodo nuevo para usar en las operaciones de insertar
void crearNodoV2(Lista *nuevo, int elem){ 
    *nuevo = (Lista) malloc(sizeof(struct NodoLista));
    if (*nuevo != NULL){
        (*nuevo)->dato = elem;
        (*nuevo)->sig = NULL;
        /* Si no quiero usar la notación ->, sería: 
        (*(*nuevo)).dato = elem;
        (*(*nuevo)).sig = NULL;
        */
    }
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

/* Varias operaciones para insertar
    - Usamos la función privada crearNuevo. 
      Si no se ha podido reservar memoria esta función devuelve NULL 
*/
//Insertar al principio
void insertar(Lista *l, int elem){
    //Creamos memoria para un nuevo nodo
    Lista nuevo = crearNodo(elem); //Devuelve NULL si no ha podido reservar memoria
    //Lista nuevo; crearNodoV2(&nuevo,elem);

    if (nuevo != NULL){ //Si queremos hacerlo mas robusto comprobamos que se haya podido reservar memoria
        if (*l == NULL){ //la lista está vacía
            *l = nuevo;
        }else{ //la lista no está vacía
            nuevo->sig = *l;
            *l = nuevo;
        }
    }
}

//Insertar al final
void insertarFinal(Lista *l, int elem){
    //El puntero auxiliar se tiene que parar antes de salirse de la lista
    //Tiene que quedarse en el último nodo de la lista
    Lista ptr, nuevo;

    nuevo = crearNodo(elem); //Creo memoria para el nuevo nodo
    if (nuevo != NULL){
        ptr = *l;

        if (ptr == NULL){ //la lista está vacía
            *l = nuevo;   //Tengo que modificar *l, si modifico ptr no estoy añadiendo el nodo a la lista
        }else{
            //Me muevo al final de la lista
            //Puedo usar ptr->sig porque se que en este punto ptr nunca será NULL
            while (ptr->sig != NULL){
                ptr = ptr->sig;
            }
            ptr->sig = nuevo; //enlazo el nuevo nodo al final de la lista.
        }
    }
}

//Insertar ordenado. Inserta el elemento aunque ya exista en la lista
void insertarOrdenado(Lista *l,int elem){
    Lista ptr, ant; //punteros auxiliares

    ptr = *l;   //Lo inicializo apuntando al primer nodo de la lista
    ant = NULL; //Siempre va "detras" de ptr. Si ptr apunta al primer nodo, ant es NULL

    //Busco donde tendría que insertar para que la lista siga ordenada
    while (ptr != NULL && ptr->dato < elem){
        ant = ptr;
        ptr = ptr->sig;
    }

    /* Al salir de la búqueda
      ant == NULL --> Tengo que insertar al comienzo de la lista, que podrá estar vacía (ptr == NULL) o no
      ant != NULL --> Tengo que insertar en medio de la lista (ptr !=NULL) o al final (ptr == NULL)
    */
    if (ant == NULL){
        insertar(l,elem);  //Reutilizo el insertar al principio.

        /* Código alternativo si no se reutiliza en insertar al principio
        Lista nuevo = crearNodo(elem); //Creo memoria para el nuevo nodo
        if (ptr == NULL){
            //insertar al principio
            *l = nuevo; 
        }else{
            nuevo->sig = *l;
            *l = nuevo;
        }
        */
    }else{
        Lista nuevo = crearNodo(elem); //Creo memoria para el nuevo nodo
        //inserta en medio o al final
        ant->sig = nuevo;
        nuevo->sig = ptr;
        
        /* Código alternativo desglosado para insertar en medio (ptr != NULL) o al final (ptr == NULL)
        if (ptr != NULL){
            //insertar en medio
            ant->sig = nuevo;
            nuevo->sig = ptr;
        }else{
            //insertar al final
            ant->sig = nuevo;
        }*/
    }
}

//Insertar ordenado sin elementos repetidos
void insertarOrdenadoSinRepeticiones(Lista *l,int elem){
    Lista ptr, ant; 

    ptr = *l;   
    ant = NULL;

    //Busco donde tendría que insertar para que la lista siga ordenada
    while (ptr != NULL && ptr->dato < elem){
        ant = ptr;
        ptr = ptr->sig;
    }

    /* Al salir de la búqueda
      ptr->dato == elem --> El elemento ya está en la lista y no hay que insertarlo
      ant == NULL --> Tengo que insertar al comienzo de la lista, que podrá estar vacía (ptr == NULL) o no
      ant != NULL --> Tengo que insertar en medio de la lista (ptr !=NULL) o al final (ptr == NULL)
    */
    if (!(ptr!=NULL && ptr->dato == elem)){ //Cuidado con asegurarse que ptr es != NULL antes de acceder a ptr->dato
        if (ant == NULL) insertar(l,elem);  //Reutilizo el insertar al principio.
        else{
            Lista nuevo = crearNodo(elem); //Creo memoria para el nuevo nodo
            ant->sig = nuevo;
            nuevo->sig = ptr;
        }
    }
}

//Varias operaciones para eliminar
//Elimina el nodo en el comienzo de la lista
void eliminar(Lista *l){
    if ((*l) != NULL){
        Lista ptr = *l;
        *l = (*l)->sig;
        free(ptr);
    }
}

/* Elimina del principio y devuelve el nodo extraído de la lista.
    - En este caso la memoria no se libera, porque extraemos el nodo de la lista
      y lo devolvemos
    - Devuelve NULL si la lista estaba vacía
*/
Lista sacar(Lista *l){
    Lista ptr = *l;
    
    if (*l != NULL){
        *l = (*l)->sig;
        ptr->sig = NULL;
    }
    
    return ptr;
} 

//Elimina un elemento de una lista no ordenada
void eliminarElem(Lista *l,int elem){ 
    if (*l != NULL){ //Si es vacía no hay que hacer nada
        Lista ptr, ant;

        ptr = *l;
        ant = NULL;

        //Buscamos el elemento a eliminar
        while (ptr != NULL && ptr->dato != elem){
            ant = ptr;
            ptr = ptr->sig;
        }

        if (ptr != NULL){ //Si lo ha encontrado ptr != NULL
            if (ant == NULL) eliminar(l); //Elimino del principio de la lista
            else{
                //eliminamos del medio o del final
                ant->sig = ptr->sig;
                free(ptr);
            }
        }
    }
}

Lista sacarElem(Lista *l, int elem){
    Lista dato = NULL;
    if (*l != NULL){ //Si es vacía no hay que hacer nada
        Lista ptr, ant;

        ptr = *l;
        ant = NULL;

        //Buscamos el elemento a sacar
        while (ptr != NULL && ptr->dato != elem){
            ant = ptr;
            ptr = ptr->sig;
        }

        if (ptr != NULL){ //Si lo ha encontrado ptr != NULL
            if (ant == NULL)
                dato = sacar(l); //Saco del principio de la lista
            else{
                //sacamos del medio o del final
                ant->sig = ptr->sig;
                dato = ptr;
            }
        }
    }    
    return dato;
}

//Elimina todas las ocurrencias de un elemento en una lista no ordenada
void eliminarMultiplesElem(Lista *l,int elem){ 
    if (*l != NULL){ //Si es vacía no hay que hacer nada
        Lista ptr, ant;

        ptr = *l;
        ant = NULL;

        //Buscamos el elemento a eliminar y si lo encontramos lo eliminamos y seguimos buscando
        while (ptr != NULL){
            if (ptr->dato != elem){ //avanzamos al siguiente elemento
                ant = ptr;
                ptr = ptr->sig;
            }else{ //Hemos encontrado el elemento y lo borramos
                /* Este if es muy parecido que el que elimina una ocurrencia, pero ...
                    - lo hemos movido dentro del bucle while que hace la búsqueda
                    - una vez borrado el nodo, colocamos ant y ptr correctamente para seguir buscando
                */
                if (ant == NULL) {
                    eliminar(l); //Elimino del principio de la lista
                    //Coloco los punteros en la situación inicial. ant ya tiene valor NULL
                    ptr = *l; 
                }else{
                    //eliminamos del medio o del final
                    ant->sig = ptr->sig;
                    free(ptr);
                    //Coloco los punteros. ant ya tiene el valor correcto
                    ptr = ant->sig;
                }
            }
        }
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
            if (ant == NULL) eliminar(l); //Elimino del principio de la lista
            else{
                //eliminamos del medio o del final
                ant->sig = ptr->sig;
                free(ptr);
            }
        }
    }
}

Lista sacarOrdenado(Lista *l, int elem){
    Lista dato = NULL;
    if (*l != NULL){ //Si es vacía no hay que hacer nada
        Lista ptr, ant;

        ptr = *l;
        ant = NULL;

        //Buscamos el elemento a sacar
        while (ptr != NULL && ptr->dato < elem){
            ant = ptr;
            ptr = ptr->sig;
        }

        /*
            ptr->dato == elem --> Saco el elemento
            ptr->dato != elem --> El elemento no está en la lista
        */
        if (ptr != NULL && ptr->dato == elem){ //Si lo ha encontrado ptr != NULL y ptr->dato tiene el elem a eliminar
            if (ant == NULL) 
                dato = sacar(l); //Saco del principio de la lista
            else{
                //sacamos del medio o del final
                ant->sig = ptr->sig;
                dato = ptr;
            }
        }
    }
    return dato;
}

//Elimina todas las ocurrencias de un elemento en una lista ordenada
void eliminarOrdenadoMultiplesElem(Lista *l,int elem){ 
    if (*l != NULL){ //Si es vacía no hay que hacer nada
        Lista ptr, ant;

        ptr = *l;
        ant = NULL;

        //Buscamos el elemento a eliminar. Si hay mas de uno estarán consecutivos en la lista porque está ordenada
        while (ptr != NULL && ptr->dato < elem){
            ant = ptr;
            ptr = ptr->sig;
        }

        while (ptr!= NULL && ptr->dato == elem){
            //Mientras encontremos el dato en la lista lo eliminamos
            //Siguiendo el mismo criterio de los punteros ant y ptr del eliminarOrdenado
            //Y colocando los punteros una vez eliminado un nodo
            if (ant == NULL) {
                eliminar(l); //Elimino del principio de la lista
                //Coloco los punteros en la situación inicial. ant ya tiene valor NULL
                ptr = *l; 
            }else{
                //eliminamos del medio o del final
                ant->sig = ptr->sig;
                free(ptr);
                //Coloco los punteros. ant ya tiene el valor correcto
                ptr = ant->sig;
            }
        }
    }
}

//Varias operaciones de recorrido de la lista (la lista se pasa por valor)
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

/* Busca un elemento en la lista
    - Devuelve 1 si el elemento está en la lista
    - Devuelve 0 si el elemento no está
*/
int buscarElementoV1(Lista l,int elem){
    //Lo puedo implementar recorriendo la lista directamente con l porque se ha pasado por valor
    while (l != NULL && l->dato != elem){
        l = l->sig;
    }
    return (l != NULL);
}

/* Se usa un puntero auxiliar para recorrer la lista
int buscarElementoV1(Lista l,int elem){
    Lista ptr = l;

    while (ptr != NULL && ptr->dato != elem){
        ptr = ptr->sig;
    }
    return (ptr != NULL);
}
*/

/* Busca un elemento en la lista
    - Devuelve NULL si el elemento no está
    - Devuelve un puntero al nodo en el que está almacenado elem si lo encuentra
    - No extrae el elemento de la lista. Solo devuelve un puntero al nodo donde se encuentra elem
*/
Lista buscarElementoV2(Lista l, int elem){
    Lista ptr = l;
    
    while (ptr != NULL && ptr->dato != elem){
        ptr = ptr->sig;
    }
    return ptr; //Devuelve NULL o un puntero a un nodo de la lista
} 

//Liberar toda la memoria reservada
/*void destruir(Lista *l){
    Lista ptr;

    while (*l != NULL){
        ptr = *l;
        *l = (*l) -> sig;
        free(ptr);
    }
    //Aquí *l es NULL
}*/

/*
void destruir(Lista *l){
    Lista ptr = *l;

    while (ptr != NULL){
        *l = *l -> sig;
        free(ptr);
        ptr = *l;
    }
    //Aquí *l es NULL
} 
*/


void destruir(Lista *l){
    while (*l != NULL){
        eliminar(l); //Reutilizo el eliminar un nodo del principio de la lista
        /* Paso l y no *l porque l ya es un puntero a un puntero (struct NodoLista ** l)
           y eliminar pide como argumento exactamente eso */
    }
    //Aquí *l es NULL
} 
