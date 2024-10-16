/*
 * Polinomio.h
 *
 */
#include "Polinomio.h"
#include <stdio.h>
#include <stdlib.h>

//Parte 1. PARA APROBAR

/*Crea el polinomio 0 (es decir, un polinomio vacío).*/
void polinomioCero(TPolinomio *p){
	*p = NULL;
}

/*Devuelve el grado del polinomio, es decir, el mayor exponente de los
monomios que no son nulos. En el ejemplo, el grado es 7.
El grado del polinomio cero es 0.*/
unsigned int grado(TPolinomio p){
	if(p==NULL){
		return 0;
	}
	else{
		return (p)->exp;
	}
}

/* Devuelve el coeficiente del exponente exp del polinomio p.*/
unsigned int coeficiente(TPolinomio p, unsigned int exp){
	if(p==NULL){
		return 0;
	}
	else{
		while(p!=NULL && p->exp >= exp){
			if(p->exp == exp){
				return p->coef;
			}
			p=p->sig;
		}
		return 0;
	}
}

TPolinomio crearNodo(unsigned int coef, unsigned int exp){
	TPolinomio nuevo = (TPolinomio) malloc(sizeof(struct TMonomio));
	if(nuevo == NULL){
		perror("No se pudo reservar memoria\n");
		exit(-1);
	}
	else{
		nuevo->coef = coef;
		nuevo->exp = exp;
		nuevo->sig = NULL;
	}

	return nuevo;
}

/* Insertar el monomio con coeficiente coef, y exponente exp en el polinomio,
 * de manera que el polinomio quede ordenado. Asegurarse que no se insertan
 * monomios cuyo coeficiente sea 0 y tampoco dos monomios con el mismo exponente.
 * Si al insertar un monomio ya hay otro con el mismo exponente los coeficientes
 * se sumarán. Se puede asumir que el valor del coeficiente siempre será un numero
 * natural (entero no negativo).*/
void insertar(TPolinomio *p, unsigned int coef, unsigned int exp){
	if(coef!=0){
		unsigned int new_coef = coef + coeficiente(*p, exp);	// coef + coeficiente anterior

		TPolinomio current = *p;
		TPolinomio prev = NULL;
		while(current!=NULL && current->exp > exp){	// busco donde se mete
			prev = current;
			current = current->sig;
		}

		if(coeficiente(*p, exp) != 0){	// es que en current tengo que cambier coef
			current->coef = new_coef;
		}
		else{	// si no estaba, lo meto
			TPolinomio nuevo = crearNodo(coef,exp);
			if(prev!=NULL){
				prev->sig = nuevo;
				nuevo->sig = current;
			}
			else{
				*p = nuevo;
				nuevo->sig = current;
			}
			
		}
	}
}

void mostrarLosCerosDeEnMedio(TPolinomio p){
	int exp_arriba = (int) (p->exp)-1;
	int exp_abajo = 0;

	if(p->sig != NULL){	// si hay siguiente, es que el exp se calcula asi
		exp_abajo = (int) (p->sig)->exp +1;
	}

	for(int i=exp_arriba; i>=exp_abajo; i--){
		printf("(%u,%u)", 0, i);
	}
}

/*Escribe por la pantalla el polinomio con un formato similar al siguiente:
 * [(3,7)(0,6)(2,5)(0,4)(3,3)(0,2)(5,1)(9,0)] para el polinomio ejemplo.
 * Ten en cuenta que los monomios de exponente menor al grado del polinomio
 * con coeficiente 0 también aparecen en la salida, aunque no estén almacenados
 * en el polinomio. */
void imprimir(TPolinomio p){
	printf("[");
	while(p!=NULL){
		printf("(%u,%u)", p->coef, p->exp);
		mostrarLosCerosDeEnMedio(p);
		
		p=p->sig;
	}
	printf("]\n");
}

/* Elimina todos los monomios del polinomio haciendo que el polinomio resultante
 * sea el polinomio 0.*/
void destruir(TPolinomio *p){
	while(*p!=NULL){
		TPolinomio temp = *p;
		*p = (*p)->sig;
		free(temp);
	}
}

//Parte 2. Notable
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
	
	FILE* fent = fopen(nombre, "rt");
	if(fent == NULL){
		perror("No se pudo abrir el fichero\n");
		exit(-1);
	}

	polinomioCero(p);
	char coef, exp;
	unsigned int coef_num, exp_num;
	
	while(fscanf(fent, "%c%c", &coef, &exp)==2){
		coef_num = coef - '0';
		exp_num = exp - '0';
		insertar(p,coef_num,exp_num);
	}
	fclose(fent);
}

//Parte 3. Sobresaliente
/* Evalúa el polinomio para el valor x y devuelve el resultado.
 * Para la evaluación del polinomio debes utilizar el método de Horner,
 * de manera que ax^4 + bx^3+ cx^2+dx+e puede evaluarse
 * en un valor cualquiera x teniendo en cuenta que es equivalente
 * a: (((ax+b)x+c)x+d)x+e.
*/
int evaluar(TPolinomio p,int x){
	if(p==NULL){
		return 0;
	}
	else{
		unsigned int n = grado(p);
		int horner = coeficiente(p, n);	// se empieza con el primer coeficiente
		//n--;
		while(n>0){
			horner = horner * x + coeficiente(p, n-1);
			n--;	
		}

		return horner;
	}
}
