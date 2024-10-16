/*
 * listaDobleCircular.c
 *
 *  Created on: 19 mar. 2021
 *      Author: Monica
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "listaDobleCircular.h"

//Comprobar si esta vacía
int estaVacia(TListaDoble l){
	return (l == NULL);
}

//Crear una lista vacía
void crear(TListaDoble *l){
	*l = NULL;
}

TListaDoble crearNodo(char *persona){
	TListaDoble nuevo = (TListaDoble)malloc(sizeof(struct TNodo));
	strcpy(nuevo->persona,persona);
	nuevo->sig = NULL;
	nuevo->ant = NULL;

	return nuevo;
}

//Crear una lista desde un fichero de texto donde el nombre de cada persona está en una linea distinta del fichero
void crearDesdeFicheroTexto(char *nomFic, TListaDoble *l){
	FILE *fd;
	char persona[20];

	fd = fopen(nomFic,"rt");
	if (fd == NULL){
		perror("Error abriendo el fichero");
	}else{
		int nleidos;
		crear(l);
		while ((nleidos=fscanf(fd,"%s",persona))>0){
			insertar(l,persona);
		}

		fclose(fd);
	}
}

void leerNombre(char *persona,FILE *fd){
	int nleidos,i;
	char c;
	i=0;
	while ((nleidos=fread(&c,sizeof(char),1,fd))>0 && c!='\0'){
		persona[i]=c;
		i++;
	}
	persona[i]='\0';
}

//Crear una lista desde un fichero binario donde el nombre de cada persona está en una linea distinta del fichero
void crearDesdeFicheroBinario(char *nomFic, TListaDoble *l){
	FILE *fd;
	char persona[20];

	fd = fopen(nomFic,"rb");
	if (fd == NULL){
		perror("Error abriendo el fichero");
	}else{
		while(!feof(fd)){
			leerNombre(persona,fd);
			insertar(l,persona);
		}
		fclose(fd);
	}
}

//Insertar al principio de una lista doblemente enlazada y circular
void insertar(TListaDoble *lista,char *persona){
	TListaDoble nuevo = crearNodo(persona);

	if (*lista == NULL){
		//La lista esta vacia
		*lista = nuevo;
		nuevo->sig = nuevo;
		nuevo->ant = nuevo;
	}else{
		nuevo->sig = *lista;
		if (*lista == (*lista)->sig){
			//La lista solo tiene un nodo
			nuevo->ant = *lista;
			(*lista)->sig = nuevo;
		}else{
			//La lista tiene mas de un elemento
			nuevo->ant = (*lista)->ant;
			(*lista)->ant->sig = nuevo;
		}
		(*lista)->ant = nuevo;
		*lista = nuevo;
	}
}

//Mostrar la lista en orden de inserción
void mostrar(TListaDoble l){
	TListaDoble ptr;

	if (l!=NULL){
		ptr = l->ant;
		while (ptr != l){
			printf("%s ",ptr->persona);
			ptr = ptr->ant;
		}
		printf("%s\n",ptr->persona);
	}
}

//Mostrar la lista en orden inverso al de inserción
void mostrarInverso(TListaDoble l){
	TListaDoble ptr;

	ptr = l;
	if (ptr != NULL){ //porque ahora no hay un puntero sig al final que apunte a NULL
		while (ptr->sig != l){
			printf("%s ",ptr->persona);
			ptr = ptr->sig;
		}
		printf("%s\n",ptr->persona);
	}
}

//Mostrar la lista en un fichero de texto
void mostrarEnFicheroTexto(char *nomFic, TListaDoble l){
	TListaDoble ptr;
	FILE *fd;

	fd = fopen(nomFic,"wt");
	if (fd == NULL){
		perror("Error creando el fichero");
	}else{
		if (l!=NULL){
			ptr = l->ant;
			while (ptr != l){
				fprintf(fd,"%s\n",ptr->persona);
				ptr = ptr->ant;
			}
			fprintf(fd,"%s\n",ptr->persona);
		}
		fclose(fd);
	}
}

//Mostrar la lista en un fichero binario
void mostrarEnFicheroBinario(char *nomFic, TListaDoble l){
	TListaDoble ptr;
	FILE *fd;

	fd = fopen(nomFic,"wb");
	if (fd == NULL){
			perror("Error creando el fichero");
	}else{
		if (l!=NULL){
			ptr = l->ant;
			while (ptr != l){
				fwrite(ptr->persona,sizeof(char),strlen(ptr->persona)+1,fd);
				ptr = ptr->ant;
			}
			fwrite(ptr->persona,sizeof(char),strlen(ptr->persona)+1,fd);
		}
		fclose(fd);
	}
}

//Borrar del principio de una lista
void borrarPrincipio(TListaDoble *l){
	TListaDoble borrar;

	if (*l != NULL){
		borrar = *l;
		if ((*l)->sig == (*l)){
			//Solo hay un nodo y hay que inicializar *l a NULL
			*l=NULL;
		}else{
			*l = (*l)->sig;
			(*l)->ant = borrar->ant;
			(*l)->ant->sig = *l;
		}
		free(borrar);
	}

}

//Borrar el nodo apuntado por ptr
void borrarNodo(TListaDoble *l, char *persona){
	TListaDoble ptr;

	//Buscar el nodo a borrar
	ptr = *l;
	while (ptr!=NULL && strcmp(ptr->persona,persona)!=0){
		if (ptr->sig == ptr){
			ptr = NULL;
		}else{
			ptr = ptr->sig;
		}
	}

	if (*l !=NULL && ptr != NULL){
		if(ptr == *l){
			//Hay que borrar el primer nodo
			borrarPrincipio(l);
		}else if (ptr == (*l)->ant){
			//Hay que borrar el ultimo nodo
			ptr->ant->sig = ptr->sig;
			(*l)->ant = ptr->ant;
			free(ptr);
		}else{
			//Hay que borrar un nodo intermedio
			ptr->ant->sig = ptr->sig;
			ptr->sig->ant = ptr->ant;
			free(ptr);
		}
	}
}

/*Devuelve la persona almacenada en el nodo que está a distancia dist
 *  del nodo nodoIni (teniendo en cuenta que la lista es circular)
 *  Si sentido es 1 nos desplazamos en el sentido de las agujas del reloj
 *  Si sentido es 2 nos desplazamos en el sentido contrario al de las agujas del reloj
 */
void datosDistanciaD(TListaDoble l, int dist, int sentido, int nodoIni, char *persona){
	TListaDoble ini;

	if (l!=NULL && (sentido == 1 || sentido == 2)){
		//Colocamos el nodo inicial segun lo indicado en nodoIni
		ini = l;
		while (nodoIni > 0){
			ini = ini->sig;
			nodoIni--;
		}
		//printf("Busqueda desde: %s\n",ini->persona);
		if (sentido == 1){
			while (dist > 0){
				ini = ini->sig;
				dist--;
			}
		}else{
			while (dist > 0){
				ini = ini->ant;
				dist--;
			}
		}
		strcpy(persona,ini->persona);
	}
}

//Devuelve el numero de elementos en la lista
int longitud(TListaDoble l){
	TListaDoble ptr;
	int cont = 0;

	if (l != NULL){
		ptr = l;
		while (ptr->sig!=l){
			cont++;
			ptr = ptr->sig;
		}
		cont++;
	}
	return cont;
}

//Destruye la lista
void destruir(TListaDoble *l){
	while (*l!=NULL){
		borrarPrincipio(l);
	}
}

