public class Main {
    public static void main(String[] args) {
        try{
            VariableCompartida v = new VariableCompartida(0);
            int veces1 = 10000;
            int veces2 = 10000;
            int valor_esperado = veces1+veces2;
            Thread t1 = new ThreadCompartido("T1", v, veces1);
            Thread t2 = new ThreadCompartido("T2", v, veces2);

            t1.start();
            t2.start();


            t1.join();
            t2.join();

            System.out.println("Valor esperado: "+ valor_esperado + "\nValor recibido: " + v.getV());

        }catch(InterruptedException e){
            System.err.println(e.getMessage());
        }
        // PREGUNTA: ¿Se obtienen los resultados esperados? Aumenta
        // progresivamente el número de incrementos hasta observar algún
        // comportamiento “extraño”. Justifica los resultados obtenidos

        // Con iteraciones altas se obtienen valores inesperados, ya que ambos hilos están compitiendo
        // por el recurso compartido y pueden llegar a entrar a la vez creando conflictos, y, por ejemplo,
        // aumentando los dos a la vez la variable en cuestión; provocando una mala actualización del recurso compartido
    }
}
