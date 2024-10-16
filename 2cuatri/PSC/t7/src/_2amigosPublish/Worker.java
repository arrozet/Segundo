package _2amigosPublish;

import javax.swing.*;

import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class Worker extends SwingWorker<Void,Amigos>{

	private int n;
	private Panel panel;
	
	public Worker(int n,Panel panel){
		this.n = n; 
		this.panel = panel;
		panel.limpiaArea();
	}
	
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
	protected Void doInBackground() throws Exception {		
		int numAmigos = 0; long amigo1 = 1;
		while (numAmigos < n && !this.isCancelled()){
		
			long amigo2 = sumaDivisores(amigo1);
			if (amigo2 >= amigo1){
				if (sumaDivisores(amigo2) == amigo1){
					publish(new Amigos(amigo1,amigo2,numAmigos));
					numAmigos++;
				}
			}
			amigo1++;
		}
		
		return null;
	}
	
	//Para poder desactivar Cancelar una vez terminada la tarea
	public void done() {
		try {
			get(); //se espera a que termine
			panel.activarCancelar(false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (CancellationException e) {
			panel.mensaje("Tarea cancelada");
			panel.activarCancelar(false);
		}
	}
		
	public void process(List<Amigos> lista){
		try {
			panel.escribeAmigos(lista);
			panel.mensaje("numeros amigos calculados");
		}catch(CancellationException e) {
			panel.mensaje("TareaCancelada");
		}
	}
}
