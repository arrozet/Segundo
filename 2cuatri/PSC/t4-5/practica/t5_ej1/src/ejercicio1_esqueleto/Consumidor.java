package ejercicio1_esqueleto;

public class Consumidor extends Thread {
	//Atributos
	private int numIter;
	private Buffer buffer;
		
	//Constructor
	public Consumidor(int numIter, Buffer b) {
		this.numIter = numIter;
		this.buffer = b;
	}
		
		
	//Métodos
	public void run() {
		for(int i = 0; i<numIter; i++) {
			//System.out.println(i);
			buffer.extraer();
			//System.out.println("Consumidor: " + extraido);
		}
	}
}

