public class ThreadCompartido extends Thread {
    private VariableCompartida v;
    private String nombre;
    private int veces;

    public ThreadCompartido(String nombre, VariableCompartida v, int veces){
        this.nombre = nombre;
        this.v = v;
        this.veces = veces;
    }

    @Override
    public void run() {
        for(int i = 0; i<veces; i++){
            v.setV(v.inc());
            //System.out.println(nombre + ": " + v.getV());
        }
    }
}
