package orden;

public class OrdenNLogRec extends Metodo{
	public OrdenNLogRec() {
		super(Metodo.Orden.NLOGN, Metodo.Orden.NLOGN);
	}
	
	// EJERCICIO 2
	@Override
	public int codigo(int n) {
		if(n==0) {
			return 1;
		}
		else {
			int res = 0;
			for(int i =0; i<n; i++) {
				res+=codigo(n/2);
			}
			return res;
		}
	}
	
}
