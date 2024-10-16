package _3amigosProgressBar;

import java.awt.event.*;

public class Controlador implements ActionListener{
	private Panel panel;
	private Worker worker;
	private ControladorBarra ctrbarra;
	
	public Controlador(Panel panel,ControladorBarra ctrbarra){
		this.panel = panel;
		this.ctrbarra = ctrbarra;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("NUMERO")){
			worker = new Worker(panel.numero(),panel);
			worker.addPropertyChangeListener(ctrbarra);
			worker.execute();
			panel.mensaje("Tarea creada");
			panel.activarCancelar(true);
		}else{
			if (worker!=null){
				worker.cancel(true);
				panel.mensaje("Tarea cancelada");
				panel.activarCancelar(false);
			}
			
		}
	}
}
