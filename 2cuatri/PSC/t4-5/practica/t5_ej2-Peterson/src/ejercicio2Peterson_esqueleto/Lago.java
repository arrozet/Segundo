package ejercicio2Peterson_esqueleto;

//Recurso compartido
public class Lago {
	private int nivel;
	private static Peterson_rios mutex_rios = new Peterson_rios();
	private static Peterson_rios mutex_presas = new Peterson_rios();
	private static Peterson_rios mutex_rios_presas = new Peterson_rios();
	
	public Lago(){
		nivel = 0;
		//peterson = new Peterson_rios();
	}
	
	public int get(){
		return nivel;
	}
	
	//La capacidad del lago se supone indefinida
	public void incrementa(int id, int iter){
		mutex_rios.entrar(id);			// prot entrada -> solo 1 rio a la vez
		mutex_rios_presas.entrar(0);	// solo 1 rio o una presa a la vez
		nivel++;
		System.out.println(iter+":Rio " + id + " ha incrementado el nivel: "+nivel);
		mutex_rios_presas.salir(0);
		mutex_rios.salir(id);		// prot salida
	}
	
	
	public void decrementa(int id, int iter){
		mutex_presas.entrar(id);		// prot entrada -> solo 1 lago a la vez
		// lo pongo antes para que mientras espero no haya otra presa quitando agua y que el nivel acabe siendo !=0
		while(nivel==0){
			Thread.yield();
		}
		mutex_rios_presas.entrar(1);	// solo 1 rio o una presa a la vez
		nivel--;
		System.out.println(iter+":Presa " + id + " ha decrementado el nivel: " +nivel);
		mutex_rios_presas.salir(1);
		mutex_presas.salir(id);		// prot salida
	}
}
