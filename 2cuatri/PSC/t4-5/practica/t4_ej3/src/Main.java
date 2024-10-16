public class Main {
    public static void main(String[] args) {
        try{
            VariableCompartida v = new VariableCompartida(0);
            int veces1 = 100;
            int veces2 = 100;
            //int valor_esperado = veces1+veces2;
            Thread t1 = new ThreadCompartidoMod("T1", v, veces1);
            Thread t2 = new ThreadCompartidoEsc("T2", v, veces2);

            t1.start();
            t2.start();


            t1.join();
            t2.join();

            //System.out.println("Valor esperado: "+ valor_esperado + "\nValor recibido: " + v.getV());

        }catch(InterruptedException e){
            System.err.println(e.getMessage());
        }
        // PREGUNTA: ¿Se recogen todos los valores? ¿Qué ocurre?

        // Como no hay sincronización, cada hebra muetra sus respectivos println cuando pueden, leyendo a veces valores repetidos
        // y no leyéndose otros valorse
    }
}
