
public class Ej3_NumInversiones {
	// A contiene valores naturales todos distintos
	private static int numInversiones (int[] A) {
		int numInversiones = 0;
		for(int i=0; i<A.length-1; i++){
			for(int j=A.length-1; j>=i; j--) {
				if(A[i]>A[j]) {
					numInversiones++;
				}
			}
		}
		
		return numInversiones;
	}
	
	private static int numInversiones_DyV (int[] A) {
		return numInversiones_DyV(A, 0, A.length-1);
	}
	
	private static int numInversiones_DyV (int[] A, int start, int end) {
		return 0;
	}
	
	public static void main(String[] args) {
		int[] a = {2,4,1,3,5};
		System.out.println(numInversiones(a));
		System.out.println(numInversiones_DyV(a));
	}
}
