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
   while(!estaVacia(*l2)){
      Lista nodo = sacar(l2);
      insertarNodoOrdenado(l1, nodo);
   }
}

/* Ordenar las listas l1 y l2 y generar una lista nueva
   - La lista nueva que se devuelve no puede tener repeticiones
   - Solo se pueden usar las funciones ya existentes en Lista.h
   - No se puede manipular directamente la lista fuera del módulo

*/
Lista ordenarListasV2(Lista *l1, Lista *l2){ 
   
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
/*
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
 */
     return 0;
}