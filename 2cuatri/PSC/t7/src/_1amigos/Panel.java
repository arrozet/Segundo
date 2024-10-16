package _1amigos;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
import java.util.List;

@SuppressWarnings("serial")
public class Panel extends JPanel{
	
	private JLabel etiqueta = new JLabel("Cuantos amigos quieres?");
	private JLabel mensaje = new JLabel("GUI Creada");
	private JTextField numero = new JTextField(3);
	
	private JTextArea listaAmigos = new JTextArea(10,40);
	private JScrollPane scroll = new JScrollPane(listaAmigos);
	private JButton fin = new JButton("Cancelar");
	
	
	public Panel(){
		this.setLayout(new BorderLayout());
		listaAmigos.setEnabled(false);
		fin.setEnabled(false);
		JPanel norte = new JPanel();
		norte.add(etiqueta);norte.add(numero);
		norte.add(fin);
		this.add(BorderLayout.NORTH, norte);
	    this.add(BorderLayout.CENTER,  scroll);
	    this.add(BorderLayout.SOUTH,  mensaje);
	}
	
	public void controlador(ActionListener ctr){
		numero.addActionListener(ctr);
		numero.setActionCommand("NUMERO");
		fin.addActionListener(ctr);
		fin.setActionCommand("FIN");
		
	}
	public int numero() {
		return Integer.parseInt(numero.getText());
	}

	public void escribeAmigos(List<_1amigos.Amigos> list){
		for (int i=0; i<list.size(); i++){
			listaAmigos.append(list.get(i).toString()+ "   ");
			if ((i+1)%5 == 0) listaAmigos.append("\n");
		}
		activarCancelar(false);
	}
	public void limpiaArea(){
		listaAmigos.setText("");
	}
	public void mensaje(String str){
		mensaje.setText(str);
	}
	
	public void activarCancelar(boolean valor) {
		fin.setEnabled(valor);
	}
}
