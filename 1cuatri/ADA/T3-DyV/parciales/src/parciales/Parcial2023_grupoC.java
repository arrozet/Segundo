package parciales;

public class Parcial2023_grupoC {
	// EJERCICIO 3
	
	// PRECONDICIÓN: vector de naturales creciente sin repeticiones
	// ¡¡OJO!! -> Puede ser de la forma [3,4,7,45], no tiene por qué empezar por 0
	
	// APARTADO 1
	public static int faltanteMasPequeFB(int[] a) {
		int i = 1;
		while(i<a.length) {
			if(a[i] - a[i-1] > 1) {
				return a[i-1] + 1;
			}
			i++;
		}
		return -1;
	}
	
	/* APARTADO 2
	 * ANALISIS COMPLEJIDAD ALGORITMO
	 * Por tanto, considerando las comparaciones como operaciones básicas, en el peor caso será:
	 * 
	 * 	
	 * T(n) = sum(1,n, 2) + 1 = 2(n-1) + 1 = 2n-1 € 0(n)
	 * */
	
	// APARTADO 3
	public static int faltanteMasPequeDyV(int[] a) {
		return faltanteMasPequeDyV(a, 0, a.length-1, a[0]);
	}
	
	// paso primerElem para poder determinar por qué mitad tirar, ya que a partir del primero, los elementos deberian ser consecutivos sin repes
	public static int faltanteMasPequeDyV(int[] a, int ini, int fin, int primerElem) {	
		int mid = (ini+fin) / 2;
		if(ini==fin || (fin-ini+1 == 2 && a[ini] == a[fin] - 1 )) {	// no quedan elementos o quedan 2 sin faltantes
			return -1;				// tengo que poner la segunda condicion porque sino se puede quedar en un bucle inf
									// ejemplo -> a={1,2,3} -> se queda haciendo mid = 1 y fin = 2 infinitamente
		}
		// compruebo que el indice que voy a mirar es correcto
		else if (mid+1 <= fin && a[mid+1] != a[mid]+1) {
			return a[mid]+1;	// un elemento será faltante cuando el consecutivo no se corresponda con elem+1
		}
		
		else {
			if(a[mid] == mid+primerElem) {	// si el elemento en el que estoy coincide con la primerElem + mi posicion,
											// hasta ese elem todos son consecutivos -> no hay faltantes -> miro la otra mitad
				return faltanteMasPequeDyV(a, mid, fin, primerElem);
			}
			else {
				return faltanteMasPequeDyV(a, ini, mid, primerElem);
			}
		}
	}
	
	/*
	 * ANALISIS DE COMPLEJIDAD (peor caso) CON TEOREMA MAESTRO - considerando comparaciones como operaciones básicas
	 * 
	 *         2do if
	 * 	  1er if  | 3er if  llamada recursiva
	 * 		  |   |   |      |
	 * T(n) = 1 + 2 + 1 + T(n/2) = 4 + T(n/2)
	 * 
	 * Usando el teorema maestro reducido tenemos que f(n) = 4 € 0(n^0) -> d=0
	 * Como a = 1, b = 2 y d = 0 -> 1 = 2^0 = 1 -> T(n) € 0(n^d * log n) = 0(n^0 * log n) = 0(log n)
	 * */
	
	public static void main(String[] args) {
		int[] a = {0, 1, 2, 5, 7};
		int[] b = {3, 4, 5, 6, 7, 10, 12};
		int[] c = {1,2,3};
		System.out.println(faltanteMasPequeFB(a));
		System.out.println(faltanteMasPequeDyV(a));
		System.out.println(faltanteMasPequeFB(b));
		System.out.println(faltanteMasPequeDyV(b));
		System.out.println(faltanteMasPequeFB(c));
		System.out.println(faltanteMasPequeDyV(c));

	}

}
