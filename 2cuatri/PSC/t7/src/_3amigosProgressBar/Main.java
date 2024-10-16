package _3amigosProgressBar;

import java.util.ArrayList;
import java.util.List;

public class Main {
	private static boolean sumaDivisores(int a, int b){
		int suma = 0;
		int i = 1;
		while (i<a && suma <= b){
			if (a % i == 0) suma+=i;
			i++;
		}
		return suma == b;
	}
	
	private static int sumaDivisores(int a){
		int suma = 1;
		int i = 2;
		while (i<a){
			if (a % i == 0) suma+=i;
			i++;
		}
		return suma;
	}

	public static void  main(String[] args){
		// TODO Auto-generated method stub
		List<Amigos> lista = new ArrayList<Amigos>();
		int numAmigos = 0; int amigo = 1;
		while (numAmigos < 7){
			int posAmigo = sumaDivisores(amigo);
			if (posAmigo >= amigo){
				if (sumaDivisores(posAmigo,amigo)){
					lista.add(new Amigos(amigo,posAmigo,numAmigos));
					numAmigos++;
				}
			}
			amigo++;
		}
		System.out.println(lista);
	}

}
