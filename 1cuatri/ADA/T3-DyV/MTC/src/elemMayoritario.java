
public class elemMayoritario {
	
	public static int elemMayor(int[] a) {
		return elemMayor(a, 0, a.length-1);
	}
	
	private static int elemMayor(int[] a, int start, int end) {
		int n = (start+end)/2;
		if(end == start) {	// tiene 1 elem
			return a[start];
		}
		else if (end-start == 1) { 	// tiene 2 elem
			if(a[start]==a[end]) {	// se repite 2 veces
				return a[start];
			}
			else {					// no vale, pues no cumple la cond de elem mayoritario
				return Integer.MIN_VALUE;
			}
		
		}
		else if (end-start == 2) {	// tiene 3 elem
			if(a[start] == a[start+1] || a[start]==a[end]) {	// uno se repite 2 veces
				return a[start];
			}
			else {					// no vale, pues no cumple la cond de elem mayoritario
				return Integer.MIN_VALUE;
			}
		}
		else{
			int mid = (start+end)/2;
			int izq = elemMayor(a, start, mid);
			int der = elemMayor(a, mid+1, end);
			
			if(izq == der) {
				return izq;
			}
			else if (frecuencia(a,izq) > n/2) {
				return izq;
			}
			else if (frecuencia(a,der) > n/2) {
				return der;
			}
			else {
				return Integer.MIN_VALUE;
			}
		}
		
		// T(n) = 2*T(n/2) + An + B -> T(n) = 0(n*logn)
	}
	
	private static int frecuencia(int[] a, int n) {
		int i = 0;
		for(int j=0; j<a.length-1; j++) {
			if(a[j] == n) {
				i++;
			}
		}
		
		return i;
	}

	public static void main(String[] args) {
		int[] a = {2,1,1,1,2,2};
		System.out.println(elemMayor(a));
		
		int[] b = {1,2,1,2,1,1};
		System.out.println(elemMayor(b));
		
		int[] c = {1,5,8,3,5};
		System.out.println(elemMayor(c));

	}

}
