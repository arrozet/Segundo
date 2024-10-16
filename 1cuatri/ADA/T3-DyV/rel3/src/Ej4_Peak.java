
public class Ej4_Peak {
	// array unimodal -> elementos organizados en secuencia no vacía estrictamente creciente, seguida de secuencia no vacía estrictamente decreciente
	private static int peak(int[] A) {
		int peak = Integer.MIN_VALUE;
		for(int i=0; i<A.length; i++) {
			if(A[i]>peak) {
				peak = A[i];
			}
		}
		return peak;
	}
	
	private static int peak_DyV(int[] A) {
		return peak_DyV(A,0,A.length-1);
	}
	private static int peak_DyV(int[] A, int start, int end) {
		int peak = Integer.MIN_VALUE;
		// uso busqueda binaria para poder hallar complejidad O(logn), pues el de FB es O(n)
		int mid = (start+end)/2;
		
		if(		(mid == end && A[mid] > A[mid-1]) ||			// extremo derecho
				(mid == start && A[mid] > A[mid+1]) ||			// extremo izquierdo
				(A[mid] > A[mid+1] && A[mid] > A[mid-1])){	 	// no es un extremo 
			peak = A[mid];
		}
		
		// ---------------------------------------------------
//			realmente no hacia falta considerar los extremos, ya que se trataba de un array unimodal con
//			dos secuencias no vacías, una creciente y otra decreciente. así que nunca va a caer el elemento maximo en un extremo
//			Por tanto, considerando los extremos esto serviría para cualquier array, sin importar su orden
		// ---------------------------------------------------
		
		// si no es ningun extremo ni es el que está en mitad
		else if(A[mid] < A[mid+1]) {	// si es más grande el de la derecha, el que buscamos está en la derecha
			peak = peak_DyV(A, mid+1, end);
		}							// NOTA: el = da igual del <= da igual donde lo pongas
		else if(A[mid] <= A[mid-1]){	// si es más grande el de la izquierda, el que buscamos está en la izquierda
			peak = peak_DyV(A, start, mid);
		}
		return peak;
	}
	
	public static void main(String[] args) {
		int[] a = {1,5,7,9,6,2};
		System.out.println(peak(a));
		System.out.println(peak_DyV(a));
		
		int[] b = {1,5,7,9,6,10};
		System.out.println(peak(b));
		System.out.println(peak_DyV(b));
		
		int[] c = {10,9,18,7,81,7,5};
		System.out.println(peak(c));
		System.out.println(peak_DyV(c));
		
		int[] d = {5,7,8,3,4,9,1,3};
		System.out.println(peak(d));
		System.out.println(peak_DyV(d));
	}
}
