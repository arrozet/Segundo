package agua;
import java.util.concurrent.locks.*;

public class GestorAgua {
	Lock l = new ReentrantLock(true);

	Condition entraHidrogeno = l.newCondition();
	int hidrogeno = 0;
	boolean pasaHidrogeno = true;
	Condition entraOxigeno = l.newCondition();
	int oxigeno = 0;
	boolean pasaOxigeno = true;
	Condition creoMolecula = l.newCondition();
	boolean elementosListos=false;

	public void hListo(int id) throws InterruptedException {
		try{
			l.lock();
			while(!pasaHidrogeno){
				entraHidrogeno.await();
			}
			hidrogeno++;
			System.out.println("Ha entrado el hidrógeno " + id + ". Hay " + hidrogeno);

			if(hidrogeno==2){
				pasaHidrogeno=false;
				if(oxigeno==1){
					System.out.println("---LA MOLÉCULA HA SIDO CREADA---");
					elementosListos=true;
					creoMolecula.signalAll();
				}
			}

			// Aquí como mucho entrarán 2 hidrógenos, porque pasaHidrogeno se pone a false cuando cruzan 2 de ellos
			while(!elementosListos){
				creoMolecula.await();
			}
			hidrogeno--;
			System.out.println("	Ha salido el hidrógeno " + id + ". Hay " + hidrogeno);

			if(oxigeno==0 && hidrogeno==0){
				elementosListos=false;
				pasaHidrogeno=true;
				pasaOxigeno=true;
				entraHidrogeno.signalAll();
				entraOxigeno.signalAll();
				System.out.println("\n");
			}
		}finally {
			l.unlock();
		}

	}
	
	public void oListo(int id) throws InterruptedException {
		try{
			l.lock();
			while(!pasaOxigeno){
				entraOxigeno.await();
			}
			oxigeno++;
			System.out.println("Ha entrado el oxígeno " + id + ". Hay " + oxigeno);

			if(oxigeno == 1){
				pasaOxigeno=false;
				if(hidrogeno==2){
					System.out.println("---LA MOLÉCULA HA SIDO CREADA---");
					elementosListos=true;
					creoMolecula.signalAll();
				}
			}

			// Aquí como mucho entrará 1 oxígeno, porque pasaOxigeno se pone a false cuando cruza 1 de ellos
			while(!elementosListos){
				creoMolecula.await();
			}
			oxigeno--;
			System.out.println("	Ha salido el oxígeno " + id + ". Hay " + oxigeno);
			if(oxigeno==0 && hidrogeno==0){
				elementosListos=false;
				pasaHidrogeno=true;
				pasaOxigeno=true;
				entraHidrogeno.signalAll();
				entraOxigeno.signalAll();
				System.out.println("\n");
			}
		}finally {
			l.unlock();
		}
	}
}
