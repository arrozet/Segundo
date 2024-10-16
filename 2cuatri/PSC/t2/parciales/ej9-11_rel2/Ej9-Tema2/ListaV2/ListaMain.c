/*
 * ListaMain.c
 *
 *  Created on: 28/1/2020
 *      Author: usuario
 */
#include "Lista.h"

int main(){
	TLista l1;

	crear(&l1);
	mostrar(l1);

	int i;
	for (i=0; i<10; i++){
		insertarPrincipio(&l1,i);
	}
	mostrar(l1);

	borrarPrimero(&l1);
	mostrar(l1);

	borrar(&l1,4);
	borrar(&l1,7);
	borrar(&l1,0);
	borrar(&l1,8);
	mostrar(l1);
    destruir(&l1);

	crear(&l1);
	mostrar(l1);
    insertarFinal(&l1,1);
	insertarFinal(&l1,2);
	insertarFinal(&l1,3);
	mostrar(l1);
    destruir(&l1);

	crear(&l1);
	insertarOrdenado(&l1,5);
	insertarOrdenado(&l1,2);
	insertarOrdenado(&l1,4);
	insertarOrdenado(&l1,7);
	mostrar(l1);

	return 0;
}

