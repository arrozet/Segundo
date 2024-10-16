import p5.*;

import java.util.Set;

public class MainCobertura {
	
	public static void imprimirSolucion(int[][] a) {
		Grafo g = new Grafo(a);
		Cobertura p= new Cobertura(g);
		Set<Integer> sol = p.getConjuntoCobertura();
		System.out.println(sol);
	}

	public static void main(String[] args) {
		// test 1
		int[][] test1 = 
					{
					{0, 0,	0,	1,	1},
					{0,	0,	1,	1,	0},
					{0,	1,	0,	1,	0},
					{1,	1,	1,	0,	0},
					{1,	0,	0,	0,	0}
					};	
		imprimirSolucion(test1);
		
		// test 3
		int[][] test3 = 
					{
					{0,	1,	1,	1,	1},
					{1,	0,	0,	0,	0},
					{1,	0,	0,	0,	1},
					{1,	0,	0,	0,	0},
					{1,	0,	1,	0,	0}
					};	
		imprimirSolucion(test3);
		
		// test 4
		int[][] test4 = 
					{
					 {0,	1,	1,	1,	1,	1,	0,	1,	1,	1},
					 {1,	0,	1,	0,	1,	1,	1,	1,	1,	1},
					 {1,	1,	0,	1,	0,	1,	0,	0,	1,	1},
					 {1,	0,	1,	0,	1,	1,	0,	1,	0,	1},
					 {1,	1,	0,	1,	0,	1,	1,	1,	1,	1},
					 {1,	1,	1,	1,	1,	0,	0,	1,	1,	0},
					 {0,	1,	0,	0,	1,	0,	0,	0,	1,	1},
					 {1,	1,	0,	1,	1,	1,	0,	0,	1,	1},
					 {1,	1,	1,	0,	1,	1,	1,	1,	0,	0},
					 {1,	1,	1,	1,	1,	0,	1,	1,	0,	0}
					};	
		imprimirSolucion(test4);
		
		// test 5
		int[][] test5 = 
					{
					 {0,	1,	1,	0,	0,	1,	0,	0,	1,	1},
					 {1,	0,	0,	1,	1,	1,	1,	1,	1,	1},
					 {1,	0,	0,	1,	1,	1,	1,	1,	1,	0},
					 {0,	1,	1,	0,	1,	1,	1,	1,	0,	0},
					 {0,	1,	1,	1,	0,	1,	1,	0,	1,	0},
					 {1,	1,	1,	1,	1,	0,	0,	1,	1,	1},
					 {0,	1,	1,	1,	1,	0,	0,	1,	0,	0},
					 {0,	1,	1,	1,	0,	1,	1,	0,	1,	1},
					 {1,	1,	1,	0,	1,	1,	0,	1,	0,	0},
					 {1,	1,	0,	0,	0,	1,	0,	1,	0,	0}
					};
		imprimirSolucion(test5);
		
		
	}
	

}
