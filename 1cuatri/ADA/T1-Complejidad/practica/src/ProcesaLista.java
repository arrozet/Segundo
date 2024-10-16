import orden.*;
import java.util.List;

import java.util.LinkedList;

public class ProcesaLista extends Metodo{
	private List<Integer> lista;
	
	public ProcesaLista() {
		super();
		lista = null;
		
	}
	public void setLista(List<Integer> l) {
		lista = l;
	}
		
	public List<Integer> getLista() {
		return lista;
	}
	
	/**
	 * Procesamos todos los elementos de la lista lista.
	 * return El número de elementos procesados en realidad.
	 */
	@Override
	public int codigo(int n) {
		procesaLista(lista);
		return n>lista.size()?n:lista.size();
	}
	
	/*private void procesaLista(List<Integer> lista) {
		int i=0;
		int j=1;
		if(lista.size()>1) {
			while(j<lista.size()) {
				if(lista.get(i)==lista.get(j)) {
					lista.remove(i);	// como esto tiene O(n), en el peor caso sería O(n^2)
				}
				else {
					i++;
					j++;
				}
			}
		}
	}*/
	
	/*private void procesaLista(List<Integer> lista) {
		int i=0;
		LinkedList<Integer> aux = new LinkedList<>();
		
		if(lista.size()>1) {
			while(i<lista.size()) {
				if(aux.size() > 0) {
					if(aux.getLast() != lista.get(i)){
						aux.add(lista.get(i));
					}
					i++;
				}
				else {
					aux.add(lista.get(i));
					i++;
				}
				
			}
			lista.clear();
			lista.addAll(aux);
		}
	}*/
	
	private void procesaLista(List<Integer> lista) {
		int i=0;
		LinkedList<Integer> aux = new LinkedList<>();
		
		if(lista.size()>1) {
			while(i<lista.size()) {
				if(aux.size() > 0) {
					if(aux.getLast() != lista.get(i) && lista.get(i) % 2 == 1){
						aux.add(lista.get(i));
					}
					else if(aux.getLast() != lista.get(i) && lista.get(i) % 2 == 0) {
						aux.add(lista.get(i));
						aux.add(lista.get(i));
					}
//					else if(aux.size()-2 > 0) {
//						if(aux.getLast() == lista.get(i) && 
//								aux.get(aux.size()-2) != lista.get(i) &&
//								lista.get(i) % 2 == 0) {
//									aux.add(lista.get(i));
//								}
//					}
					
					i++;
				}
				else {
					aux.add(lista.get(i));
					i++;
				}
				
			}
			lista.clear();
			lista.addAll(aux);
		
		}
	}

}
