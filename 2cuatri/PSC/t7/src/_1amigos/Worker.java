package _1amigos;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CancellationException;

/** 
 * @author Monica
 *
 *  Cuando termina su tarea devuelve el resultado en una lista de Amigos
 *  
 *  No devuelve ningún resultado intermedio (Void)
 */
public class Worker extends SwingWorker<java.util.List<Amigos>,Void>{

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
	protected java.util.List<Amigos> doInBackground() throws Exception {
		List<Amigos> lista = new ArrayList<Amigos>();
	
		int numAmigos = 0; long amigo1 = 1;
		
		while (numAmigos < n && !this.isCancelled()){
			
			long amigo2 = sumaDivisores(amigo1);
			
			if (amigo2 >= amigo1){
				if (sumaDivisores(amigo2) == amigo1){
					lista.add(new Amigos(amigo1,amigo2,numAmigos));
					numAmigos++;
				}
			}
			amigo1++;
		}
		
		return lista;
	}
	
	public void done(){
		try {
			panel.limpiaArea();
			panel.escribeAmigos(get());
			panel.mensaje("numeros amigos calculados");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("tarea cancelada");
			e.printStackTrace();
		} catch (ExecutionException | CancellationException e) {
			// TODO Auto-generated catch block
			System.out.println("tarea cancelada");
		}
	}

}
