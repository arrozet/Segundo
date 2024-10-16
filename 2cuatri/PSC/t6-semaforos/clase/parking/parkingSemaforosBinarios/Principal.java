package parkingSemaforosBinarios;

public class Principal {
    public static void main(String[] args){
      //Recurso compartido: parking con 6 plazas
      Parking parking = new Parking(6);   //Entidad pasiva. Recurso compartido
      
      //Creamos 20 coches distintos
      Coche coches [] = new Coche[20];
      for (int i=0; i<20; i++) {
    	  coches[i] = new Coche(i,parking);
      }

      //Iniciamos las 20 hebras
      for (int i=0; i<20; i++) {
    	  coches[i].start();
      }

      try{
    	  //Esperamos a que las 20 hebras finalicen
    	  for (int i=0; i<20; i++) {
        	  coches[i].join();
          }
      } catch (InterruptedException e){
          System.out.println("La hebra ha sido interrumpida");
      }
    }
}