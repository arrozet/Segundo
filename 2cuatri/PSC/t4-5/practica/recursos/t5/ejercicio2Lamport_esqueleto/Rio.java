package ejercicio2Lamport_esqueleto;

public class Rio extends Thread {
	private static int MAX_RIO;
	int id;
	Lago nivel;
	
	public Rio(Lago n, int i, int max) {
		id = i;
		MAX_RIO = max;
		nivel = n; //recurso compartido
	}
	
	public void run() {
		try {
			for (int i = 0; i < MAX_RIO; ++i) {
				nivel.incrementa(id,i); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}