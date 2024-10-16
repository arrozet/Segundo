

public class subvectorSumaMaxima {

	static class Result {
        int maxSum;
        int leftIndex;
        int rightIndex;
        
        public Result(int leftIndex, int rightIndex, int maxSum) {
            this.maxSum = maxSum;
            this.leftIndex = leftIndex;
            this.rightIndex = rightIndex;
        }
        
        
        
        public int getMaxSum() {
			return maxSum;
		}



		public int getLeftIndex() {
			return leftIndex;
		}



		public int getRightIndex() {
			return rightIndex;
		}



		@Override
        public String toString() {
        	return "("  + leftIndex + ", " + rightIndex + ", " + maxSum + ")";
        }
    }
	
	// O(n^2)
	public static Result subvectorSM_FB(int[] a) { // fb significa fuerza bruta
		int inicio = 0, fin = 0, sumaMaxima = a[0];
		int sumaTemp = 0;
		
		for(int i=0; i<a.length; i++) {
			sumaTemp = 0;
			for(int j=i; j<a.length; j++) {
				sumaTemp+=a[j];
				if (sumaTemp>sumaMaxima) {
					sumaMaxima = sumaTemp;
					inicio = i;
					fin = j;
				}
			}
			
		}
		
		return new Result(inicio, fin, sumaMaxima);
	}
	
	
	// O(n) ?
	public static Result subvectorSM_FB2(int[] a) { // fb significa fuerza bruta
		int inicio = 0, fin = 0, sumaMaxima = a[0];

		int sumaTemp = 0, minimo = 0;
		
		for(int i=0; i<a.length; i++) {
			sumaTemp += a[i];
			sumaMaxima = Math.max(sumaTemp-minimo, sumaMaxima);
			minimo = Math.min(sumaTemp, minimo);	// si no hay elementos negativos, minimo siempre sera 0
													// si los hay, al restarsele el minimo a la suma temp, estas eliminando siempre el factor
													// negativo, que haria que la suma no fuese maxima
			
			
			
		}
		
		return new Result(inicio, fin, sumaMaxima);
	}
	
	
	// Divide y vencerás -> O(n*logn)
	public static Result subvectorSM_DyV(int[] a) {
		return subvectorSM_DyV(a, 0, a.length-1);
	}
	
	private static Result subvectorSM_DyV(int[] a, int start, int end) {
		if(start == end) {
			return new Result (start,end, a[start]);
		}
		else {
			int mid = (start+end)/2;
			Result izq = subvectorSM_DyV(a, start, mid);	// si le pones un -1 debería ir en teoría pero da stack overflow
			Result der = subvectorSM_DyV(a, mid+1, end);
			Result cruza = subvector_cruzado(a,start,end);
			
			int maximo = Math.max(izq.maxSum, Math.max(der.maxSum, cruza.maxSum));
			
			if(maximo == izq.maxSum){
				return izq;
			}
			else if (maximo == der.maxSum) {
				return der;
			}
			else {
				return cruza;
			}
		}
	}
	
	private static Result subvector_cruzado(int[] a, int start, int end) {
		int mid = (start+end) / 2;
		int sumaIzq = a[mid], sumaDer = a[mid];
		int indiceIzq = mid, indiceDer = mid;
		int sumaTemp = 0;
		
		// hallo la suma máxima de un subvector con end = medio (mitad izquierda)
		for(int i=mid; i>=start; i--) {
			sumaTemp+=a[i];
			if(sumaTemp>sumaIzq) {
				sumaIzq = sumaTemp;
				indiceIzq = i;
			}
		}
		
		sumaTemp=0;
		// hallo la suma máxima de un subvector con inicio = medio - 1 (mitad derecha)
		for(int i=mid+1; i<=end; i++) {
			sumaTemp+=a[i];
			if(sumaTemp>sumaDer) {
				sumaDer = sumaTemp;
				indiceDer = i;
			}
		}
		
		return new Result(indiceIzq, indiceDer, sumaIzq+sumaDer);
		
		
		
	}
	
	// main
	public static void main(String[] args) {
		int[] a = {1,2,3,7,8};
		System.out.println(subvectorSM_FB(a));
		System.out.println(subvectorSM_FB2(a));
		System.out.println("DyV: " + subvectorSM_DyV(a));
		
		
		System.out.println();
		int[] b = {-1,-2,-3,-7,-8};
		System.out.println(subvectorSM_FB(b));
		System.out.println(subvectorSM_FB2(b));
		System.out.println("DyV: " + subvectorSM_DyV(b));
	
		
		System.out.println();
		int[] c = {1,2,-90,7,8};
		System.out.println(subvectorSM_FB(c));
		System.out.println(subvectorSM_FB2(c));
		System.out.println("DyV: " + subvectorSM_DyV(c));
		
		
		System.out.println();
		int[] d = {-5,2,3,-1,80};
		System.out.println(subvectorSM_FB(d));
		System.out.println(subvectorSM_FB2(d));
		System.out.println("DyV: " + subvectorSM_DyV(d));
		
	}

}
