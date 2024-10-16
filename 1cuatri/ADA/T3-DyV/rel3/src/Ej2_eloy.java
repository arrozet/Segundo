

public class Ej2_eloy {
//	Dado un vector V de enteros todos distintos y un número entero S. Diseñar un
//	algoritmo que en tiempo O(n lg n) determine si existen o no dos elementos de V
//	tales que su suma sea S.
	
	public static Boolean existeSuma(int[] v, int S, int sup, int infi, int medio) {
		Boolean res = false;
		
		if (Sumalista(v,S,medio)) {
			res = true;
		} else if (infi==medio) {
			res = Sumalista(v,S,sup);
		} else if (existeSuma(v, S, sup, medio, ((sup+medio)/2))){
			res = true;
		}  else if (existeSuma(v, S, medio, infi, ((medio+infi)/2) )){
			res = true;
		}
		
		return res;
	}
	
	public static Boolean Sumalista(int[] v, int S, int m) {
		Boolean res = false;
		for(int i=0; i<v.length ; i++){
			if( ( (v[m] + v[i]) == S) && (m!=i) ) {
				res = true;
			}
		}
		return res;
	}
	
	public static void main(String[] args) {
		int[] a = {-11,-10,-6,3,-3,0,5,55,56,57,58};
		Boolean n = existeSuma(a,10,10,0,5);
		System.out.println(n);
	}
	
	// complejidad T(n) = 2T(n/2) + f(n) --> por el teorema maestro reducido
	// a = 2, b = 2, d = 1 -> a=b^d -> O((n^d)*logn) = O(nlogn)
}
