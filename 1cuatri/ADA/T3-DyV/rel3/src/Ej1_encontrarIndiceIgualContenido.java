

public class Ej1_encontrarIndiceIgualContenido {
	// V está ordenado con n enteros distintos
	private static int encuentraI(int[] a, int inicio, int fin) {
		int i = Integer.MIN_VALUE;
		if(inicio <= fin) {
			int mid = (inicio + fin) / 2;
			if(a[mid] == mid) {
				i = mid;
			}
			else if (a[mid] > mid) {	// si el numero contenido en el indice es > indice, el que buscas esta a la izq
				i = encuentraI(a, inicio, mid-1);
			}
			else {
				i = encuentraI(a, mid+1, fin);
			}
		}
		return i;
	}
	
	public static void main(String[] args) {
		int[] a = {-7,1,2,3,44344};
		
		System.out.println(encuentraI(a, 0, a.length-1));
	}
}
