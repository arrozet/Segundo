package orden;

public class OrdenConstanteIter extends Metodo{
	public OrdenConstanteIter() {
		super(Metodo.Orden.CTE, Metodo.Orden.CTE);
	}
	
	@Override
	public int codigo(int n) {
		return 0;	// O(1) -> Siempre retorna 0
	}
	
}
