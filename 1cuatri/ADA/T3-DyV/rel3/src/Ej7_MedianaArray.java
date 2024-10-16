import java.util.Arrays;

public class Ej7_MedianaArray {
	public static int mediana(int[] a) {	// ordenarlo es O(nlogn)
		Arrays.sort(a);
		return a[(a.length-1)/2];
	}
	
	private static int mediana_DyV(int[] a) {	// partir es O(n)
		return 0;
	}
	
	public static void main(String[] args) {
		System.out.println(mediana_DyV(null));
	}
}
