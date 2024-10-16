package orden;

public class OrdenLogIter extends Metodo{
	public OrdenLogIter() {
		super(Metodo.Orden.LOGN, Metodo.Orden.LOGN);
	}
	// EJERCICIO 2
	@Override
	public int codigo(int n) {
		int res = 0;
		while(n>1) {
			res++;
			n/=2;
		}
		return res;
	}
	
}
