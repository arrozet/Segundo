package orden;

public class OrdenCuadradoRec extends Metodo{
	public OrdenCuadradoRec() {
		super(Metodo.Orden.N2, Metodo.Orden.N2);
	}
	@Override
	public int codigo(int n) {
		if(n==0) {
			return 1;
		}
		else {
			return n* codigo(n-1);
		}
	}
	
}
