package _2amigosPublish;

import java.awt.event.*;

public class Controlador implements ActionListener{
	private Panel panel;
	private Worker worker;
	public Controlador(Panel panel){
		this.panel = panel;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("NUMERO")){
			panel.activarCancelar(true);
			worker = new Worker(panel.numero(),panel);
			worker.execute();
			panel.mensaje("Tarea creada");
		}else{
			if (worker!=null){
				worker.cancel(true);
				panel.mensaje("Tarea cancelada");
				panel.activarCancelar(false);
			}
			
		}
	}
}
