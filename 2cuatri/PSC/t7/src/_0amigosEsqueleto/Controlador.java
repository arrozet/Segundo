package _0amigosEsqueleto;

import java.awt.event.*;

public class Controlador implements ActionListener{
	private Panel panel;
	private Worker worker;
	
	public Controlador(Panel panel){
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("NUMERO")){
			//Calcula los amigos y los muestra en la vista
			//panel.limpiaArea();
			//panel.mensaje("Calculando N amigos");
			worker = new Worker(panel.numero(), panel);
			worker.execute();
			panel.mensaje("Tarea creada");
			panel.activarCancelar(true);

		}else if (e.getActionCommand().equals("FIN")){
			if (worker!=null){
				//Cancela la tarea
				worker.cancel(true);
				panel.activarCancelar(false);
				panel.mensaje("Tarea cancelada");
			}
		}
	}
}
