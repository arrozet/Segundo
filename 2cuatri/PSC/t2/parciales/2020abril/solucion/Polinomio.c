#include "Polinomio.h"
#include <stdio.h>
#include <stdlib.h>

/*Crea el polinomio 0 (es decir, un polinomio vacío).*/
void polinomioCero(TPolinomio *p){
	(*p) = NULL;
}

/*Devuelve el grado del polinomio, es decir, el mayor exponente de los
monomios que no son nulos. En el ejemplo, el grado es 7.
El grado del polinomio cero es 0.*/
unsigned int grado(TPolinomio p){
	unsigned int grado = 0;

	if (p != NULL){
		grado = p->exp;
	}
	return grado;
}

/* Devuelve el coeficiente de exponente exp del polinomio p.*/
unsigned int coeficiente(TPolinomio p, unsigned int exp){
	unsigned int coef = 0;
	TPolinomio ptr = p;

	while (ptr != NULL && ptr->exp > exp){
		ptr = ptr -> sig;
	}

	if (ptr != NULL && ptr->exp == exp){
		coef = ptr->coef;
	}

	return coef;
}

void crearMonomio(TPolinomio *monomio, unsigned int coef, unsigned int exp){
	*monomio = (TPolinomio) malloc(sizeof(struct TMonomio));
	(*monomio)->coef = coef;
	(*monomio)->exp = exp;
	(*monomio)->sig = NULL;
}

/* Insertar el monomio con coeficiente coef, y exponente exp en el polinomio,
 * de manera que el polinomio quede ordenado. Asegurarse que no se insertan
 * monomios cuyo coeficiente sea 0 y tampoco dos monomios con el mismo exponente.
 * Si al insertar un monomio ya hay otro con el mismo exponente los coeficientes
 * se sumarán. Se puede asumir que el valor del coeficiente siempre será un numero
 * natural (es decir será >=0).*/
void insertar(TPolinomio *p, unsigned int coef, unsigned int exp){
	TPolinomio ant,ptr;

	if (coef != 0){//No se insertan monomios con coeficiente 0
		//Buscamos el sitio donde insertar
		ant = NULL;
	    ptr = (*p);
		while (ptr != NULL && ptr->exp > exp){
			ant = ptr;
			ptr = ptr -> sig;
		}

		if (ptr != NULL && ptr->exp == exp){
			//Si el exp ya existe se suman los coeficientes
			ptr->coef = ptr->coef + coef;
		}else{
			//Si el exp no existe creamos un monomio nuevo
			TPolinomio monomio;
			crearMonomio(&monomio,coef,exp);
			if (ant == NULL){
				monomio->sig = (*p);
				(*p) = monomio;
			}else {
				monomio->sig = ptr;
				ant->sig = monomio;
			}
		}
	}
}

/*Escribe por la pantalla el polinomio con un formato similar al siguiente:
 * [(3,7)(0,6)(2,5)(0,4)(3,3)(0,2)(5,1)(9,0)] para el polinomio ejemplo.
 * Ten en cuenta que los monomios de exponente menor al grado del polinomio
 * con coeficiente 0 también aparecen en la salida aunque no estén almacenados
 * en el polinomio. */
void imprimir(TPolinomio p){
	int exp;
	unsigned int coef;

	if (p != NULL){
		exp = grado(p);
		printf("[");
		while (exp>=0){
			coef = coeficiente(p,exp); //usar esta llamada es mas ineficiente que la otra version de imprimir
			printf("(%d,%d)",coef,exp);
			exp--;
		}
		printf("]\n");
	}else{
		printf("Polinomio cero");
	}
}

/*void imprimir(TPolinomio p){
	TPolinomio ptr = p;
	int sigExp;

	if (ptr != NULL){
		sigExp = grado(ptr);
		printf("[");
		while (ptr != NULL){
			//Distinguimos entre un nodo cuyo coeficiente sea 0 y otro que no
			if (ptr->exp == sigExp){
				printf("(%d,%d)",ptr->coef,sigExp);
				ptr = ptr->sig;
			}else{
				printf("(%d,%d)",0,sigExp);
			}
			sigExp--;
		}
		while (sigExp >= 0){
			printf("(%d,%d)",0,sigExp);
			sigExp --;
		}
		printf("]\n");
	}else{
		printf("Polinomio cero\n");
	}
}*/

/* Elimina todos los monomios del polinomio haciendo que el polinomio resultante
 * sea el polinomio 0.*/
void destruir(TPolinomio *p){
	TPolinomio ptr = (*p);
	while (ptr != NULL){
		(*p) = (*p)->sig;
		free(ptr);
		ptr= (*p);
	}
	//NO ES NECESARIO PONER (*p) = NULL, porque ya es NULL al salir del bucle while
}

//Parte 2 - Notable
/* Lee los datos de un polinomio de un fichero de texto, y
 * crea la lista de monomios p. El formato del polinomio en el fichero contiene
 * una secuencia de pares de dígitos correspondientes al coeficiente y exponente
 * de cada monomio del polinomio, incluyendo los que tienen coeficiente nulo.
 * En ambos casos, suponemos que los coeficientes y exponentes son dígitos del 0 al 9
 * (no hay números superiores).
 * Por ejemplo, para el polinomio de ejemplo el fichero de texto estaría compuesto
 * por la secuencia de caracteres “0690332551370402”.
 * Observa que los monomios pueden venir desordenados en el fichero de entrada.
 *
 * La conversión de un valor de tipo ‘char’ que contenga
 * un valor númerico (ej. char c = ‘2’)
 * a su correspondiente valor entero (int valor),
 * se puede hacer de la siguiente forma: valor = c – ‘0’
*/
void crearDeFichero(TPolinomio *p, char *nombre){
	FILE *fd;
	char coef, exp;
	unsigned int c, e;
	int nleidos;

	fd = fopen(nombre,"rt");
	if (fd == NULL){
		perror("Error al abrir el fichero");
	}else{
		polinomioCero(p);
		//Es importante saber el formato del fichero.
		//Con el formato que nos dan no nos queda mas remedio que leer los valores del
		//fichero como caracteres
		while ((nleidos = fscanf(fd,"%c\n%c\n",&coef,&exp))> 0){
			c = coef-'0';
			e = exp-'0';
			insertar(p,c,e);
		}
		fclose(fd);
	}
}

//Parte 3
//Parte 3. Sobresaliente
/* Evalúa el polinomio para el valor x y devuelve el resultado.
 * Para la evaluación del polinomio debes utilizar el método de Horner,
 * de manera que ax^4 + bx^3+ cx^2+dx+e puede evaluarse
 * en un valor cualquiera x teniendo en cuenta que es equivalente
 * a (((ax+b)x+c)x+d)x+e.
*/
int evaluar(TPolinomio p,int x){
	int eval, grad;
	unsigned int coef;
	TPolinomio ptr = p;

	eval = 0;
	if (p!=NULL){
		grad = grado(p);
		while (grad >0){
			coef = coeficiente(p,grad);
			eval = (eval+coef)*x;
			if (coef != 0){
				ptr=ptr->sig;
			}
			grad--;
		}
		//calculo el último término que es distinto
		eval = eval + coeficiente(p,0);
	}
	return eval;
}
