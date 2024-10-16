
public class cambioMoneda_izqDer {
	public static int cambioMonedas(int[] mon, int cant) {
	    int nFilas = mon.length;
	    int nCols = cant + 1;
	    int[][] C = new int[nFilas][nCols];

	    for (int i = 0; i < nFilas; i++) {
	        C[i][0] = 0; // Para pagar 0, hacen falta 0 monedas
	    }

	    for (int i = 0; i < nFilas; i++) {
	        for (int j = 1; j < nCols; j++) {
	            if (i == 0 && j < mon[0]) {
	                C[i][j] = Integer.MAX_VALUE; // La cantidad no se puede pagar
	            } else if (i == 0) {
	                C[i][j] = 1 + C[i][j - mon[0]]; // Uso una moneda de valor mon[0] y calculo el resto
	            } else if (j < mon[i]) {
	                C[i][j] = C[i - 1][j]; // Si el valor de mon[i] excede la cantidad a pagar, uso monedas de menor valor
	            } else {
	                C[i][j] = Math.min(C[i - 1][j], 1 + C[i][j - mon[i]]); // Uso monedas de valor mon[i] y calculo el resto
	            }
	        }
	    }
	    return C[mon.length - 1][cant];
	}


}
