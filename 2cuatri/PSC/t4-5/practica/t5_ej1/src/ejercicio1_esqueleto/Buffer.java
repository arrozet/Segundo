package ejercicio1_esqueleto;

public class Buffer {
	private Peterson mutex;
	private int[] elem; //array de elementos
	private int nelem;  //n�mero de elementos en el buffer
	private int p;      //posici�n donde guardar pr�ximo elemento
	private int c;      //posici�n donde est� el siguiente elemento a consumir
	
	public Buffer(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		
		mutex = new Peterson();
		elem = new int[n];
		p = 0;
		c = 0;
		nelem = 0;
	}
	
	public void insertar(int x) {
		
		while (nelem==elem.length) {
			System.out.println("Buffer lleno: esperar");
			Thread.yield();
		}
		mutex.entrada_productor();

		elem[p] = x;
		System.out.println("Productor: "+x);
		p = (p+1) % elem.length; //incremento circular
		++nelem;
		
		mutex.salida_productor();
	}
	
	public int extraer() {
		
		while (nelem==0) {
			System.out.println("Buffer vacio: esperar");
			Thread.yield();
		}
		mutex.entrada_consumidor();
		
		int x = elem[c];
		System.out.println("	Consumidor: "+x);
		c = (c+1) % elem.length; //incremento circular
		--nelem;
		
		mutex.salida_consumidor();
		return x;
	}
}
