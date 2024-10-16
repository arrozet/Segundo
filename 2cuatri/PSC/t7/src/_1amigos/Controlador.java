package _1amigos;

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
			worker = new Worker(panel.numero(),panel);
			worker.execute();
			panel.mensaje("Tarea creada");
			panel.activarCancelar(true);
		}else if (e.getActionCommand().equals("FIN")){
			if (worker!=null){
				worker.cancel(true);
				panel.activarCancelar(false);
				panel.mensaje("Tarea cancelada");
			}
		}
	}
}
