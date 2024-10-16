package _0amigosEsqueleto;

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
		norte.add(etiqueta);
		norte.add(numero);
		norte.add(fin);
		this.add(BorderLayout.NORTH, norte);
	    this.add(BorderLayout.CENTER,  scroll);
	    this.add(BorderLayout.SOUTH,  mensaje);



	}
	
	//El controlador se registra en los eventos
	//de los que quiere ser informado
	public void controlador(ActionListener ctr){
		numero.addActionListener(ctr);
		numero.setActionCommand("NUMERO");
		fin.addActionListener(ctr);
		fin.setActionCommand("FIN");
	}

	//Devuelve el número introducido por el usuario
	//en el campo de texto "numero"
	public int numero() {
		try {
			return Integer.parseInt(numero.getText());
		} catch (NumberFormatException e) {
			return 0; // Devuelve 0 si la entrada no es un número válido
		}
	}

	//Escribe en el área de texto
	//la lista de amigos recibida como parámetro
	public void escribeAmigos(List<Amigos> list){
		for (int i=0; i<list.size(); i++){
			listaAmigos.append(list.get(i).toString() + "\t");
			if ((i+1)%5 == 0) listaAmigos.append("\n");
		}
		activarCancelar(false);
	}
	
	//Limpia el área de texto
	public void limpiaArea(){
		listaAmigos.setText("");
	}
	
	//Escribe un mensaje en la parte inferior de la pantalla
	public void mensaje(String str){
		mensaje.setText(str);
	}
	
	//activa o desactiva el botón cancelar
	public void activarCancelar(boolean valor) {
		fin.setEnabled(valor);
	}
}
