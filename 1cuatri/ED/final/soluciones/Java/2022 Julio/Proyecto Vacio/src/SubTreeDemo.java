import exercises.BinaryTree;

import exercises.LinkedSeq;

public class SubTreeDemo {

    public static void main(String[] args) {
        Integer[] values = {11, 2, 1, 7, 29, 15, 40, 35};
        BinaryTree<Integer> t = mkBST(values);
        System.out.println("Árbol original " + t);
        
        int n = t.subTreesInRange(0,10);
        System.out.println("Subárboles en el rango [0,10] " + n);

        n = t.subTreesInRange(10,20);
        System.out.println("Subárboles en el rango [10,20] " + n);
        
        n = t.subTreesInRange(1,50);
        System.out.println("Subárboles en el rango [1,50] " + n);
        
/*
Árbol original BinaryTree(Node<Node<Node<null,1,null>,2,Node<null,7,null>>,11,Node<Node<null,15,null>,29,Node<Node<null,35,null>,40,null>>>)
Subárboles en el rango [0,10] 3
Subárboles en el rango [10,20] 1
Subárboles en el rango [1,50] 8
*/
    }

    private static <T extends Comparable<? super T>> BinaryTree<T> mkBST(T[] values) {
        BinaryTree<T> t = new BinaryTree<>();
        for (T v : values) {
            t.insertBST(v);
        }
        return t;
    }
}
