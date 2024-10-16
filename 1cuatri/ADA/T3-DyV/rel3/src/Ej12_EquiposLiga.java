
public class Ej12_EquiposLiga {
	public static int[][] liga(int n){
		//precondición: n=2^i, i natural positivo != 0 -> equipos multiplos de 2
		int[][] liga = new int[n][n];
		
		// hago el cuadrante
		if(n==1) {
			liga[0][0] = 1;
			
		}
		else {
			int[][] aux = liga(n/2);
			int orden_aux = n/2;
			
			for(int row = 0; row<orden_aux; row++) {
				for(int col = 0; col<orden_aux; col++) {
					liga[row][col] = aux[row][col];		// esquina SI
					liga[row+orden_aux][col+orden_aux] = aux[row][col];	// esquina ID
					liga[row+orden_aux][col] = n/2 + aux[row][col];	// esquina II
					liga[row][col+orden_aux] = n/2 + aux[row][col];	// esquina SD
				}
			}
		}
		
		return liga;
	}
	
	public static void main (String[] args) {
		//precondición: n=2^i, i natural positivo != 0
		
		int[][] matrix = liga(8);
	    for (int row = 0; row < matrix.length; row++){
		 
		 for (int col = 0; col < matrix[row].length; col++){
			if(col == 0) {
				System.out.print(matrix[row][col] + " | ");
			}
			else {
				System.out.print(matrix[row][col] + "  ");
			}
			
		 }
	    System.out.println();
	   }
	}
}
