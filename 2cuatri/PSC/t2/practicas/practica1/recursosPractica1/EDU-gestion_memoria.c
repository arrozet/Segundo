//Estudiante: Eduardo González Bautista


#include "gestion_memoria.h"
#include <stdio.h>
#include <stdlib.h>

#define MAX 1000
/* Crea la estructura utilizada para gestionar la memoria disponible.
 * Inicialmente, s�lo un nodo desde 0 a MAX
*/


	void crear(T_Manejador* manejador){
        (*manejador) = (T_Manejador) malloc(sizeof(struct T_Nodo));
        (*manejador)->inicio = 0;
        (*manejador)->fin = MAX-1;
        (*manejador)->sig = NULL;
    }

    /* Destruye la estructura utilizada (libera todos los nodos de la lista.
 * El par�metro manejador debe terminar apuntando a NULL
*/
	void destruir(T_Manejador* manejador){
        T_Manejador ant, act;
        act = (*manejador);
        ant = NULL;
        while (act != NULL){
            ant = act;
            act = act->sig;
            free(ant);
        }
        (*manejador) = NULL;
    }

    /* Devuelve en �dir� la direcci�n de memoria �simulada� (unsigned) donde comienza
 * el trozo de memoria continua de tama�o �tam� solicitada.
 * Si la operaci�n se pudo llevar a cabo, es decir, existe un trozo con capacidad
 * suficiente, devolvera TRUE (1) en �ok�; FALSE (0) en otro caso.
 */
	void obtener(T_Manejador *manejador, unsigned tam, unsigned* dir, unsigned* ok){
        T_Manejador ant, act;
        act = (*manejador);
        ant = NULL;
        (*ok) = 0;
        while (act != NULL && ((act->fin) - (act->inicio) + 1 < tam)){
            ant = act;
            act = act->sig;
        }
        if (act != NULL){
            unsigned tamn = act->fin - act->inicio + 1;
            (*ok) = 1;
            (*dir) = act->inicio;
            if (tamn == tam){
                if(ant == NULL){
                    (*manejador) = act->sig;
                    free(act);
                } else {
                    ant->sig = act->sig;
                    free(act);
                }
            } else {
                act->inicio += tam;
            }
        }

    }
    /* Muestra el estado actual de la memoria, bloques de memoria libre
*/
    void mostrar (T_Manejador manejador){
        printf("-----------------------\n");
        while (manejador != NULL){
            printf("Desde %d hasta %d: Libre.\n",manejador->inicio, manejador->fin);
            manejador = manejador->sig;
        }
        //printf("-----------------------\n");
    }

    /* Devuelve el trozo de memoria continua de tama�o �tam� y que
 * comienza en �dir�.
 * Se puede suponer que se trata de un trozo obtenido previamente.
 */
	void devolver(T_Manejador *manejador,unsigned tam,unsigned dir){
     T_Manejador previous, current;
     current = (*manejador);
     previous = NULL;
     while (current != NULL && current->inicio < dir){ //cuando salga de aqui, prevous y current seran los trozos de memoria libres que rodean al trozo pasado por parametro
        previous = current;
        current = current->sig;
     }
     if (previous != NULL && previous->fin == dir - 1){
        previous->fin = dir + tam - 1;//pegado a la izquierda
        if (previous->fin == current->inicio - 1){//pegado a izquierda y derecha
            previous->fin = current->fin;
            previous->sig = current->sig;
            free(current);
        }
     } else { //no pega a la izquierda
        if (current != NULL && current->inicio == dir + tam){//pega a la derecha
            current->inicio = dir;
        } else {//no pega
            T_Manejador nuevo = (T_Manejador) malloc(sizeof(struct T_Nodo));
            nuevo->inicio = dir;
            nuevo->fin = dir + tam - 1;
            nuevo->sig = current;
            if (previous == NULL){
                (*manejador) = nuevo;
            } else {
                previous->sig = nuevo;
            }
        }
     }
    }




