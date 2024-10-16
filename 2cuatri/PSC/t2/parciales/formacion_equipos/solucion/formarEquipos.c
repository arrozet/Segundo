/*
 * formarEquipos.c
 *
 *  Created on: 21 mar. 2021
 *      Author: Monica
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "listaDobleCircular.h"
#include "formarEquipos.h"

/** ¡¡IMPORTANTE!!
 *
 *   En este modulo se utiliza el modulo "listaDobleCircular".
 *
 *   Hay que considerarlo como si no supieramos como está la lista implementada.
 *   Solo conocemos la interfaz
 *
 *   Por ello:
 *   	 - Solo es posible utilizar las funciones en el fichero listaDobleCircular.h
 *       - No esta permitido manejar la lista directamente a a través de sus punteros.
 */

//Funcion privada
int calcularDistancia(){
	//Se obtiene un numero aleatorio entre 0 y 6
	return (rand() % 7);
}

/*Crea una lista enlazada doblemente circular con las personas
	incluidas en el fichero de texto pasado como parametro.
	Mirar fichero personas.txt proporcionado
*/
void crearCirculoPersonas(char *nombre,TListaDoble *personas){
	//Se reutiliza la funcion ya definida en el modulo de la lista enlazada
	crearDesdeFicheroTexto(nombre,personas);
}

/*Forma dos equipos a partir de la lista de personas en el primer parámetro
 * Como salida se devuelven los dos equipos como listas enlazadas doblemente
 * enlazada y circular.
 *
 * equipo1 y equipo2 podrían ser listas simples, pero para evitar no tener
 * que implementar los dos módulos, aquí todas las listas son del mismo tipo
 */
void formarEquipos(TListaDoble *personas, TListaDoble *equipo1, TListaDoble *equipo2){
	//En la implementacion de esta funcion SOLO se hacen llamadas a las funciones
	//de manejo de la lista enlazada definidas en el fichero listaDobleCircular.h

	//Generacion de numeros aleatorios: Para crear una semilla distinta en cada ejecución
	//Solo se debe llamar una vez. Por eso no se invoca dentro de la funcion calcularDistancia()
	srand (time(NULL));
	int d,sentido,l;
	char persona[20];
	int ini = 0;

	//Creamos los dos equipos
	crear(equipo1);
	crear(equipo2);

	/* Mientras la lista de personas no este vacia
	 *    - Aqui no seria posible hacer while (personas != NULL) porque eso seria
	 *      acceder a los detalles de implementacion del modulo listaDobleCircular
	 *    - Se aplicaria lo mismo al resto de la implementacion de esta funcion
	 */
	while(!estaVacia(*personas)){
		sentido=1;
		//Calculamos la distancia de forma aleatoria, entre el numero de personas que quedan en la lista
		d = calcularDistancia();
		//Obtenemos la persona que se encuentra a distancia d desde el inicio de la lista
		datosDistanciaD(*personas,d,sentido,ini,persona);
		//printf("Persona a distancia %d desde %d, sentido %d: %s\n\n",d,ini,sentido,persona);
		//Insertamos esa persona en el equipo1
		insertar(equipo1,persona);
		//Eliminamos la persona de la lista
		borrarNodo(personas,persona);
		//mostrarInverso(*personas);
		//fflush(stdout);
		l = longitud(*personas);
		if (l != 0){ //Para evitar division por 0
			ini = (ini+d)%l; //Calculamos el nuevo inicio desplazandonos a la derecha (entre 0 y el numero de elementos en la lista)
		}
		if (!estaVacia(*personas)){
			//Hacemos lo mismo pero en el sentido contrario al de las agujas del reloj
			d = calcularDistancia();
			sentido=2;
			datosDistanciaD(*personas,d,sentido,ini,persona);
			//printf("Persona a distancia %d desde %d, sentido %d: %s\n\n",d,ini,sentido,persona);
			insertar(equipo2,persona);
			borrarNodo(personas,persona);
			//mostrarInverso(*personas);
			//fflush(stdout);
			sentido=1;
			l = longitud(*personas);
			//Calculamos el nuevo inicio desplazandonos a la izquierda
			//Al estar restando nos tenemos que asegurar que el valor no es negativo
			//Hay que tener en cuenta que la longitud anterior era (l+1), para que no salgan valores negativos
			if (l !=0){
				ini = (ini-d+l)%l;
			}
		}
	}
}
