/*
 * cola.h
 *
 *  Created on: 22/2/2020
 *      Author: usuario
 */

#ifndef COLAV1_COLA_H_
#define COLAV1_COLA_H_

#include <stdio.h>
#include <stdlib.h>

typedef struct DatosProceso *Proceso;

struct DatosProceso{
	char historial[6];
	Proceso sig;
};

struct ColaProcesos{
	Proceso frente;
	Proceso final;
};
typedef struct ColaProcesos ColaProcesos;

void crearCola(ColaProcesos *cola);
int colaVacia(ColaProcesos cola);
void vaciarCola(ColaProcesos *cola);
void insertarProceso(ColaProcesos *cola, char *historial);
char *extraerProceso(ColaProcesos *cola, char *historial);
void mostrarCola(ColaProcesos cola);
void moverProcesos(ColaProcesos *dest, ColaProcesos *orig, int num);
int procesosEsperando(ColaProcesos cola);

#endif /* COLAV1_COLA_H_ */
