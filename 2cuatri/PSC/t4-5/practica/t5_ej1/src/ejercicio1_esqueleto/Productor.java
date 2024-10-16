package ejercicio1_esqueleto;

public class Productor extends Thread{
	//Atributos
	private int numIter;
	private Buffer buffer;
	
	//Constructor
	public Productor(int numIter, Buffer b) {
		this.numIter = numIter;
		this.buffer = b;
	}
	
	
	//Métodos
	public void run() {
		for(int i = 0; i<numIter; i++) {
			//System.out.println("Productor: " + i);
			buffer.insertar(i);
		}
	}
	
	
}
