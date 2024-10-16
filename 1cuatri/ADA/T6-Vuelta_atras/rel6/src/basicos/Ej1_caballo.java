package basicos;

// SATISFACCIÓN -> encontrar solo 1 solución o determinar que no existe

//********************************************************************
//EJERCICIO 1 - CABALLO
//********************************************************************
//- ESTRUCTURA DE LA SOLUCIÓN: matriz M de dimensión nxn, ∀0<=i,j<=n: m[i][j]€ℕ. 
//Estado inicial: ∀0<=i,j<=n:(i,j)=(xi,ji) => m[i][j]=1
//∀0<=i,j<=n:(i,j)!=(xi,ji) => m[i][j]=0
//
//- POLÍTICA DE RAMIFICACIÓN: si (xi,ji), la posición actual del caballo, las posibles posiciones siguientes son las que se muestran en estos arrays, que indican los 8 posibles movimientos siguientes, siendo el movimiento número 'índice'; el desplazamiento en el eje x el contenido de 'índice' en el array dx y el desplazamiento en el eje y el contenido de 'índice' en el array dy:
//dx=[-2,-2,2,2,1,-1,1,-1]
//dy=[-1,1,-1,1,2,2,-2,-2]
//
//- ¿VALIDEZ?:
//	# Están dentro del tablero
//	# Que aún no se haya pasado por ahí
//
//- FUNCIÓN DE TERMINACIÓN: no quedan posiciones por las que pasar; 
//la última posición que hemos marcado debe ser marcada con m[i][j] (para ahorrarnos iterar para determinar si están todas las celdas ocupadas o no)
//
//- ENTRADA: 	n, lado del tablero.
//		(xi,yi), posición de inicio

public class Ej1_caballo {
	
	public boolean caballo(int[][] m, int x, int y) {
		boolean haySol = false;
		int n=m.length;
		
		if(m[x][y] == Math.pow(n, 2)) {
			haySol=true;
		}
		else {
			int mov = 0;
			while(!haySol && mov <=7) {
				int x_sig = sigPosX(x,mov);	// como no hay tuplas predefinidas en Java, lo hago por separado
				int y_sig = sigPosY(y,mov);
				if(valida(x_sig,y_sig,m)) {
					m[x_sig][y_sig] = m[x][y]+1;	// para ir marcando hasta n
					haySol = caballo(m, x_sig, y_sig);
					if(!haySol) {	// Vuelta atrás
						m[x_sig][y_sig] = 0;
					}
				}
				mov++;
			}
		}
		return haySol;
	}
	
	private int sigPosX(int x, int mov) {
		int[] dx = {-2,-2,2,2,1,-1,1,-1};
		
		return x+dx[mov];
	}
	
	private int sigPosY(int y, int mov) {
		int[] dy = {-1,1,-1,1,2,2,-2,-2};
		
		return y+dy[mov];
	}
	
	private boolean valida(int x, int y, int[][] m) {
		int n = m.length;
		return x>=0 && y>=0 && x<n && y<n && m[x][y]==0;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
