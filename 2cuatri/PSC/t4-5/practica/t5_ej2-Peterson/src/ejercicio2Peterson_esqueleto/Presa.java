package ejercicio2Peterson_esqueleto;

public class Presa extends Thread {
	private static int MAX_PRESA;
	private int id;
	private Lago lago;

	public Presa(int id, Lago lago, int max){
		this.id = id;
		this.lago = lago;
		MAX_PRESA = max;
	}

	@Override
	public void run() {
		for(int i=0; i<1000; i++){
			lago.decrementa(id, i);
		}
	}
}
