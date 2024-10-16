package basicos;

import java.util.Iterator;
import java.util.StringJoiner;

public class LinkedList<T> {

    private static class Node<E> {
        E elem;
        Node<E> next;

        Node(E elem) {
            this.elem = elem;
            this.next = null;
        }
    }

    private Node<T> first;

    // DO NOT MODIFY CODE ABOVE
    // Please, fill in your data
    //
    // Surname, Name:
    // Group:

    public void reverse() {
        first = reverse(null, first);
    }

    private Node<T> reverse(Node<T> prev, Node<T> current){
        if(current==null) {	// estoy en el final
        	return prev;
        }
        else {
        	// revisar esto
        	Node<T> next = current.next;
        	current.next = prev; // cambio sentido flechita -> el siguiente es el de antes
        	return reverse(current, next);
        }
    }

    // DO NOT MODIFY CODE BELOW

    public static LinkedList<Integer> testList() {
        LinkedList<Integer> list = new LinkedList<>();
        Node<Integer> node = new Node<>(0);
        list.first = node;
        for (int i = 1; i < 10; i++) {
            node.next = new Node<>(i);
            node = node.next;
        }
        return list;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(",", "LinkedList(", ")");
        for (Node<T> node = first; node != null; node = node.next)
            sj.add(node.elem.toString());
        return sj.toString();
    }
    
    public static void main(String[] args) {
    	System.out.println(testList());
    	LinkedList<Integer> l = testList();
    	l.reverse();
    	
    	System.out.println(l);
    }
}
