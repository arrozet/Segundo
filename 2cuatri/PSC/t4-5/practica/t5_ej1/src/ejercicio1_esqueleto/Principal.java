package ejercicio1_esqueleto;

import java.util.Scanner;
public class Principal {
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in);){
			int bufsz, max;
			System.out.print("Introduce tama�o del buffer: ");
			bufsz = sc.nextInt();
			System.out.print("Introduce maximo del generador: ");
			max = sc.nextInt();
			
			//COMPLETAR 
			// Recurso compartido
			Buffer buf = new Buffer(bufsz);
			
			// Clases que implementan las hebras
			Thread prod = new Productor(max,buf);
			Thread cons = new Consumidor(max, buf);
			
			prod.start();
			cons.start();
			prod.join();
			cons.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
