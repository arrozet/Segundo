package orden;

public class OrdenNLogIter extends Metodo{
	public OrdenNLogIter() {
		super(Metodo.Orden.NLOGN, Metodo.Orden.NLOGN);
	}
	
	// EJERCICIO 2
	@Override
	public int codigo(int n) {
		int res = 0;
		for(int i=0; i<n; i++) {
			res++;
			int temp = n;
			while(temp>1) {
				temp/=2;
				res++;
			}
		}
		
		return res;
	}
	
}
