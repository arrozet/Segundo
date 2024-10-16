package ejercicio2Peterson_esqueleto;

public class Rio extends Thread {
	private static int MAX_RIO;
	private int id;

	private Lago lago;

	public Rio(int id, Lago lago, int max){
		this.id = id;
		this.lago = lago;
		MAX_RIO = max;
	}

	@Override
	public void run() {
		for(int i=0; i<1000; i++){
			lago.incrementa(id, i);
		}
	}
}