package orden;

public class Exponencial2Rec extends Metodo{
	public Exponencial2Rec() {
		super(Metodo.Orden.EXP2, Metodo.Orden.EXP2);
	}
	@Override
	public int codigo(int n) {
		if(n==0) {
			return 1;
		}
		else {
			return codigo(n-1) + codigo(n-1);
		}
	}
	
}
