/**
 * Estructuras de Datos. Grado en Informática, IS e IC. UMA.
 * Examen de Febrero 2015.
 *
 * Implementación del TAD Deque
 *
 * Apellidos:
 * Nombre:
 * Grado en Ingeniería ...
 * Grupo:
 * Número de PC:
 */

package dataStructures.doubleEndedQueue;

public class LinkedDoubleEndedQueue<T> implements DoubleEndedQueue<T> {

    private static class Node<E> {
        private E elem;
        private Node<E> next;
        private Node<E> prev;

        public Node(E x, Node<E> nxt, Node<E> prv) {
            elem = x;
            next = nxt;
            prev = prv;
        }
    }

    private Node<T> first, last;

    /**
     *  Invariants:
     *  if queue is empty then both first and last are null
     *  if queue is non-empty:
     *      * first is a reference to first node and last is ref to last node
     *      * first.prev is null
     *      * last.next is null
     *      * rest of nodes are doubly linked
     */

    /**
     * Complexity:
     */
    public LinkedDoubleEndedQueue() {
        // TODO Auto-generated method stub

    }

    /**
     * Complexity:
     */
    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Complexity:
     */
    @Override
    public void addFirst(T x) {
        // TODO Auto-generated method stub

    }

    /**
     * Complexity:
     */
    @Override
    public void addLast(T x) {
        // TODO Auto-generated method stub

    }

    /**
     * Complexity:
     */
    @Override
    public T first() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Complexity:
     */
    @Override
    public T last() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Complexity:
     */
    @Override
    public void deleteFirst() {
        // TODO Auto-generated method stub

    }

    /**
     * Complexity:
     */
    @Override
    public void deleteLast() {
        // TODO Auto-generated method stub

    }

    /**
     * Returns representation of queue as a String.
     */
    @Override
    public String toString() {
    String className = getClass().getName().substring(getClass().getPackage().getName().length()+1);
        String s = className+"(";
        for (Node<T> node = first; node != null; node = node.next)
            s += node.elem + (node.next != null ? "," : "");
        s += ")";
        return s;
    }
}
