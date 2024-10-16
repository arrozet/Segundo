/*
 * cola.h
 *
 *  Created on: 22/2/2020
 *      Author: usuario
 */

#ifndef COLAV2_COLA_H_
#define COLAV2_COLA_H_

#include <stdio.h>
#include <stdlib.h>

typedef struct Proceso *ColaProcesos;
struct Proceso{
	char historial[11];
	ColaProcesos sig;
};

void crearCola(ColaProcesos *cola);
int colaVacia(ColaProcesos cola);
void vaciarCola(ColaProcesos *cola);
void insertarProceso(ColaProcesos *cola, char *historial);
void extraerProceso(ColaProcesos *cola, char *historial);
void mostrarCola(ColaProcesos cola);
void moverProcesos(ColaProcesos *dest, ColaProcesos *orig, int num);
int procesosEsperando(ColaProcesos cola);

#endif /* COLAV2_COLA_H_ */
