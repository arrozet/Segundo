/*
 * ALUMNO: Oliva Zamora, Rubén
 * GRADO: Ingeniería del Software A
 */

package C_publish_process_progress;

public class Primos {
    // Un número es primo si es mayor que 1 y sus únicos divisores son 1 y él mismo
    private long n1;
    private long n2;
    private int pos;

    public Primos(long n1,long n2,int pos){
        this.n1 = n1;
        this.n2 = n2;
        this.pos = pos;
    }

    public String toString(){
        return pos+":("+ n1 + ","+ n2+")";
    }

    public int pos(){
        return pos;
    }
}
