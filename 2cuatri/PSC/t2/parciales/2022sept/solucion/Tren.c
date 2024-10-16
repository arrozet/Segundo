/*
 * Examen Septiembre 2022 PSC - todos los grupos.
 * Implementación Tren.c
*/

#include "Tren.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

void inicializarTren(Vagon * tren, int maximoVagones){
    for(int i=0; i<maximoVagones; i++){
        tren[i]=NULL;
    }
}


int entraPasajero(Vagon * tren, unsigned numeroVagon, unsigned numeroAsiento, char * nombre){
    if(tren[numeroVagon]==NULL){
        tren[numeroVagon]= (Vagon) malloc(sizeof(struct Asiento));
        if(tren[numeroVagon]==NULL){
            printf("entraPasajero: no se pudo asignar la memoria al vagon del tren");
            return -1;
        }
        //tren[numeroVagon]->nombre = nombre;
        tren[numeroVagon]->num = numeroAsiento;
        tren[numeroVagon]->sig = NULL;
        strcpy(tren[numeroVagon]->nombre, nombre);

        return 0;
    }
    else{

        Vagon current = tren[numeroVagon]; // busco entre los asientos para ver si esta o no ocupado
                                           // además aprovecho para en previous guardar el lugar después del que iría
                                           // el nuevo asiento en caso de que no esté ocupado 
        Vagon insertarDespuesDeMi = NULL;
        while(current!=NULL && current->num <= numeroAsiento ){
            if(current->num == numeroAsiento){
                printf("El asiento %d está ocupado\n", numeroAsiento);
                return -1;
            }
            insertarDespuesDeMi = current;
            current=current->sig;
        }

        Vagon new = (Vagon) malloc(sizeof(struct Asiento));
        if(new==NULL){
            printf("entraPasajero: no se pudo asignar la memoria al new");
            return -1;
        }   
        new->num=numeroAsiento;
        strcpy(new->nombre, nombre);
        new->sig=current;
        if(insertarDespuesDeMi==NULL){  // es el primer elemento
            tren[numeroVagon] = new;
        }
        else{
            insertarDespuesDeMi->sig = new;
        }
        

        return 0;
    }
}

void imprimeTren(Vagon * tren, unsigned maximoVagones){
    for(unsigned int i=0; i<maximoVagones; i++){
        if(tren[i]!=NULL){
            printf("Vagon %d:\n", i);
            Vagon current = tren[i];
            while(current!=NULL){
                printf("Asiento %u, %s\n", current->num, current->nombre);
                current=current->sig;
            }
        }
    }
    printf("---\n");
}

int salePasajero(Vagon * tren, unsigned numeroVagon, unsigned numeroAsiento){
    if(tren[numeroVagon]==NULL){
        printf("salePasajero: no hay nadie en el vagón %u\n", numeroVagon);
        return -1;
    }
    else{
        Vagon current = tren[numeroVagon]; // busco entre los asientos para ver si esta o no ocupado
                                           // además aprovecho para en previous guardar el lugar después del que iría
                                           // el nuevo asiento en caso de que no esté ocupado 
        Vagon previous = NULL;
        while(current!=NULL && current->num<numeroAsiento){ // lo del < es una optimización para que no busque si no es necesario, ya que está ordenado
            previous = current;
            current=current->sig;
        }
        // si no lo he encontrado, es que ese asiento no está
        if(current==NULL || current->num>numeroAsiento){
            printf("salePasajero: no hay nadie en el vagón %u, asiento %u\n", numeroVagon, numeroAsiento);
            return -1;
        } 

        if(previous!=NULL){  // si no está al principio
            previous->sig = current->sig;
        }
        else{   // si está al principio
            tren[numeroVagon] = current->sig;
        }
        free(current);
        return 0;
    }
    
    return 0;
}

Vagon buscarAsiento(Vagon * tren, unsigned numeroVagon1, unsigned numeroAsiento1){
    // PASAJERO 1 (comprobar que su asiento SÍ está ocupado)
        Vagon current1 = tren[numeroVagon1]; // busco entre los asientos para ver si esta o no ocupado
                                           // además aprovecho para en previous guardar el lugar después del que iría
                                           // el nuevo asiento en caso de que no esté ocupado 
        while(current1!=NULL && current1->num<numeroAsiento1){  // < es una optimización (en lugar de !=)
            current1=current1->sig;
        }
        // si no lo he encontrado, es que ese asiento no está
        if(current1==NULL || current1->num>numeroAsiento1){
            printf("intercambianAsientos - pasajero 1: no hay nadie en el vagón %u, asiento %u\n", numeroVagon1, numeroAsiento1);
            return NULL;
        } 

        return current1;
}

int intercambianAsientos(Vagon * tren, unsigned numeroVagon1, unsigned numeroAsiento1,unsigned numeroVagon2, unsigned numeroAsiento2){
/*
    // PASAJERO 1 (comprobar que su asiento SÍ está ocupado)
        Vagon current1 = tren[numeroVagon1]; // busco entre los asientos para ver si esta o no ocupado
                                           // además aprovecho para en previous guardar el lugar después del que iría
                                           // el nuevo asiento en caso de que no esté ocupado 
        while(current1!=NULL && current1->num<numeroAsiento1){  // < es una optimización (en lugar de !=)
            current1=current1->sig;
        }
        // si no lo he encontrado, es que ese asiento no está
        if(current1==NULL || current1->num>numeroAsiento1){
            printf("intercambianAsientos - pasajero 1: no hay nadie en el vagón %u, asiento %u\n", numeroVagon1, numeroAsiento1);
            return -1;
        } 
    // PASAJERO 2 (comprobar que su asiento SÍ está ocupado)    
        Vagon current2 = tren[numeroVagon2]; // busco entre los asientos para ver si esta o no ocupado
                                           // además aprovecho para en previous guardar el lugar después del que iría
                                           // el nuevo asiento en caso de que no esté ocupado 
        while(current2!=NULL && current2->num<numeroAsiento2){
            current2=current2->sig;
        }
        // si no lo he encontrado, es que ese asiento no está
        if(current2==NULL || current2->num>numeroAsiento2){
            printf("intercambianAsientos - pasajero 2: no hay nadie en el vagón %u, asiento %u\n", numeroVagon2, numeroAsiento2);
            return -1;
        } 
*/
        Vagon current1 = buscarAsiento(tren, numeroVagon1, numeroAsiento1);
        Vagon current2 = buscarAsiento(tren, numeroVagon2, numeroAsiento2);

        if(current1 == NULL || current2 == NULL){
            return -1;
        }
        // Si llego aquí, ambos asientos están ocupados. Los intercambio
        /*
        int max = 0;
        if(strlen(current1->nombre) > strlen(current2->nombre)){
            max = (int) strlen(current1->nombre);
        }
        else{
            max = (int) strlen(current2->nombre);
        }
        char aux[max];
        */
       // por qué si no hago malloc o hago char aux[max] (estatico), no printea NADA?
       /*
       size_t max = 0;
       if(strlen(current1->nombre) > strlen(current2->nombre)){
            max = strlen(current1->nombre);
        }
        else{
            max = strlen(current2->nombre);
        }
        */
       char* aux = (char*) malloc(sizeof(char)*strlen(current1->nombre)+1); // el +1 es para indicar que reserve memoria para el /0 tb
        strcpy(aux, current1->nombre);
        strcpy(current1->nombre, current2->nombre);
        strcpy(current2->nombre, aux);
        return 0;
}

void ultimaParada(Vagon * tren, unsigned maximoVagones){
    
    for(unsigned int i=0; i<maximoVagones; i++){
        while(tren[i]!=NULL){
            Vagon aux = tren[i];
            free(aux);
            tren[i] = tren[i]->sig;
        }
    }
}

/// @brief Guarda en fichero de TEXTO los datos de los pasajeros en el tren. El formato del fichero de texto será el siguiente, 
// una primera línea con el siguiente texto: 
// Vagon;Asiento;Nombre 
// Tras esta línea, incluirá una línea por cada pasajero, ordenados por vagón primero y luego por número de asiento.  
// 0;2;Carmen Garcia 
// 0;3;Pepe Perez 
// 1;5;Adela Gamez 
// 1;7;Josefa Valverde 
/// @param filename Nombre del fichero en el que se van a almacenar los datos de los pasajeros del tren. 
/// @param tren Array con los vagones y pasajeros que tiene el tren actualmente. 
/// @param maximoVagones Máximo de vagones que tiene el tren. 
/// 1.5 pts 
void almacenarRegistroPasajeros(char *filename, Vagon * tren, unsigned maximoVagones){
    FILE* fsal = fopen(filename, "wt");
    if(fsal==NULL){
        perror("No se pudo abrir el fichero\n");
        exit(-1);
    }
    fprintf(fsal, "Vagon;Asiento;Nombre\n");
    
    for(unsigned i=0; i<maximoVagones; i++){
        if(tren[i]!=NULL){
            Vagon este = tren[i];
            while(este!=NULL){
                //printf("echo2\n");
                fprintf(fsal, "%u;%u;%s\n", i, este->num, este->nombre);
                este = este->sig;
            }
        }
    }
    
    fclose(fsal);
}
 
/// @brief Algunas estaciones están automatizadas y proporcionan un fichero con los pasajeros que van a entrar en un vagón en su parada. 
// Esta función carga los pasajeros que están en el fichero BINARIO filename en el  
// vagón numeroVagon. Se asume que los pasajeros almacenados en el fichero no van a  
// sentarse en asientos previamente ocupados.  
// El fichero binario almacena la información de cada pasajero con la siguiente  
// estructura:  
// - Un entero con el número de asiento. 
// - La cadena de caracteres con el nombre. 
/// @param filename Nombre del fichero que contiene los datos de los pasajeros del vagon. 
/// @param tren Array con los vagones y pasajeros que tiene el tren actualmente. 
/// @param numeroVagon Vagon del que se van a importar los pasajeros.  
/// 1.5 pts 
void importarPasajerosVagon(char *filename, Vagon * tren,unsigned numeroVagon){
    FILE* fent = fopen(filename, "rb");
    if(fent==NULL){
        perror("No se pudo abrir el fichero\n");
        exit(-1);
    }
    unsigned numAsiento;
    char nombre[31];
    while(fread(&numAsiento, sizeof(unsigned), 1, fent) == 1){
        fread(nombre, 1, 30, fent);
        entraPasajero(tren,numeroVagon,numAsiento,nombre);
    }

    fclose(fent);
}