package orden;

public class OrdenCubicoIter extends Metodo{
	public OrdenCubicoIter() {
		super(Metodo.Orden.N3, Metodo.Orden.N3);
	}
	
	// EJERCICIO 2
	@Override
	public int codigo(int n) {
		int res = 0;
		for(int i=0; i<n; i++) {
			res ++;
			for(int j=0; j<n; j++) {
				res ++;
				for(int k=0; k<n; k++) {
					res++;
				}
			}
		}
		return res;
	}
	
}
