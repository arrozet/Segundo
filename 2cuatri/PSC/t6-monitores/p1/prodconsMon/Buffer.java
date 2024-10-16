
package prodconsMon;

import java.util.Arrays;

public class Buffer {
	//Buffer
	private int N = 10; //Tamaño del buffer
	private int[] buffer = new int[N]; //Buffer
	private int[] numCons = new int[N];//Contador del numero de consumidores que faltan por leer cada elemento del buffer

	private int nespacios = N;//Numero de espacios que hay en el buffer
	private int ncons; //Numero de consumidores del buffer
	private int[] nelems; //Numero de elementos en el buffer para cada consumidor
	private int[] c; //posicion a partir de la que consume cada consumidor
	private int p; //posicion a partir de la que guarda el productor
	
	//n - numero de consumidores
	public Buffer(int n){
		System.out.println("Tamanio del buffer " + N);
		System.out.println("Numero de consumidores " + n + "\n");

		ncons = n;
		p = 0;
		c = new int[n];
		nelems = new int[n];
	}
	
	//synchronized (ex. mutua) + wait/notify (cond. sincronizaci�n)
	public synchronized void almacenar(int elem) throws InterruptedException {
		//CS-Productor: espera mientras el buffer est� lleno
		while(nespacios==0){
			wait();
		}
		
		//actualiza todas las variables
		nespacios--;
		buffer[p] = elem;	// almaceno elem en el buffer
		System.out.println("Se almacena " + elem + " en el buffer -> " + Arrays.toString(buffer));
		numCons[p] = ncons;		// todos tienen que consumir el nuevo recurso
		p = (p+1)%N;		// incremento ciruclar de p
		for(int i=0; i< nelems.length; i++){
			nelems[i]++;	// a todos los consumidores le queda 1 elemento por consumir
		}

		notifyAll();
	}
	
	//id- identificador del consumidor
	public synchronized int extrae(int id) throws InterruptedException {
		//CS-Consumidor_id: espera mientras no tenga elementos que consumir
		while(nelems[id]==0){
			wait();
		}

		//actualiza todas las variables
		int pos_consumo = c[id];
		numCons[pos_consumo]--;		// queda 1 menos por consumir aqui
		nelems[id]--;
		System.out.println("	Consumidor " + id + " ha consumido " + buffer[pos_consumo] + ". Quedan " + numCons[pos_consumo] + " consumidores por consumir");

		// si todos los consumidores han consumido el valor
		if(numCons[c[id]]==0){	// acabo de consumir c[id]... ¿No queda nadie por consumir ese recurso?
			nespacios++;
			System.out.println("		Elemento " + buffer[pos_consumo] + " eliminado");
		}
		c[id] = (c[id]+1) % N;	// acabo de consumir ese recurso, paso a la siguiente (incremento ciruclar)

		notifyAll();

		return buffer[pos_consumo];
	}
}
