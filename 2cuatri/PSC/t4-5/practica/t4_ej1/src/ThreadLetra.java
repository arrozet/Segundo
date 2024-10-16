public class ThreadLetra implements Runnable{
    private String nombre;
    private char c;
    private int veces;

    public ThreadLetra(String nombre, char c, int veces){
        this.nombre = nombre;
        this.c = c;
        this.veces = veces;
    }
    @Override
    public void run() {
        for(int i=1; i<=veces; i++){
            System.out.println(nombre + ": " + i + " - " + c);
        }
    }
}
