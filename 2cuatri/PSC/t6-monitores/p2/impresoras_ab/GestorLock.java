package impresoras_ab;

import java.util.concurrent.locks.*;

/**
 * 
 * @author mmar
 * Soluci�n al problema del gestor de impresoras utilizando
 * condiciones. La condici�n colaGeneral es utilizada por todas las
 * hebras cuando piden una impresora y no hay del tipo que piden.
 *
 * Las colas colaA y colaB son utilizadas en exclusiva por las hebras de tipo
 * A y B, respectivamente.
 * 
 * 
 * 
 */
public class GestorLock implements Gestor {
	
	private int numImpA,numImpB; //numero de impresoras de cada tipo
	private Lock l = new ReentrantLock(true);

	private int esperoA = 0;
	private int esperoB = 0;

	//Conditions
	private Condition colaA = l.newCondition();
	private Condition colaB = l.newCondition();
	private Condition colaAB = l.newCondition();

	public GestorLock(int numA,int numB){
		numImpA = numA;
		numImpB = numB;
	}
	
	public void qImpresoraA(int id) throws InterruptedException {
		l.lock();
		try{
			System.out.println("Hebra de tipo A "+id+" pide impresora");
			//CS_ImpA: Espera si no hay impresora de tipo A - colaA
			while(numImpA==0){
				esperoA++;
				colaA.await();
			}
			numImpA--;
			System.out.println("Impresora de tipo A dada a la hebra tipo A " + id);
			System.out.println("Queda " + numImpA + " de tipo A " + numImpB + " de tipo B.");
			System.out.println("*************************************************************");
		}
		finally{
			l.unlock();
		}
	}
	
	
	public void qImpresoraB(int id) throws InterruptedException {
		l.lock();
		try{
			System.out.println("Hebra de tipo A "+id+" pide impresora");
			//CS_ImpA: Espera si no hay impresora de tipo A - colaA
			while(numImpB==0){
				esperoB++;
				colaB.await();
			}
			numImpB--;
			System.out.println("Impresora de tipo B dada a la hebra tipo B " + id);
			System.out.println("Queda " + numImpA + " de tipo A " + numImpB + " de tipo B.");
			System.out.println("*************************************************************");
		}
		finally{
			l.unlock();
		}
	}

	
	public char qImpresoraAB(int id) throws InterruptedException {
		char valor = 'A';
		try{
			l.lock();
			System.out.println("Hebra de tipo A "+id+" pide impresora");
			//CS_ImpA: Espera si no hay impresora de tipo A ni de tipo B - colaAB
			while(numImpA==0 && numImpB==0){
				colaAB.await();
			}
			if(numImpA>0){
				numImpA--;
				valor = 'A';
			}
			else{
				numImpB--;
				valor = 'B';
			}
			System.out.println("Impresora de tipo "+ valor + " dada a la hebra tipo " + valor +" " + id);
			System.out.println("Queda " + numImpA + " de tipo A " + numImpB + " de tipo B.");
			System.out.println("*************************************************************");
		}
		finally{
			l.unlock();
		}
		return valor;
	}
	
	
	public void dImpresora(char tipo) {
		try {
			l.lock();
			if (tipo == 'A') {
				numImpA++;
				if(esperoA>0){
					esperoA--;
					colaA.signal();
				}else{
					colaAB.signal();
				}
			} else {
				numImpB++;
				if(esperoB>0){
					esperoB--;
					colaB.signal();
				}else{
					colaAB.signal();
				}

			}

			System.out.println("Devuelta la impresora de tipo " + tipo);
			System.out.println("Queda " + numImpA + " de tipo A " + numImpB + " de tipo B.");
			System.out.println("*************************************************************");
		}
		finally {
			l.unlock();
		}
	}
}
