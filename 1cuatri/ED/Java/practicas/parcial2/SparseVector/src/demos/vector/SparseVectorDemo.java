package demos.vector;

import dataStructures.vector.SparseVector;

public class SparseVectorDemo {
    public static void main(String[] args) {
        SparseVector<String> sv = new SparseVector<>(3,"hola");
        System.out.println(sv);
        for (int i =0; i < 8; i++) {
            System.out.println("i = " +i);
            sv.set(i,"que");
            System.out.println(sv);
            sv.set(i, "hola");
            System.out.println(sv);
        }
    }
}
/*
SparseVector(8,Unif(hola))
i = 0
SparseVector(8,Node(Node(Node(Unif(que), Unif(hola)), Unif(hola)), Unif(hola)))
SparseVector(8,Unif(hola))
i = 1
SparseVector(8,Node(Node(Node(Unif(hola), Unif(que)), Unif(hola)), Unif(hola)))
SparseVector(8,Unif(hola))
i = 2
SparseVector(8,Node(Node(Unif(hola), Node(Unif(que), Unif(hola))), Unif(hola)))
SparseVector(8,Unif(hola))
i = 3
SparseVector(8,Node(Node(Unif(hola), Node(Unif(hola), Unif(que))), Unif(hola)))
SparseVector(8,Unif(hola))
i = 4
SparseVector(8,Node(Unif(hola), Node(Node(Unif(que), Unif(hola)), Unif(hola))))
SparseVector(8,Unif(hola))
i = 5
SparseVector(8,Node(Unif(hola), Node(Node(Unif(hola), Unif(que)), Unif(hola))))
SparseVector(8,Unif(hola))
i = 6
SparseVector(8,Node(Unif(hola), Node(Unif(hola), Node(Unif(que), Unif(hola)))))
SparseVector(8,Unif(hola))
i = 7
SparseVector(8,Node(Unif(hola), Node(Unif(hola), Node(Unif(hola), Unif(que)))))
SparseVector(8,Unif(hola))
 */