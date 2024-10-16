public class ArraysConRepeticiones {

	//Precondición: hay un elemento repetido=> v.length >= 2
	// y son consectuvos
	public static int encuentraElem(int [] v) {
		return encuentraElem(v,0,v.length-1);
	}
	
	private static int encuentraElem(int [] v, int izq, int der) {
		int mid = (izq+der) / 2;
		
		if(izq == der) {	// si me queda un solo elemento
			return Integer.MIN_VALUE;
		}
		else if(mid-1 >= 0 && v[mid] == v[mid-1]) {	// miro la parte izq (asegurandome de que existe)
			return v[mid];
		}
		else if(mid+1 < v.length && v[mid] == v[mid+1]) {	// miro la parte der (asegurandome de que existe)
			return v[mid];
		}
		else {
			// como son consecutivos los numeros del array
			// si el indice es igual al numero que hay en el indice, entonces a la izquierda NO está el repetido
			if(mid == v[mid]) {
				return encuentraElem(v, mid+1, der);
			}
			// si el indice no es igual, necesariamente queda a la izquierda el elemento repetido
			else{
				return encuentraElem(v, izq, mid-1);
			}
			
			
		}
	}
	
	public static void main(String[] args) {
		int[] v = {	};
		System.out.println(encuentraElem(v));
		
		int[] a = {0,1,2,3,4,4,5,6};
		System.out.println(encuentraElem(a));
		
		int[] b = {0,1,2,3,4,4,5,6};
		System.out.println(encuentraElem(b));
	}
}
