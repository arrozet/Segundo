#include <stdio.h>
#include "Lista.h"

/* Se pide implementarlo a nivel del programa que usa el módulo
   No se puede acceder a las listas a través de la estructura de
   punteros (podemos asumir que no sabemos como están implementadas)
   
   Hay que resolverlo accediendo solo a las funciones definidas
   en la librería. No se puede reservar o liberar memoria

   Ordenar las listas l1 y l2 de forma que:
   - l1 contenga la lista resultante de la ordenación
   - l2 al terminar sea una lista vacía
*/
void ordenarListas(Lista *l1, Lista *l2){
     Lista elem;

     while (!estaVacia(*l2)){ //No podemos hacer *l2 == NULL porque estaríamos accediendo a los detalles de impl.
          elem = sacar(l2);
          insertarNodoOrdenado(l1,elem);
     }
}

//Función privada de ayuda
void eliminarRepetidos(Lista *l, Lista *elem){
     int e = (*elem)->dato;
     *elem = sacar(l);
     while (*elem != NULL && (*elem)->dato == e){
          destruir(elem);
          *elem = sacar(l); //El ultimo elemento extraido se queda en elem sin insertar aún
     }
}

Lista ordenarListasV2(Lista *l1, Lista *l2){ 
     //sin repetición de elementos y sin poder implementar funciones adicionales dentro de Lista.h/Lista.c
     //se puede liberar la memoria del nodo que no se tenga que volver a insertar con destruir
     Lista l3;
     Lista elem1, elem2;
 
     l3 = crear();
     elem1 = sacar(l1); 
     elem2 = sacar(l2); 
     //Mientras haya elementos en las dos listas
     while (!estaVacia(elem1) && !estaVacia(elem2)){
        if (elem1->dato < elem2->dato){
          insertarNodoOrdenado(&l3,elem1);
          eliminarRepetidos(l1,&elem1);
        }else if (elem2->dato < elem1->dato){
          insertarNodoOrdenado(&l3,elem2);
          eliminarRepetidos(l2,&elem2);
        }else{
          //son iguales
          insertarNodoOrdenado(&l3,elem1);
          eliminarRepetidos(l1,&elem1);
          eliminarRepetidos(l2,&elem2);
        }
     }
     //Si quedan elementos en *l1
     while (!estaVacia(elem1)){
          insertarNodoOrdenado(&l3,elem1);
          eliminarRepetidos(l1,&elem1);
     }

     //Si quedan elementos en *l2
     while (!estaVacia(elem2)){
          insertarNodoOrdenado(&l3,elem2);
          eliminarRepetidos(l2,&elem2);
     }

     return l3;
}

int main(){
     Lista l1, l2;

     //Creamos las dos listas
     printf("\nCreando lista l1 y lista l2...\n");
     l1 = crear();
     l2 = crear();

     printf("Insertamos de forma ordenada los elementos 3 1 5 7 7 1 3 en l1...\n");
     insertarOrdenado(&l1,3);
     insertarOrdenado(&l1,1);
     insertarOrdenado(&l1,5);
     insertarOrdenado(&l1,7);
     insertarOrdenado(&l1,7);
     insertarOrdenado(&l1,1);
     insertarOrdenado(&l1,3);
     mostrar(l1);

     printf("Insertando de forma ordenada los elementos 2 4 0 21 7 9 en l2...\n");
     insertarOrdenado(&l2,2);
     insertarOrdenado(&l2,4);
     insertarOrdenado(&l2,0);
     insertarOrdenado(&l2,21);
     insertarOrdenado(&l2,7);
     insertarOrdenado(&l2,12);
     mostrar(l2);

     printf("Creamos la lista ordenada en l3 con version 1...\n");
     ordenarListas(&l1,&l2);
     mostrar(l1);

     destruir(&l1);
     destruir(&l2);

     printf("\n\nCreando nuevamente lista l1 y lista l2...\n");
     l1 = crear();
     l2 = crear();

     insertarOrdenado(&l1,3);
     insertarOrdenado(&l1,1);
     insertarOrdenado(&l1,5);
     insertarOrdenado(&l1,7);
     insertarOrdenado(&l1,7);
     insertarOrdenado(&l1,1);
     insertarOrdenado(&l1,3);
     mostrar(l1);

     insertarOrdenado(&l2,2);
     insertarOrdenado(&l2,4);
     insertarOrdenado(&l2,0);
     insertarOrdenado(&l2,21);
     insertarOrdenado(&l2,7);
     insertarOrdenado(&l2,12);
     mostrar(l2);

     printf("Creamos la lista ordenada en l3 con version 2...\n");
     Lista l3 = ordenarListasV2(&l1,&l2);
     mostrar(l3);

     destruir(&l1);
     destruir(&l2);
 
     return 0;
}