public class ThreadCompartidoEsc extends Thread {
    private VariableCompartida v;
    private String nombre;
    private int veces;

    public ThreadCompartidoEsc(String nombre, VariableCompartida v, int veces){
        this.nombre = nombre;
        this.v = v;
        this.veces = veces;
    }

    @Override
    public void run() {
        for(int i = 1; i<=veces; i++){
            System.out.println("Valor leido: " + v.getV());
        }
    }
}
