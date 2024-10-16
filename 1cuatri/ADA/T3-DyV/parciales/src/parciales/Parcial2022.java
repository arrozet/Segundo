package parciales;

public class Parcial2022 {
	
	public static int encuentraInicioFB(int[] fila) {
		int ini = 0;
		int i = 1;
		while (i < fila.length && fila[i - 1] < fila[i]) {
			i++;
		}
		if (i < fila.length) {
			ini = i;
		}
		return ini;
	}
	/*	a)
	 * ANÁLISIS COMPLEJIDAD PEOR CASO - será cuando el array esté completamente ordenado y se hagan todas las comparaciones sin necesidad
	 * 			  bucle				   comparacion de salida que falla
	 * 				|					|   if
	 * 				|                   |	|
	 * T(n) = sum(1,fila.length-1, 2) + 1 + 1 = 2(n-1)+2 = 2n - 2 + 2 = 2n € 0(n)
	 * 
	 * Necesitamos por tanto O(log n) para conseguir un algortimo más eficiente
	 * */

	
	// 3b
	
	// PRECONDICIÓN: el elemento máximo de la mitad ordenada con elementos más pequeños es MENOR
	// que el elemento mínimo de la mitad con elementos más grandes
	// (el chaval que tenga el ticket 500 no va a entrar antes que el que tenga el ticket 200,
	// por lo que si el chaval 500 quiere volver a ver la peli se tendrá que poner al fondo de la cola
	// otra vez, pero en cuyo caso el chaval 200 o bien no estará en la cola o estará delante de él)
	public static int encuentraInicioDyV(int[] fila) {
		return encuentraInicioDyV(fila, 0, fila.length-1);
	}
	
	public static int encuentraInicioDyV(int[] fila, int ini, int fin) {
		int mid = (fin+ini)/2;
		
		// si los indices son validos y el de la izq y der son mas grandes que mi elemento, estoy en el minimo
		if(mid-1 >= 0 && mid+1 <= fin && 
				fila[mid-1]>fila[mid] && fila[mid+1]>fila[mid]) {
			return mid;
		}
		// esto implicaria que el array esta ordenado, por lo que el elemento minimo es el primero
		else if (fila[ini] < fila[fin]) {
			return ini;
		}
		else {
			// si el de la derecha es más grande que en el que estoy, necesariamente está a la izquierda
			// esto debe ocurrir ya que el subarray siempre está ordenado crecientemente. Si no es más grande,
			// eso significa que el estoy en el subarray de los numeros mayores, y me tengo que ir a la derecha
			if(fila[fin] > fila[mid]) {
				return encuentraInicioDyV(fila,ini, mid);
			}
			else {
				return encuentraInicioDyV(fila,mid,fin);
			}
		}
	}
	
	/*
	 * ANÁLISIS COMPLEJIDAD TEOREMA MAESTRO (peor caso)
	 * 		1er if  if del else
	 * 		  | 2do if|   llamada recursiva de ultima condicion
	 * 		  |   |   |     |
	 * T(n) = 4 + 1 + 1 + T(n/2) = 6 + T(n/2)
	 * 
	 * f(n) = 6 € 0(n^0) -> d=0
	 * 
	 * a = 1, b = 2, d = 0 --> 1 = 2^0 = 1 -> T(n) € 0(n^0 * log n) = 0(log n)
	 * */
	
	// SOLUCION WUOLAH (menos entendible)
	public static int encuentraInicioDyV2(int[] a) {
		return encuentraInicio(a, 0, a.length - 1);
	}
	
	// Precondición: izq <= der
	private static int encuentraInicio(int[] a, int izq, int der) {
		int pos = -1;
		
		if (izq == der) {
			pos = izq;
		} else if (izq == der - 1) {
			if (a[izq]< a[der]) {
					pos = izq;
			}else {
				pos = der;
			}
		} else {
			int m = (izq + der) / 2;
			if (a[m] > a[izq] && a[m] < a[der]) {
					pos = izq;
			} else if (a[m] > a[izq]) {
				pos = encuentraInicio(a, m, der);
			} else {
				pos = encuentraInicio(a, izq, m);
			}
		}
		return pos;
	}
	
	
	// PRUEBAS
	public static void main(String[] args) {
		int[] a = {234,1,3,7,23,63,78};
		int[] b = {234,235,267,438,23,63,78};
		
		System.out.println(encuentraInicioDyV(a));
		System.out.println(encuentraInicioDyV2(a));
		
		System.out.println(encuentraInicioDyV(b));
		System.out.println(encuentraInicioDyV2(b));

	}

}
