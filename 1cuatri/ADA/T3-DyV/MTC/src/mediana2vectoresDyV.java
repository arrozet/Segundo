
public class mediana2vectoresDyV {
	public static int mediana(int[] a, int[] b) {
		return mediana(a,0,a.length-1,b,0,b.length-1);
	}
	private static int mediana(int[] a, int iniA, int finA, int[] b, int iniB, int finB) {
		int mediana_total = -1;
		
		int n = finA - iniA + 1;
		if (n==1) {
			return Math.min(a[iniA], b[iniB]);
		}
		else if (n == 2) {
			if (a[finA] < b[iniB]) {
				return a[finA];
			}
			else if (b[finB] < a[iniA]) {
				return b[finB];
			}
			else {
				return Math.max(a[iniA], b[iniB]);
			}
		}
		else {
			int medioA = (iniA+finA)/2;
			int medioB = (iniB+finB)/2;
			
			if(a[medioA] == b[medioB]) {
				mediana_total = a[medioA];
			}
			else if(a[medioA] < b[medioB]) {
				return mediana(a,medioA,finA,b,iniB,medioB);
			}
			else {	// a[medioA] > b[medioB]
				return mediana(a,iniA,medioA,b,medioB,finB);
			}
		}
		
		
		
		
		return mediana_total;
	}
	public static void main(String[] args) {
		int[] a = {1,2,3,10};
		int[] b = {4,7,8,15};
		
		System.out.println(mediana(a, b));

	}

}
