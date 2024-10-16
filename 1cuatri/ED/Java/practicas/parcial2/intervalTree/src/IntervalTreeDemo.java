import dataStructures.list.LinkedList;
import dataStructures.list.List;
public class IntervalTreeDemo {
    public static void main(String[] args){
        Interval resultado;
//Interval Tree t1 
        IntervalTree t1 = new IntervalTree();
        System.out.println("Tamaño de t1 antes de insertar elementos: " + t1.size());
        int low1 [] = {5,3,16,21,11};
        int high1 [] = {10,8,20,25,14};
        for(int i = 0; i < low1.length; i++){
            t1.insert(new Interval(low1[i], high1[i]));
        }
        System.out.println("Árbol t1: "+t1);
        System.out.println("Tamaño de t1: " + t1.size());
        List<Interval> intervalos = new LinkedList<>();
        intervalos.append(new Interval(27,30));
        intervalos.append(new Interval(2,4));
        intervalos.append(new Interval(11,13));
        intervalos.append(new Interval(1,2));
        intervalos.append(new Interval(14,16));
        intervalos.append(new Interval(4,7));
        intervalos.append(new Interval(7,12));
        intervalos.append(new Interval(11,15));
        for(Interval i : intervalos){
            resultado = t1.searchOverlappingInterval(i);
            if(resultado == null) {
                System.out.println(i+ " no solapa con intervalos de t1");
            }else {
                System.out.println(i+ " solapa en t1 con el intervalo " + resultado);
            }
        }

        System.out.println("**********************************");
        for(Interval i : intervalos){
            System.out.println(i + " lista de todos los intervalos de t1 con los que solapa:"+t1.allOverlappingIntervals(i))
            ;
        }
    }
}
/*
Tamaño de t1 antes de insertar elementos: 0
Árbol t1: [3, 8]( 8 )[5, 10]( 25 )[11, 14]( 14 )[16, 20](
25 )[21, 25]( 25 )
Tamaño de t1: 5
[27, 30] no solapa con intervalos de t1
[2, 4] solapa en t1 con el intervalo [3, 8]
[11, 13] solapa en t1 con el intervalo [11, 14]
[1, 2] no solapa con intervalos de t1
[14, 16] solapa en t1 con el intervalo [16, 20]
[4, 7] solapa en t1 con el intervalo [5, 10]
[7, 12] solapa en t1 con el intervalo [5, 10]
[11, 15] solapa en t1 con el intervalo [11, 14]
**********************************
[27, 30] lista de todos los intervalos de t1 con los que
solapa: LinkedList()
[2, 4] lista de todos los intervalos de t1 con los que
solapa: LinkedList([3, 8])
[11, 13] lista de todos los intervalos de t1 con los que
solapa: LinkedList([11, 14])
[1, 2] lista de todos los intervalos de t1 con los que
solapa: LinkedList()
[14, 16] lista de todos los intervalos de t1 con los que
solapa: LinkedList([16, 20],[11, 14])
[4, 7] lista de todos los intervalos de t1 con los que
solapa: LinkedList([5, 10],[3, 8])
[7, 12] lista de todos los intervalos de t1 con los que
solapa: LinkedList([5, 10],[3, 8],[11, 14])
[11, 15] lista de todos los intervalos de t1 con los que
solapa: LinkedList([11, 14])
*/