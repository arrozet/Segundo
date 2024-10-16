public class KesimoElemento {

	public static int buscarKesimo(int[] a, int k) {
		return buscarKesimo(a, k, 0, a.length - 1);
	}

	private static int buscarKesimo(int[] v, int k, int ini, int fin) {
		int p = partir(v, ini, fin);
		
		if(p==k) {
			return v[p];
		}
		else if (p>k) {
			return buscarKesimo(v, k, ini, p-1);
		}
		else{
			return buscarKesimo(v, k, p+1, fin);
		}
	}
	
	private static void intercambia(int []a, int i, int j){
		int aux = a[i];
		a[i] = a[j];
		a[j] = aux;
		
	}

	
	private static int partir(int[] a, int inf, int sup){
		int pivote = a[inf]; int i = inf+1; int j = sup; 
		do {
			while((i<=j) && (a[i] <= pivote)){ i++; }
			while((i<=j) && (a[j] > pivote)){ j--; }
			if (i<j){ intercambia(a,i,j); }
		}while (i <= j);
 
		intercambia(a,inf,j);
		return j;
		
	}


}
