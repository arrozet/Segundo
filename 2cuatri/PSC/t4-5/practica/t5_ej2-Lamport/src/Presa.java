//package ejercicio2Lamport_esqueleto;

public class Presa extends Thread {
	private static int MAX_PRESA;

	int id;
	Lago nivel;
	
	public Presa(Lago n,int i, int max) {
		id = i;
		MAX_PRESA = max;
		nivel = n;
	}
	
	public void run() {
		try {
			int i = 0;
			while (i < MAX_PRESA) {
				nivel.decrementa(id,i); //Cuando tiene turno decrementa
				++i;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
