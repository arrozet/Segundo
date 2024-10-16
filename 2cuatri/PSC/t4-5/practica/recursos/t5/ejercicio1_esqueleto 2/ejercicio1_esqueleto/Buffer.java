package ejercicio1_esqueleto;

public class Buffer {
	private int[] elem; //array de elementos
	private int nelem;  //n�mero de elementos en el buffer
	private int p;      //posici�n donde guardar pr�ximo elemento
	private int c;      //posici�n donde est� el siguiente elemento a consumir
	
	public Buffer(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		
		elem = new int[n];
		p = 0;
		c = 0;
		nelem = 0;
	}
	
	public void insertar(int x) {	
		elem[p] = x;
		System.out.println("Productor: "+x);
		p = (p+1) % elem.length; //incremento circular
		++nelem;
	}
	
	public int extraer() {
		int x = elem[c];
		System.out.println("	Consumidor: "+x);
		c = (c+1) % elem.length; //incremento circular
		--nelem;
		return x;
	}
}
