package orden;

public class OrdenLogRec extends Metodo{
	public OrdenLogRec() {
		super(Metodo.Orden.LOGN, Metodo.Orden.LOGN);
	}
	@Override
	public int codigo(int n) {
		if(n==0) {
			return 1;
		}
		else {
			return codigo(n/2);
		}
	}
	
}
