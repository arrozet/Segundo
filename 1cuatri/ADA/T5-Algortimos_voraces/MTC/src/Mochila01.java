
public class Mochila01 {
	
	// Precondición: los objetos están ordenados decrecientemente por valor -> 1er -> ordenado mayor a menor
	public static int[] resolver(int W, int[] p, int[] w) {
		// Hay que ordenar p respecto a w
		// ordenar(p,w,pOrd,wOrd);
		
		int[] sol = new int[p.length];
		int i=0;
		int sobrante = W;
		
		while(i<p.length) {
			if(p[i] <= sobrante) {
				sol[i] = 1;
				sobrante -= p[i];
			}
			i++;
		}
		
		return sol;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
