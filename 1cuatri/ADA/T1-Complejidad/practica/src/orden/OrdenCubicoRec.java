package orden;

public class OrdenCubicoRec extends Metodo{
	public OrdenCubicoRec() {
		super(Metodo.Orden.N3, Metodo.Orden.N3);
	}
	
	@Override
	public int codigo(int n) {
		if(n==0) {
			return 1;
		}
		else {
			return n*n* codigo(n-1);
		}
	}
	
}
