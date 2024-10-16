package orden;

public class OrdenLinealRec extends Metodo{
	public OrdenLinealRec() {
		super(Metodo.Orden.N, Metodo.Orden.N);
	}
	
	@Override
	public int codigo(int n) {
		if(n==0) {
			return 1;
		}
		else {
			return 1 + codigo(n-1);
		}
	}
	
}
