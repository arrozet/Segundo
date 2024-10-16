
public class RecorridoTablero {
	private int[][] tablero; 
	private int[][] solucion;
	private int fila; //Fila de la casilla de inicio
	private int col;  //Columna de la casilla de inicio
	private int n;   
	private int m;

	public RecorridoTablero(int[][] t, int fila, int col) {
		tablero = t;
		n = tablero.length;
		m = tablero[0].length;
		this.fila = fila;
		this.col = col;
		solucion = null;
	}

	public int resolverMemo() {	// top-down - final de tabla a principio de tabla
		// Creamos la tabla auxiliar
		solucion = new int[n][m]; // -1 representará que la celda está vacía.
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				solucion[i][j] = -1;
			}
		}
		// Rellenamos la tabla desde la casilla indicada
		return resuelve(fila, col);
	}

	private int resuelve(int i, int j) {
		if(i==0) {
			solucion[i][j] = tablero[i][j];
		}
		else if (i>0 && j==0) {	// solo puedo moverme arriba o arriba derecha
			solucion[i][j] = tablero[i][j] + Math.max(resuelve(i-1, j), resuelve(i-1, j+1));
		}
		else if (i>0 && j==m-1) { // solo puedo moverme arriba o arriba izquierda
			solucion[i][j] = tablero[i][j] + Math.max(resuelve(i-1, j), resuelve(i-1, j-1));
		}
		else {	// puedo moverme arriba, arriba izq o arriba der
			solucion[i][j] = tablero[i][j] + maximo3(resuelve(i-1, j), resuelve(i-1, j-1), resuelve(i-1, j+1));
		}

		
		return solucion[i][j];
	}

	private int maximo3(int a, int b, int c) {
		int res = a;
		if (b > res) {
			res = b;
		}
		if (c > res) {
			res = c;
		}
		return res;
	}

	public Recorrido reconstruirSol() {
		if (solucion == null) {
			throw new RuntimeException("Se debe resolver el problema primero");
		}
		Recorrido r = new Recorrido();
		//***Completar Implementación***
		int i=fila;
		int j=col;
		while(i>=0) {
			r.add(i, j);
			// para que no de IndexOutOfBounds, si es 0 que no mire tablas
			if(i>0) {
				// el siguiente valor de solucion se corresponde con nuevo_valor
				int nuevo_valor = solucion[i][j] - tablero[i][j];
				i--;
				
				// habrá que buscarlo, ¿no? ¡Vamos a ello!
				if(j-1>=0 && nuevo_valor==solucion[i][j-1]) {
					j=j-1;
				}
				else if(j+1<m && nuevo_valor==solucion[i][j+1]) {
					j=j+1;
				}
			}
			else {
				i--;
			}
			
		}
		// Mientras no llegue a caso base,
		// mira qué opción tomar, usando solucion
		// Se actualiza el recorrido 
		// Se salta a la casilla del subproblema que queda
		
		return r;
	}

	public void imprimeMatrizSolucion() {
		for (int i = 0; i < solucion.length; i++) {
			for (int j = 0; j < solucion[i].length; j++) {
				System.out.print(solucion[i][j] + " ");
			}
			System.out.println(" ");
		}
	}
	
}
