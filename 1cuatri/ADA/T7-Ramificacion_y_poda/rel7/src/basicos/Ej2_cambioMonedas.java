//package basicos;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.PriorityQueue;
//
//public class Ej2_cambioMonedas {
//
////	********************************************************************
////	EJERCICIO 2 - CAMBIO DE MONEDAS
////	********************************************************************
////	- ENTRADA: 
////		# d=[d_0,...,d_k-1], tipos de monedas
////		# n=[n_0,...,n_k-1], cantidad de monedas de cada tipo
////		# M, total a pagar
////
////	- ESTRUCTURA DE LA SOLUCIÓN: lista S=[s_0,...,s_i], i<=k-1; donde s_i es la cantidad de monedas 
////	utilizadas de tipo i. 
////	Estado inicial: S=[]
////
////	- RESTRICCIONES EXPLÍCITAS (RAMAS POSIBLES EN CADA NIVEL): en cada paso de construcción se decidirá 
////	cuántas monedas de tipo i se utilizarán. 
////	Estará limitado por el número de monedas máximo de cada tipo (0<=s_i<=n_i)
////
////	- RESTRICCIONES IMPLÍCITAS (VALIDEZ): 
////		# Al añadir s_i monedas, no debemos pagar más de M.
////		# Si i=k (última decisión), entonces no será correcta si al final hemos pagado exactamente M
////
////	- FUNCIÓN DE TERMINACIÓN: si S.length = k, entonces la solución es completa ya que hemos asignado 
////	cantidades a todas las monedas.
////
////	- FUNCIÓN OBJETIVO: total de monedas de la solución (minimizar) -> sum(i=1,k,s_i).
////
////	- FUNCIÓN DE COTA: f(S) = g(s) + h(S)
////		# Calidad de lo que ya llevamos: g(S)=sum(j=1,i,s_j)
//	
////		# La estimación optimista considerará que, de lo que no hemos construido aún, no 
////		hay límite de monedas
//	
////		# Se va a intentar pagar la cantidad restante con la moneda de más valor de las no utilizadas, 
////		que será la siguiente, ya que la lista de monedas está ordenada por valor de mayor a menor. 
////		h(S)=restante / d_i+1
//	
//	public static List<Integer> MonedasRyP(int []d, int[] n, int M, List<Integer> p){
//		int k = d.length; 
//		ColaPrioridad<List<Integer>> h = new PriorityQueue<>();
//		h.add(p);
//		int calMejor = Integer.MAX_VALUE; 
//		List<Integer> mejor = null;
//		while (!h.isEmpty()){
//			int f=0;	// esto no se si es asi
//			List<Integer> act = h.peek();
//			if (act.size() == k){
//				f = cota(act,d,M);
//			if ( f < calMejor){
//				calMejor = f; mejor = act;
//				h.actualizar(calMejor);
//			}
//		} else {
//			int etapa = act.size();int opcion = n[etapa];
//			while(opcion >= 0){
//				int valor = valor(act,d) + opcion * d[etapa];
//				if ( etapa == k && valor == M || etapa < k && valor <=M){
//					List<Integer> hijo = new ArrayList<Integer>(act);
//					hijo.add(opcion);
//				if (cota(hijo,d,M)< calMejor) {
//					h.add(hijo);
//					}
//				}
//			}	
//			opcion--;
//			}
//		} return mejor;
//	}
//	
//	private static int valor(List<Integer> sol, int [] d){
//		int v = 0;
//		for (int i = 0; i < sol.size(); i++){
//			v += sol.get(i) * d[i];
//		}
//		return v;
//	}
//	
//	private static int cota (List<Integer> sol, int [] d, int M){
//		int g = 0; int h = 0;
//		// conocido
//		for (Integer cantidad : sol){
//			g += cantidad;
//		}
//		int etapa = sol.size();
//		// estimacion
//		if (etapa < d.length){ //solución no completa
//			h = (int) Math.ceil((M - valor(sol, d))/ d[etapa]);
//		}
//		return g + h;
//	}
//	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
