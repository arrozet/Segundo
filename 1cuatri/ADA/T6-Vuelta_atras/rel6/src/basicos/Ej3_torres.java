package basicos;

import java.util.ArrayList;
import java.util.List;

//********************************************************************
//EJERCICIO 3 - TORRES
//********************************************************************
//- ESTRUCTURA DE LA SOLUCIÓN: arraylist t con las posiciones de las torres T. El índice representará la fila y el contenido, la columna.
//Estado inicial: t=[]
//
//- POLÍTICA DE RAMIFICACIÓN: una solución parcial será prometedora si hay alguna posición en la que pueda situar una torre válida. 
// Si es así, se explorarán las alternativas. Al incluir una nueva torre, llenaremos de pseudo-obstáculos la fila y columna que cubran, 
// para evitar poner ahí nuevas torres (pondremos a true las casillas correspondientes).
//
//De lo contrario, volveremos a atrás.
//
//- ¿VALIDEZ?: 
//	# Las torres no deben atacarse: en una misma fila y columna, no puede haber 2 torres si no hay obstáculos entre ellas
//	# Dos torres no pueden estar en la misma casilla
//
//- FUNCIÓN DE TERMINACIÓN: cuando todas las casillas estén cubiertas por torres válidas, es decir, la matriz esté llena de true.
//
//- ENTRADA: 	tablero L de tamaño nxn, con lij un booleano que marca si hay o no obstáculo

public class Ej3_torres {
	
	public List<Integer> torres(boolean[][] L){
		// El índice representará la fila y el contenido, la columna.
		// PROBLEMA: tengo que rellenar de algo que no sea 0 las filas que esten llenas de obstaculos
		List<Integer> sol = new ArrayList<>();
		
		boolean haySol = torres(L, sol);
		
		return haySol?sol:null;
	}
	
	public static boolean torres(boolean[][] L, List<Integer> sol) {
		boolean haySol = false;
		
		// cuando todo esté lleno de obstáculos, es decir, estén todas las casillas cubiertas
		if(llenoObstaculos(L)) {
			haySol = true;
		}
		else {	
			int row=0, col = 0;
			while(!haySol && row<L.length) {
				while(!haySol && col<L.length) {
					if(valida(L,row,col)) {
						sol.set(row, col);
						// para evitar tener que estar actualizando y desactualizando la matriz (gran coste temporal), 
						// crearé una temporal que guarde el estado sin actualizar de la matriz
						
						// PROBLEMA: esto creo que no rellena la matriz, sino que crea un pointer nuevo a la misma direccion de memoria
						boolean[][] L_act = L;	
						actualizarObstaculos(L_act, row, col);
						haySol = torres(L_act, sol);
						if(!haySol) {
							sol.remove(row);	// remove indice row
						}
					}
					
					col++;
				}
				row++;
			}
			
		}
		
		return haySol;
	}
	
	// PROBLEMA: esta también se para si la casilla en cuestión está cubierta por una torre, aunque no sea un obstáculo
	// Además, tal y como está implementado tampoco se mira arriba pq se supone que ya está cubierto
	
	// POSIBLE SOLUCIÓN: en lugar de una matriz de booleanos (condición del enunciado), que sea una matriz de int
	// y que 0 represente vacio, 1 muro, 2 torre y 3 cubierto por torre (o algo del estilo).
	
	// ESTO LO ESCRIBÍ EL JUEVES
	// SEGÚN LAZCANO ESTE ES EL BUEN ENFOQUE. En el primer boolean torres() tienes que pasar de tu matriz
	// de booleanos a una de int's. Asocias un valor a cada cosa (-1 obstaculo, 0 vacio, 1 torre, 
	// sumar 1 al valor de la casilla cubierta por la torre que fuese...)
	
	private static void actualizarObstaculos(boolean[][] L, int row, int col) {
		int aux_row=row;
		int aux_col=col;
		boolean stop = false;
		
		// actualizar fila -> mirar problema
		while(!stop && aux_row<L.length) {
			if(L[aux_row][col] == true) {
				stop = true;
			}
			else {
				L[aux_row][col] = true;
			}
			aux_row++;
		}
		
		// actualizar columna -> mirar problema
		stop = false;
		while(!stop && aux_col<L.length) {
			if(L[row][aux_col] == true) {
				stop = true;
			}
			else {
				L[row][aux_col] = true;
			}
			aux_col++;
		}
	}
	
	//# Las torres no deben atacarse: en una misma fila y columna, no puede haber 2 torres si no hay obstáculos entre ellas
	//# Dos torres no pueden estar en la misma casilla
	
	// resumiendo, que la casilla que estás mirando sea false (ya que actualizamos los pseudo-obstáculos en cada paso)
	private static boolean valida(boolean[][] L, int row, int col) {
		return L[row][col] == false;
	}
	
	private static boolean llenoObstaculos(boolean[][] L) {
		boolean lleno = true;
		int fila=0, columna=0;
		
		// si hay algún false en la matriz, para y devuelve false
		while(lleno && fila<L.length) {
			while(lleno && columna<L.length) {
				if(L[fila][columna] == false) {
					lleno = false;
				}
				columna++;
			}
			fila++;
		}
		
		return lleno;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
