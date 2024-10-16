package basicos;
import java.util.ArrayList;
import java.util.List;

public class Scarpa_sumaElems {
    
//        public static List<Integer> sumar(List<Integer> A, int x){
    	public static List<List<Integer>> sumar(List<Integer> A, int x){
    		// List<Integer> sol = new ArrayList<>();
    		List<List<Integer>> sol = new ArrayList<>();
    		List<Integer> posible = new ArrayList<>();
            int i = 0;
//            return (sumaElems(sol, A, x) ? sol : null);
            return sumaElems(sol, A, x, posible);
        }
    	
//        private static boolean sumaElems(List<Integer> sol, List<Integer> A, int x) {
        private static List<List<Integer>> sumaElems(List<List<Integer>> sol, 
        		List<Integer> A, int x, List<Integer> posible) {
        	
                if(suma(posible) == x) {
                	sol.add(new ArrayList<>(posible));
                	// IMPORTANTE. Si no pones new Arraylist<>(lista_a_añadir) NO VA
                	// pq se metería en sol un POINTER a la lista posible, y saldría repetido
                	// su valor final que sería vacío.
                	// Haciendo new ArrayList<>(posible) te aseguras de meter una COPIA DEL OBJETO
                	// que es lo que quieres
                }
                
//                else{
                    List<Integer> candidatos = A;
                    int k = 0;
//                    boolean haySol = false;
                    while(k < candidatos.size()){
                        if(suma(posible)+candidatos.get(k) <= x && 
                        		(posible.isEmpty() || candidatos.get(k)>=posible.get(posible.size()-1))){	
                        	// para solo meter los elementos en orden ascendente (así evito permutaciones)
                            posible.add(candidatos.get(k));
                            sumaElems(sol, A, x,posible);
                            posible.remove(posible.size()-1);
                        }
                        k++;

                    } 
//                }
                return sol;

        }

        private static int suma(List<Integer> sol) {
            int suma = 0;
            for(int numero : sol){
                suma += numero;
            }
            return suma;
        }

        public static void main(String[] args) {
            int x = 4;
            List<Integer> A = new ArrayList<>();
            A.add(1);
            A.add(2);
            A.add(3);

//            List<Integer> asignacion = sumar(A,x);
            System.out.println(sumar(A,x));
        }
    
}
