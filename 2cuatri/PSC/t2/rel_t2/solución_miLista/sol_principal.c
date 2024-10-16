#include <stdio.h>
#include "sol_miLista.h"

int main(){
     Lista l1, l2;

     //Creamos dos listas, de distinta forma
     printf("\nCreando lista l1 y lista l2...\n");
     crear(&l1);
     l2 = crearV2();

     printf("Insertando los elementos 3 1 3 5 3 7 5 3 al principio de l1...\n");
     insertar(&l1,3);
     insertar(&l1,1);
     insertar(&l1,3);
     insertar(&l1,5);
     insertar(&l1,3);
     insertar(&l1,7);
     insertar(&l1,5);
     insertar(&l1,3);
     printf("Insertando los elementos 4 2 6 al final de l1...\n");
     insertarFinal(&l1,4);
     insertarFinal(&l1,2);
     insertarFinal(&l1,6);
     //printf("Mostrando l1...\n");
     mostrar(l1);
     printf("Eliminando dos elementos del principio de l1...\n");
     eliminar(&l1);
     eliminar(&l1);
     printf("Eliminando una ocurrencia de los elementos 5 y 3 de l1...\n");
     eliminarElem(&l1,5);
     eliminarElem(&l1,3);
     //printf("Mostrando l1...\n");
     mostrar(l1);
     printf("Saca el primer elemento de la lista, devolviendo un puntero al nodo extraido ...\n");
     Lista nodo = sacar(&l1);
     if (!estaVacia(nodo)){
          printf("El elemento extraido de l1 es %d\n",nodo->dato);
          printf("Libero la memoria del nodo extraido...\n");
          free(nodo);
     }
     printf("Saca el elemento de la lista con valor 4, devolviendo un puntero al nodo extraido ...\n");
     nodo = sacarElem(&l1,4);
     if (!estaVacia(nodo)){
          printf("El elemento extraido de l1 es %d\n",nodo->dato);
          printf("Libero la memoria del nodo extraido...\n");
          free(nodo);
     }
     //printf("Mostrando l1...\n");
     mostrar(l1);
     printf("Eliminando todas las ocurrencias del 3 en l1...\n");
     eliminarMultiplesElem(&l1,3);
     //printf("Mostrando l1...\n");
     mostrar(l1);
     printf("\n\n");

     printf("Insertando de forma ordenada los elementos 3 1 5 7 7 1 3 al principio de l2...\n");
     insertarOrdenado(&l2,3);
     insertarOrdenado(&l2,1);
     insertarOrdenado(&l2,5);
     insertarOrdenado(&l2,7);
     insertarOrdenado(&l2,7);
     insertarOrdenado(&l2,1);
     insertarOrdenado(&l2,3);
     //printf("Mostrando l2...\n");
     mostrar(l2);
     printf("Eliminando dos elementos del principio de l2...\n");
     eliminar(&l2);
     eliminar(&l2);
     mostrar(l2);
     printf("Saca el elemento de la lista con valor 5, devolviendo un puntero al nodo extraido ...\n");
     nodo = sacarOrdenado(&l2,5);
     if (!estaVacia(nodo)){
          printf("El elemento extraido de l2 es %d\n",nodo->dato);
          printf("Libero la memoria del nodo extraido...\n");
          free(nodo);
     }
     mostrar(l2);
     printf("Eliminando todas las ocurrencias del elemento 3 y del elemento 7 de l2...\n");
     eliminarOrdenadoMultiplesElem(&l2,3);
     eliminarOrdenadoMultiplesElem(&l2,7);
     mostrar(l2);
     printf("\n\n");

     printf("Liberando la memoria de l2 y creando una lista nueva ...\n");
     destruir(&l2);
     l2 = crearV2(); //no sería necesario porque l2 deja la lista como una lista vacía
     printf("Insertando de forma ordenada y sin repeticiones los elementos 3 1 5 7 7 1 3 al principio de l2...\n");
     insertarOrdenadoSinRepeticiones(&l2,3);
     insertarOrdenadoSinRepeticiones(&l2,1);
     insertarOrdenadoSinRepeticiones(&l2,5);
     insertarOrdenadoSinRepeticiones(&l2,7);
     insertarOrdenadoSinRepeticiones(&l2,7);
     insertarOrdenadoSinRepeticiones(&l2,1);
     insertarOrdenadoSinRepeticiones(&l2,3);
     printf("Mostrando l2...\n");
     mostrar(l2);
     printf("Buscando el 3 y el 8 en la lista l2...\n");
     printf("Esta el 3? = %d\n",buscarElementoV1(l2,3));
     nodo = buscarElementoV2(l2,8);
     printf("Esta el 8? = %d\n",(nodo != NULL));
     if (!estaVacia(nodo)){
          printf("Libero la memoria del nodo devuelto al buscar...\n");
          free(nodo);
     }
     printf("\n\n");

     return 0;
}