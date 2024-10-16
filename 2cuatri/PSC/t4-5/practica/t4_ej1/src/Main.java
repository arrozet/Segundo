public class Main {
    public static void main(String[] args) {
        try{
            Thread t1 = new Thread(new ThreadLetra("T1", 'a', 10));
            Thread t2 = new Thread(new ThreadLetra("T2", 'b', 5));
            Thread t3 = new Thread(new ThreadLetra("T3", 'c', 2));

            t1.start();
            t2.start();
            t3.start();

            t1.join();
            t2.join();
            t3.join();
        }catch(InterruptedException e){
            System.err.println(e.getMessage());
        }
        // PREGUNTA: ¿Se mezclan las letras? Justifica el comportamiento observado

        // Claro que se mezclan las letras, no hay mecanismos de sincronización
    }
}
