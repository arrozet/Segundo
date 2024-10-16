//package ejercicio2Lamport_esqueleto;


public class Principal {
	
	public static void main(String[] args){
		final int MAX = 1000;
		
		Lago lago = new Lago();     //Recurso compartido
		
		//Hebras de dos tipos distintos: Rio y Lago
		//??IMPORTANTE!! 
		//En esta soluci?n los identificadores deben ser 
		//distintos para los cuatro procesos
		Rio r0 = new Rio(lago,0,MAX);  
		Rio r1 = new Rio(lago,1,MAX);
		Presa p0 = new Presa(lago,2,MAX);
		Presa p1 = new Presa(lago,3,MAX);
		
		r0.start();
		r1.start();
		p0.start();
		p1.start();
		
		try{
			r0.join();
			r1.join();
			p0.join();
			p1.join();
		}catch(InterruptedException e){;}
		//Mostramos el nivel final del lago
		System.out.println("Nivel Final Lago: "+ lago.get());
	}
}
