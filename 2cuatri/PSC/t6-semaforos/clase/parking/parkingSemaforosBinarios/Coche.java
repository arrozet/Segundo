package parkingSemaforosBinarios;

//Cada coche entra en el parking y sale una sola vez
public class Coche extends Thread{
    private Parking parking;
    private int id;
    private java.util.Random rnd = new java.util.Random();
        
    public Coche(int id, Parking p){
         this.parking = p;
         this.id=id;
    }

    public void run(){
          try {
				parking.entrar(id);
				
				//El coche está aparcado un tiempo aleatorio
				Thread.sleep(50 + rnd.nextInt(1000));
				
				parking.salir(id);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
}
