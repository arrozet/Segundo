public class ThreadCompartidoMod extends Thread {
    private VariableCompartida v;
    private String nombre;
    private int veces;

    public ThreadCompartidoMod(String nombre, VariableCompartida v, int veces){
        this.nombre = nombre;
        this.v = v;
        this.veces = veces;
    }

    @Override
    public void run() {
        for(int i = 1; i<=veces; i++){
            v.setV(i);
            System.out.println("Valor modificado a: " + i);
        }
    }
}
