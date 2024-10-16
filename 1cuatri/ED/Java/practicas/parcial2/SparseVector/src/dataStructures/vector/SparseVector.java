/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package dataStructures.vector;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SparseVector<T> implements Iterable<T> {
    private interface Vector<T> {
        T get(int sz, int i);
        Vector<T> set(int sz, int i, T x);
        default boolean isUnif(){ 
            return this instanceof Unif;
        }
        default Unif<T> toUnif() {
            if (!isUnif()) throw new VectorException("No Unif");
            return (Unif<T>) this;
        }
    }

    // Unif Implementation
    // Unif es un vector con todos los elementos = elem

    private static class Unif<T> implements Vector<T> {
        private T elem;
        public Unif(T e) {
            elem = e;
        }
        @Override
        public T get(int sz, int i) {
            // TODO
            return elem;
        }

        @Override
        public Vector<T> set(int sz, int i, T x) {
            // TODO
            if(sz==1){
                // perfecto, no hay que pensar para donde ir; solamente tengo una posibilidad
                return new Unif<>(x);   // hay que hacerlo asi y no cambiando elem pq sino modifico todo el vector
            }
            else{
                Node<T> n;
                // igual que en el set de Node
                if(i<sz/2){ // si lo que quiero setear esta por la izquierda, lo de la derecha lo dejo a Unif y miro la izq
                    n = new Node<>(set(sz/2,i,x),this);
                }
                else{   // lo mismo pero si esta por la derecha
                    n = new Node<>(this, set(sz/2,i-sz/2,x));   // hay que ajustar el indice, como en Node
                }
                return n;
            }

        }

        @Override
        public String toString() {
            return "Unif(" + elem + ")";
        }
    }

    // Node Implementation
    private static class Node<T> implements Vector<T> {
        private Vector<T> left, right;

        public Node(Vector<T> l, Vector<T> r) {
            left = l;
            right = r;
        }

        @Override
        public T get(int sz, int i) {
            if(i>=0 && i<=(sz/2)-1){
                return left.get(sz/2, i);
            }
            else {
                return right.get(sz / 2, i - sz/2);
            }
        }

        @Override
        public Vector<T> set(int sz, int i, T x) {
            if(i>=0 && i<=(sz/2)-1){
                left = left.set(sz/2, i,x);
            }
            else {
                right = right.set(sz / 2, i - sz/2,x);  // debo ajustar índices
            }
            return this.simplify();
        }

        public Vector<T> simplify() {
            // deberia usar compareTo, no equals
            // devuelve un solo unif en caso de que haya 2 y sean iguales

            if(left.isUnif() && right.isUnif() && left.toUnif().elem.equals(right.toUnif().elem)){
                return left;
            }
            else{
                return this;
            }

        }

        @Override
        public String toString() {
            return "Node(" + left + ", " + right + ")";
        }
    }

    // SparseVector Implementation
    private int size;
    private Vector<T> root;
    public SparseVector(int n, T elem) {
        if(n<0){
            throw new VectorException("n can't be < 0");
        }
        size = (int) Math.pow(2,n);

        // todos los elementos son iguales entre si, no se descomponen (invariante)
        root = new Unif<>(elem);
    }

    public int size() {
        // TODO
        return size;
    }

    public T get(int i) {
        if(!(i>=0 && i<size)){
            throw new VectorException("Get: invalid index");
        }
        return root.get(size,i);
    }

    public void set(int i, T x) {
        if(!(i>=0 && i<size)){
            throw new VectorException("Get: invalid index");
        }
        root = root.set(size,i,x);
    }

    @Override
    public String toString() {
        return "SparseVector(" + size + "," + root + ")";
    }

    @Override
    public Iterator<T> iterator() {
        return new IterVector();
    }

    private class IterVector implements Iterator<T> {
         private int index;

         public IterVector(){
             index = 0;
         }

        public boolean hasNext() {
            // TODO
            return index<size;
        }
        public T next() {
            // TODO
            if(hasNext()){
                int aux = index;
                index++;
                return get(aux);
            }
            else{
                throw new NoSuchElementException("IterVector next: there's no next");
            }
        }
    }


    /**
     * Returns a String with the representation of tree in DOT (graphviz).
     */
    public String toDot(String vectorName) {
        final StringBuffer sb = new StringBuffer();
        sb.append(String.format("digraph \"%s\" {\n", vectorName));
        sb.append("labelloc=\"t\"");
        sb.append(String.format("label=\"size = %s\"", size));
        sb.append("node [fontname=\"Arial\", fontcolor=red, shape=circle, style=filled, color=\"#66B268\", fillcolor=\"#AFF4AF\" ];\n");
        sb.append("edge [color = \"#0070BF\"];\n");
        toDotRec(root, sb);
        sb.append("}");
        return sb.toString();
    }

    private static <E> void toDotRec(Vector<E> current, StringBuffer sb) {
        final int currentId = System.identityHashCode(current);
        if (current instanceof Node<E>) {
            Node<E> node = (Node<E>) current;
            sb.append(String.format("%d [label=\"%s\"];\n", currentId, ""));
            processChild(node.left, sb, currentId);
            processChild(node.right, sb, currentId);
        } else {
            Unif<E> unif = (Unif<E>) current;
            sb.append(String.format("%d [label=\"%s\" , shape=square];\n", currentId, unif.elem));
        }
    }

    private static void processChild(Vector<?> child, StringBuffer sb, int parentId) {
        sb.append(String.format("%d -> %d;\n", parentId, System.identityHashCode(child)));
        toDotRec(child, sb);
    }


}
