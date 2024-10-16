/*
 ============================================================================
 Name        : ParcialC-2020.c
 Author      :
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include "Polinomio.h"

//Parte 3 - Sobresaliente
/* Dados dos polinomios p1 y p2, devuelve 1 si todos los monomios
 * del polinomio p1 son también monomios del polinomio p2, y 0 en
 * caso contrario. Implementar este algoritmo utilizando solo
 * funciones de “Polinomio.h”
*/
int estaIncluido(TPolinomio p1,TPolinomio p2){
	int esta = 1;
	unsigned int exp;

	exp = grado(p1);
	if (exp <= grado(p2)){
		while (exp >=0 && esta){
			if (coeficiente(p1,exp) != coeficiente(p2,exp)){
				esta = 0;
			}
			exp--;
		}
	}else{
		esta = 0;
	}
	return esta;
}

int main(void) {
	//setvbuf(stdout,NULL,_IONBF,0);
	//PARTE 1
	TPolinomio p;
	unsigned int g,c;

	//Creamos polinomio cero
	polinomioCero(&p);

	/* Insertamos monomios para crear el siguiente polinomio:
	 *  [(3,7)(0,6)(2,5)(0,4)(3,3)(0,2)(5,1)(9,0)]
	 */
	insertar(&p,2,5);
	insertar(&p,9,0);
	insertar(&p,3,7);
	insertar(&p,3,3);
	insertar(&p,0,2);
	insertar(&p,4,1);
	insertar(&p,1,1);
	insertar(&p,0,4);

	//Mostramos el grado del polinomio
	g = grado(p);
	printf("El grado del polinomio es: %d\n", g);
	printf("COMPRUEBA QUE SEA 7\n");

	//Imprimirmos el polinomio
	printf("\nEl polinomio creado es: ");
	imprimir(p);
	printf("COMPRUEBA QUE SEA: [(3,7)(0,6)(2,5)(0,4)(3,3)(0,2)(5,1)(9,0)]\n");

	//Imprimimos el coeficiente de varios monomios
	c = coeficiente(p,5);
	printf("\nEl coeficiente para el exp 5 es: %d\n", c);
	printf("COMPRUEBA QUE SEA 2\n");

	c = coeficiente(p,6);
	printf("El coeficiente para el exp 6 es: %d\n", c);
	printf("COMPRUEBA QUE SEA 0\n");

	c = coeficiente(p,0);
	printf("El coeficiente para el exp 0 es: %d\n", c);
	printf("COMPRUEBA QUE SEA 9\n");

	//PARTE 2
	TPolinomio p2;

	polinomioCero(&p2);
	crearDeFichero(&p2,"monomios.txt");
	printf("\nPolinomio 2 creado desde fichero: ");
	imprimir(p2);

	//PARTE 3

	TPolinomio p3;

	polinomioCero(&p3);
	insertar(&p3,2,5);
	insertar(&p3,3,3);
	insertar(&p3,5,1);
	printf("\nPolinomio 1: ");
	imprimir(p);
	printf("Polinomio 2: ");
	imprimir(p3);
	int esta = estaIncluido(p3,p);
	if (esta){
		printf("Los monomios del Polinomio 2 estan incluidos en el Polinomio 1\n");
	}else{
		printf("Los monomios del Polinomio 2 no estan incluidos en el Polinomio 1\n");
	}
	printf("COMPRUEBA QUE INDIQUE QUE NO ESTAN INCLUIDOS\n");

	printf("\n");
	imprimir(p2);
	int valor = evaluar(p2,3);
	printf("El valor del polinomio para x = 3 es: %d\n", valor);
	printf("COMPRUEBA QUE SEA 7152\n");


	destruir(&p);
	return 0;
}
