/* ------------------------------------------------------------------------------
   -- Student's name:
   -- Student's group:
   -- Identity number (DNI if Spanish/passport if Erasmus):
   --
   -- Data Structures. Grado en Informática. UMA.
   -------------------------------------------------------------------------------
*/

package exercises;
//import dataStructures.tuple.Tuple2;

import java.util.ArrayList;

public class BinaryTree<T extends Comparable<? super T>> {

    private static class Node<E> {
        E value;
        Node<E> left;
        Node<E> right;

        public Node(E value, Node<E> left, Node<E> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Node(E value) {
            this(value, null, null);
        }
    }

    private Node<T> root;

    public BinaryTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insertBST(T value) {
        root = insertBSTRec(root, value);
    }

    private Node<T> insertBSTRec(Node<T> node, T elem) {
        if (node == null) {
            node = new Node<>(elem);
        } else if (elem.compareTo(node.value) < 0)
            node.left = insertBSTRec(node.left, elem);
        else if (elem.compareTo(node.value) > 0)
            node.right = insertBSTRec(node.right, elem);
        else
            node.value = elem;
        return node;
    }

    /**
     * Returns representation of tree as a String.
     */
    @Override
    public String toString() {
        String className = getClass().getSimpleName();
        return className + "(" + toStringRec(this.root) + ")";
    }

    private static String toStringRec(Node<?> node) {
        return node == null ? "null" : "Node<" + toStringRec(node.left) + ","
                + node.value + "," + toStringRec(node.right) + ">";
    }

// -- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
// -- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
// -- EXERCISE 2

    public int subTreesInRange(T min, T max) {
        //TODO

        ArrayList<T> l = new ArrayList<>();
        recorrerArbol(root, l);

        int contador = 0;

        for(int i = 0; i<l.size(); i++)
        {
            if (l.get(i).compareTo(min) >= 0 && l.get(i).compareTo(max) <= 0 )
            {
                contador++;
            }
        }

        return contador;
    }

    private void recorrerArbol (Node<T> nodo,  ArrayList<T> l)
    {

        l.add(nodo.value);

        if(nodo.left != null)
        {
            recorrerArbol (nodo.left, l);
        }

        if(nodo.right != null)
        {
            recorrerArbol (nodo.right, l);
        }


    }
}
