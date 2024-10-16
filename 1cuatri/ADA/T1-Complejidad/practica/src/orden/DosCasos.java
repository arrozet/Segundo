package orden;

public class DosCasos extends Metodo{
	public DosCasos() {
		super(Metodo.Orden.CTE, Metodo.Orden.N);
	}
// EJERCICIO 1
	@Override
	public int codigo(int n) {
		int resultado = 0;
		if(n==0) {		// mejor caso
			resultado = 1;
		}
		else {	// peor caso
			for(int i=0; i<n; i++) {
				resultado += i;
			}
		}
		return resultado;
	}
	
}
