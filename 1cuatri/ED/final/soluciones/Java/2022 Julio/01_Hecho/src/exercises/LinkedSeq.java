/* ------------------------------------------------------------------------------
   -- Student's name:
   -- Student's group:
   -- Identity number (DNI if Spanish/passport if Erasmus):
   --
   -- Data Structures. Grado en Informática. UMA.
   -------------------------------------------------------------------------------
*/

package exercises;

import java.util.StringJoiner;

public class LinkedSeq<T> {
  private static class Node<E> {
    E elem;
    Node<E> next;

    public Node(E elem, Node<E> next) {
      this.elem = elem;
      this.next = next;
    }
  }

  private Node<T> first;

  public LinkedSeq() {
    first = null;
  }

  private LinkedSeq(Node<T> node) {
    first = node;
  }

  public static LinkedSeq<Integer> fromInt(int n) {
    return new LinkedSeq<>(fromInt(n, null));
  }

  private static Node<Integer> fromInt(int n, Node<Integer> node) {
    return (n < 10 ? new Node<>(n, node)
            : fromInt(n / 10, new Node<>(n % 10, node)));
  }

  @Override
  public boolean equals(Object that) {
    if (this == that)
      return true;
    if (that == null)
      return false;
    if (!(that instanceof LinkedSeq<?>))
      return false;
    else
      return equals(this.first, ((LinkedSeq<?>) that).first);
  }

  private static boolean equals(Node<?> node1, Node<?> node2) {
    if (node1 == null)
      return node2 == null;
    else if (node2 == null)
      return false;
    else
      return node1.elem.equals(node2.elem) && equals(node1.next, node2.next);
  }

  @Override
  public String toString() {
    StringJoiner joiner = new StringJoiner(", ", "LinkedSeq(", ")");
    for (Node<T> node = first; node != null; node = node.next)
      joiner.add(node.elem.toString());
    return joiner.toString();
  }

// -- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
// -- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
// -- EXERCISE 1


  public static void addSingleDigit(int d, LinkedSeq<Integer> linkedSeq) {

    int [] d0 = new int [1];
    Node<Integer> [] d1 = new Node [1];
    d0[0] = d;
    Node<Integer> prim = linkedSeq.first;
    d1[0] = prim;

  addSingleDigitRec(d0,linkedSeq.first, d1);
  linkedSeq.first = d1[0];

  }

  private static void addSingleDigitRec(int [] d, Node<Integer> nodo, Node<Integer> [] d1) {

    if(nodo.next == null)
    {
      if(d[0]+nodo.elem < 10)
      {
        nodo.elem = nodo.elem+d[0];
        d[0] = 0;
      } else
      {
        nodo.elem = d[0] + nodo.elem - 10;
        d[0] = 1;
      }

    } else
    {
      Node<Integer> nodoAntes = nodo;
      int valorAntes = nodo.next.elem;
      addSingleDigitRec (d, nodo.next, d1);

      int num = d[0]+valorAntes;

      if(nodo.elem+d[0] < 10)
      {
        nodo.elem = nodo.elem+d[0];
        d[0] = 0;

      } else
      {
        if (nodoAntes == d1[0])
        {
          Node<Integer> nuevoNodo = new Node<>(1, d1[0]);
          d1[0] = nuevoNodo;
        }
        nodo.elem = 0;
      }

    }

  }


}

