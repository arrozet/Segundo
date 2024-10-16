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
        first = null;
        last = null;
//        first.next = last;
//        last.prev = first;
    }

    /**
     * Complexity:
     */
    @Override
    public boolean isEmpty() {
        return first == null && last == null;
    }

    /**
     * Complexity:
     */
    @Override
    public void addFirst(T x) {
        Node n = new Node(x,first,null);    // siguiente nuevo first -> antiguo first
        if(this.isEmpty()){ // 0 elem
            first = n;      // first y last -> n
            last = n;
        }
        else{   // mas de 1 elem
            first.prev = n;         // anterior antiguo first -> nuevo first
            first = n;              // first -> n
        }

    }

    /**
     * Complexity:
     */
    @Override
    public void addLast(T x) {
        Node n = new Node(x,null,last); // anterior nuevo last -> antiguo last
        if(this.isEmpty()){ // no elem
            first = n;      // first y last -> n
            last = n;
        }
        else{   // mas de 1 elem
            last.next = n; // siguiente antiguo last -> nuevo last
            last = n;      // last -> n
        }
    }

    /**
     * Complexity:
     */
    @Override
    public T first() {
        return first.elem;
    }

    /**
     * Complexity:
     */
    @Override
    public T last() {
        return last.elem;
    }

    /**
     * Complexity:
     */
    @Override
    public void deleteFirst() {
        if(!this.isEmpty()){
            if(first.next == null){ // si solo hay 1 elem
                first = null;   // first y last -/> (desconectados)
                last = null;
            }
            else{   // si hay mas de 1 elem... Quiero borrar 1
                first.next.prev = null; // 1 </- 2
                first = first.next;     // first -> 2
            }

        }
    }

    /**
     * Complexity:
     */
    @Override
    public void deleteLast() {
        if(!this.isEmpty()){
            if(first.next == null){ // si solo hay 1 elem
                first = null;   // first y last -/> (desconectados)
                last = null;
            }
            else{   // si hay mas de 1 elem... Quiero borrar 1
                last.prev.next = null; // last-1 </- last-2
                last = last.prev;     // last -> last-2
            }

        }
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
