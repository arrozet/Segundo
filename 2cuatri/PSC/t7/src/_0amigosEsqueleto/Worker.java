package _0amigosEsqueleto;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CancellationException;


/**
 * @author Monica
 *
 */

public class Worker extends SwingWorker<List<Amigos>,Void>{

	private int n;
	private Panel panel; //Para actualizar la vista desde el Worker

	public Worker(int n,Panel panel){
		this.n = n;
		this.panel = panel;
	}

	//Calcula la suma de los divisores de a (sin incluir a)
	private long sumaDivisores(long a){
		long suma = 1;
		int i = 2;
		while (i<a){
			if (a % i == 0) suma+=i;
			i++;
		}
		return suma;
	}

	@Override
	protected List<Amigos> doInBackground() throws Exception {
		//Calcula los pares de amigos
		List<Amigos> pares = new ArrayList<>();

		int numAmigos=0;
		long amigo1=2;
		while(numAmigos<n && !this.isCancelled()){
			long amigo2 = sumaDivisores(amigo1);
			if(amigo1<=amigo2 && amigo1==sumaDivisores(amigo2)){
				pares.add(new Amigos(amigo1, amigo2, numAmigos));
				numAmigos++;
			}
			amigo1++;
		}

		return pares;
	}

	public void done(){
		try{
			panel.limpiaArea();
			panel.escribeAmigos(get());
			panel.mensaje("numeros amigos calculados");
		}catch (ExecutionException | CancellationException e) {
			System.err.println("Tarea cancelada");
        } catch (InterruptedException e) {
			System.err.println("Tarea cancelada");
            e.printStackTrace();
		}
	}
}
