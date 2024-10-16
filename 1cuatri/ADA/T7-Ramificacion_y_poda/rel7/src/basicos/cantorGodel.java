package basicos;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class cantorGodel {
	
	public static int cantorizacion(List<Integer> v) {
		// Simplemente he plasmado la fórmula del libro
		if (v.size() == 1) {
            return v.get(0);
        } else {
            int x1 = v.get(0);
            List<Integer> subVector = v.subList(1, v.size());
            return cantorizacion2(x1, cantorizacion(subVector));
        }
    }

    public static int cantorizacion2(int x, int y) {
        return (x + y) * (x + y + 1) / 2 + y;
    }
    
    public static int godelizacion(List<Integer> v) {
    	// Simplemente he plasmado la fórmula del libro
    	if(v.size()==0) {
    		return 0;
    	}
    	else {
    		return cantorizacion2(v.size()-1, cantorizacion(v)) + 1;
    	}
    }

    public static void main(String[] args) {
        // CANTORIZACIÓN
    	// **********************************************
    	// cambia este array para cantorizar el vector
    	Integer[] cantor = new Integer[] {4,2,7};
    	// **********************************************
        List<Integer> vectorC = new ArrayList<>(Arrays.asList(cantor));
       
        int resultadoC = cantorizacion(vectorC);
        System.out.println("Resultado de la cantorización del vector\t" + vectorC + ":\t" + resultadoC);
        
        // GODELIZACIÓN
     	// **********************************************
        // cambia este array para godelizar el vector
        Integer[] godel = new Integer[] {14,0};
        // **********************************************
        List<Integer> vectorG = new ArrayList<>(Arrays.asList(godel));
       
        int resultadoG = godelizacion(vectorG);
        System.out.println("Resultado de la godelización del vector  \t" + vectorG + ":\t" + resultadoG);
    }

}
