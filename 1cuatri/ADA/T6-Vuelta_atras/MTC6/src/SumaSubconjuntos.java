import java.util.ArrayList;
import java.util.List;
//OPTIMIZACIÓN -> encontrar la mejor solución

// Dado un conjunto S = {s1,...,sn} de números naturales y un número d, encontrar un subconjunto S tal que:
// 		- La suma de sus elementos no supera d
// 		- No hay otro subconjunto cuya suma no supere d que tenga una suma mayor que él

public class SumaSubconjuntos {

    public static List<Integer> resolverSumaSubconjuntos(int[] S, int d){
        List<Integer> sActual = new ArrayList<>();
        List<Integer> sMejor = new ArrayList<>();
        int suma_sActual = 0;

        return resolverSumaSubconjuntos(S, d, 0, sActual, suma_sActual, sMejor);
    }

    private static List<Integer> resolverSumaSubconjuntos(int[] S, int d, int indice, List<Integer> sActual, int suma_sActual, List<Integer> sMejor){
    	// si la sumaActual > sumaMejor; actualizamos el mejor
    	if (suma_sActual <= d && suma_sActual > sumaElemLista(sMejor)){
    		sMejor.clear();
    		sMejor.addAll(sActual);
        }

    	
        for (int i = indice; i < S.length; i++){
        	// si la suma de un nuevo elemento sigue siendo menor a d
            if (suma_sActual + S[i] <= d){
            	// añado a la posibilidad
            	sActual.add(S[i]);
            	suma_sActual += S[i];
            	
            	// y continuo mirando el resto de posibilidades
            	resolverSumaSubconjuntos(S, d, i + 1, sActual, suma_sActual, sMejor);
            	
            	// después, para mirarlas todas, vuelvo atrás en cada paso, explorando así todo el árbol
            	sActual.remove(sActual.size() - 1);
            	suma_sActual -= S[i];
            }
        }
        
        return sMejor;
    }

    private static int sumaElemLista(List<Integer> lista){
        int suma = 0;
        
        for (int num : lista){
            suma += num;
        }
        
        return suma;
    }

    public static void main(String[] args){
        int[] S = {3, 5, 6, 7};
        int d = 15;

        List<Integer> solucion = resolverSumaSubconjuntos(S, d);
        System.out.println("Subconjunto con suma menor o igual a " + d + ": " + solucion);
    }
}
