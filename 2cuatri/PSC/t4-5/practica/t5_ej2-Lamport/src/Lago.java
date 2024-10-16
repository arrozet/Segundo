//package ejercicio2Lamport_esqueleto;

public class Lago {
	private int nivel;
	private Lamport_rios mutex_lamport;
	public Lago(){
		nivel = 0;
		mutex_lamport = new Lamport_rios(4);
	}
	
	public void incrementa(int id, int iter) {
		mutex_lamport.cogeTurno(id);
		mutex_lamport.esperoTurno(id);
		++nivel;
		System.out.println(iter+":Rio " + id + " ha incrementado el nivel: "+nivel);
		mutex_lamport.sale(id);
	}
	
	public void decrementa(int id, int iter) {
		/* La condici?n de sincronizaci?n la implementamos de forma distinta.
		 * 
		 * - La presa coge turno y espera a su turno
		 * - Una vez que tiene el turno antes de acceder a la SC comprueba nivel
		 * - Si nivel = 0, aunque tenga el turno no puede usarlo, lo suelta
		 * - Vuelve a coger otro turno y a esperar que le toque nuevamente
		 * 
		 * - Cuando le dan el turno y el nivel es > 0 ya puede decrementar el nivel
		 * - Como cuando ejecuta el if, la hebra tiene el turno, ninguna otra hebra puede modificar
		 *   el valor de nivel. No podr? modificarse el valor de nivel hasta que la hebra
		 *   actual salga de la SC, llamando a mutex.sale(id);
		 */
		mutex_lamport.cogeTurno(id);
		mutex_lamport.esperoTurno(id);
		while(nivel==0){
			mutex_lamport.sale(id);
			mutex_lamport.cogeTurno(id);
			mutex_lamport.esperoTurno(id);
		}
		--nivel;
		System.out.println(iter+":Presa " + id + " ha decrementado el nivel: " +nivel);
		mutex_lamport.sale(id);
	}
	
	public int get() {
		return nivel;
	}
}

