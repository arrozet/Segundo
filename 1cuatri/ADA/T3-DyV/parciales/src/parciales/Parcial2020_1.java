package parciales;

public class Parcial2020_1 {
	public static int frec(int[] v, int x) {
		return  buscarDer(v, x, 0, v.length-1) - buscarIzq(v, x, 0, v.length-1) +1;
	}
	
	public static int buscarIzq(int[] v, int x, int ini, int fin) {
	   if(fin==ini) {
		   return ini;
	   }
	   else {
		   int mid = (fin+ini)/2;
		   if(v[mid]<x) {
			   return buscarIzq(v, x, mid+1, fin);
		   }
		   else if(v[mid]>x) {
			   return buscarIzq(v, x, ini, mid-1);
		   }
		   else {
			   if(v[mid-1] != x) {
				   return mid;
			   }
			   else {
				   return buscarIzq(v, x, ini, mid-1);
			   }
		   }
	   }
	}

	public static int buscarDer(int[] v, int x, int ini, int fin) {
		   if(fin==ini) {
			   return ini;
		   }
		   else {
			   int mid = (fin+ini)/2;
			   if(v[mid]<x) {
				   return buscarDer(v, x, mid+1, fin);
			   }
			   else if(v[mid]>x) {
				   return buscarDer(v, x, ini, mid-1);
			   }
			   else {
				   if(v[mid+1] != x) {
					   return mid;
				   }
				   else {
					   return buscarDer(v, x, mid+1, fin);
				   }
			   }
		   }
		}

	
	
	/*
	 * ANALISIS DE COMPLEJIDAD
	 * Hemos partido la funcion en 2 casos para que una llamada no llame a 2 func recursivas, y 
	 * asi se puede hallar con el T. maestro.
	 * Tenemos que nuestras 2 funciones funcionan de forma parigual. 
	 * Ambas con T(n) = T(n/2) + k, siendo k un numero cte de operaciones elementales.
	 * Por tanto, por el T. Maestro tenemos que a=1,b=2, k€0(n^0)->d=0
	 * Entonces, 1=2^0 -> T(n)€0(logn)
	 * 
	 * Sabiendo que nuestras dos funciones tienen complejidad 0(logn), hallamos la complejidad del metodo general
	 * 
	 * Por tanto:
	 * T(n) = logn + logn +1 = 2logn + 1 € 0(logn)
	 * 
	 * 
	 * */

	
	public static void main(String[] args) {
		int[] a = {1,2,3,4,6,12};
		System.out.println(frec(a, 4));
		
	}
}
