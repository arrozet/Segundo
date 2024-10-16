package ejercicio2Peterson_esqueleto;

public class Principal {
	public static void main(String[] args){
		final int MAX = 10;	// esto en realidad no sirve de nada
		Lago lago = new Lago();     //Recurso compartido

		//COMPLETAR
		Thread r1 = new Rio(0, lago, MAX);
		Thread r2 = new Rio(1, lago, MAX);
		Thread p1 = new Presa(0, lago, MAX);
		Thread p2 = new Presa(1, lago, MAX);
		r1.start();
		r2.start();
		p1.start();
		p2.start();

		try{
			r1.join();
			r2.join();
			p1.join();
			p2.join();
		}catch(InterruptedException e){
			System.err.println(e.getMessage());
		}

		
		//Mostramos el nivel final del lago
		System.out.println("Nivel Final Lago: "+ lago.get());
	}
}
