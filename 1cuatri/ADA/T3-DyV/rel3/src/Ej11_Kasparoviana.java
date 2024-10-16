
public class Ej11_Kasparoviana {
	public static int[][] productoK (int n){
		int [][] res = new int[(int) Math.pow(2, n)][(int) Math.pow(2, n)];	// inicializo la matriz, que tendrá 2^n filas y columnas
								// fila					columna
		if (n==0) {
			res[0][0]=1;	// caso base
		}
		else {
			int [][] aux = productoK(n-1);	// como las matrices se calculan recursivamente, debo conocer la anterior
			
			int ordenAux = (int) Math.pow(2, n-1);	// para recorrer la matriz mas pequeña (y asi multiplicarla por 2)
			
			for (int i = 0; i<ordenAux; i++) {	// acceso a la fila
				for (int j = 0; j<ordenAux; j++) {	// acceso a la columna
					// debemos ver la forma en la que cambian los signos por posiciones según esquinas: [SI, SD, II, ID] 
					// S -> Superior, I -> Inferior o Izquierda, D -> Derecha
					res[i][j] = 2 * aux[i][j];	// arriba izq
					res[i][j + ordenAux] = -2 * aux[i][j];	// arriba der
					res[i + ordenAux][j] = -2 * aux[i][j];	// abajo izq
					res[i + ordenAux][j + ordenAux] = 2 * aux [i][j];	// abajo der
				}
			}
		}
		return res;
	}
	
	// esto no es necesario, pero quería hacerlo para asentar lo que estaba haciendo
	public static int[][] matrizK (int n){
		int [][] res = new int[(int) Math.pow(2, n)][(int) Math.pow(2, n)];
		if (n==0) {
			res[0][0]=1;	// caso base
		}
		else {
			int [][] aux = matrizK(n-1);
			int ordenAux = (int) Math.pow(2, n-1);
			for(int row=0; row<ordenAux; row++) {
				for(int col=0; col<ordenAux; col++) {
					res[row][col] = aux[row][col];
					res[row][col + ordenAux] = -aux[row][col];
					res[row + ordenAux][col] = -aux[row][col];
					res[row+ ordenAux][col + ordenAux] = aux[row][col];
				}
			}
		}
		return res;
	}
	
	public static void main (String[] args) {
//		int[][] matrix = productoK(2);
		int[][] matrix = matrizK(2);
	    for (int i = 0; i < matrix.length; i++)
	    {
		 // length returns number of rows
		 System.out.print("fila " + i + " : ");
		 for (int j = 0; j < matrix[i].length; j++)
		 {
		    // here length returns # of columns corresponding to current row
		    System.out.print(matrix[i][j] + "   ");
		 }
	    System.out.println();
	   }
	}
	

}
