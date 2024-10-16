package _2ProdConsSemaforosGenerales;

import javax.crypto.SealedObject;
import java.util.concurrent.Semaphore;

//Buffer sincronizado utilizando sem�foros
public class Buffer {
	private int[] elem; //array de elementos
	private int nelem;  //n�mero de elementos en el buffer
	private int p;      //posici�n donde guardar pr�ximo elemento
	private int c;      //posici�n donde est� el siguiente elemento a consumir
	Semaphore mutex = new Semaphore(1);	// exclusion mutua
	Semaphore hayHuecos;
	Semaphore hayDatos = new Semaphore(0); 	// CS_Consumidor
	public Buffer(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		hayHuecos = new Semaphore(n);	// CS_Productor
		elem = new int[n];
		p = 0;
		c = 0;
		nelem = 0;
	}
	
	public void insertar(int x) throws InterruptedException {
		//Condicion de sincronizacion - si el buffer esta lleno espero
		hayHuecos.acquire();
		mutex.acquire();
		//------SC-----
		elem[p] = x;
		p = (p+1) % elem.length; //incremento circular
		++nelem;
		System.out.println("Elemento Producido: " + x);
		//------FIN SC-----
		mutex.release();
		hayDatos.release();
	}
	
	/* MUY IMPORTANTE EL ORDEN DE LLAMADA A LOS SEM�FOROS
	 *   - Probad a intercambiar los dos acquire() y ver qu� ocurre
	 *   
	 */
	public int extraer() throws InterruptedException {
		//Condicion de sincronizacion - si el buffer esta vacio espero
		hayDatos.acquire();
		mutex.acquire();
		//------SC------
		int x = elem[c];
		c = (c+1) % elem.length; //incremento circular
		--nelem;
		System.out.println("Elemento Consumido: " + x);
		//------FIN SC----
		mutex.release();
		hayHuecos.release();
		return x;
	}
}
