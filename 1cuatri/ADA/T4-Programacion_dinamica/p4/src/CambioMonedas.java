public class CambioMonedas {
	private int M;
	private int [] d;
	private int [][] tabla; //Mínimo número de monedas. Como la calidad siempre es positiva
							//se usará -1 para representar infinito.
	private int [][] opcion; //k elegido para la primera decisión del subproblema.
	
	public CambioMonedas(int M, int [] d) {
		this.M = M;
		this.d = d;
		tabla = null;
		opcion = null;
	}
	
	/**
	 * Método para rellenar la tabla 
	 */
	public int resolverBottomUp() {
		tabla = new int [d.length][M+1];
		opcion = new int [d.length][M+1]; 
		
		//Rellenamos la tabla de arriba a abajo y de izquierda a derecha
		for (int i = 0; i<d.length; i++) {
			for (int j = 0; j<= M; j++) {
				if (j == 0) {
					tabla[i][j] = 0;
					opcion[i][j] = 0;
				}else if (i == 0) {
					if(j%d[i] != 0) {
						tabla[i][j] = -1;
						opcion[i][j] = -1;
					}
					else {
						tabla[i][j] = j/d[i];
						opcion[i][j] = 1;
					}
					
				
				}else {//i> 0
					//En tabla[i,j] vamos a ir guardando el mínimo encontrado
					tabla[i][j] = tabla[i-1][j]; //0+M(i-1,j-0)
					opcion[i][j] = 0;
					for(int k = 1; k <= j / d[i]; k++) {
						// debo escoger el mínimo
						int posibilidad = sumaInf(k, tabla[i-1][j-k*d[i]]);
						if(menorInf(posibilidad, tabla[i][j])) {	// uso menorInf y sumaInf pq consideran que -1 es infinito
							tabla[i][j] = posibilidad;
							opcion[i][j] = k;
						}
						
					}//for k
					
				
				}//else
			}//for j
		}//for i
		return tabla[d.length-1][M];
	}
	/**
	 * Devuelve la suma de dos números, teniendo en cuenta que -1 significa infinito.
	 */
	private int sumaInf(int a, int b) {
		int suma = -1;
		if (a != -1 && b!=-1) {
			suma = a+b;
		}
		return suma;
	}
	/**
	 * Devuelve a < b, teniendo en cuenta que -1 representa infinito
	 */
	private boolean menorInf(int a, int b) {
		boolean res = false;
		if (a!= -1) {
			if (b == -1) {
				res = true;
			}else {//ninguno es infinito
				res = a < b;
			}
		}
		return res;
	}
	
	
	/**
	 * Reconstruimos la solución utilizando la información en la tabla "opcion"
	 * @return una lista s que para cada moneda de tipo i devuelve el número de monedas de ese tipo utilizadas s[i] 
	 */
	public int [] reconstruirSol() {
		if (opcion == null) {
			throw new RuntimeException("Se debe resolver el problema primero");
		}
		
		int []s = new int[d.length]; //Inicialmente 0 monedas de cada tipo
		
		//***Completar la implementación****
		int i = d.length-1;
		int j = M;
		
		while(i>0 || j>0) {	// mientras me queden monedas disponibles o dinero por pagar
			// -Leo opcion- 
			// -Construyo la solucion-
			int numMonedas = opcion[i][j];	// escojo la opcion optima
			s[i] = numMonedas;	// mi opcion optima de monedas
			j = j - numMonedas*d[i]; // lo que me queda por pagar
			i--;	// me voy al siguiente tipo de moneda
		}
		// -Añado lo que falte-
		// Falta el caso en el que i=0, al que nunca se llega
		s[0] = opcion[0][j];
		return s;
	}

	
	public void mostrarDatos() {
		System.out.println("Número Mínimo de monedas");
		for (int i = 0; i< tabla.length; i++) {
			for (int j = 0; j< tabla[i].length; j++) {
				System.out.print(tabla[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("Opciones");
		for (int i = 0; i< opcion.length; i++) {
			for (int j = 0; j< opcion[i].length; j++) {
				System.out.print(opcion[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
