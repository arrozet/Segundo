/* @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Sets implemented using a sorted linked structure
 */

package dataStructures.set;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class SortedLinkedSet<T extends Comparable<? super T>> implements SortedSet<T> {
  // A node in the linked structure
  static private class Node<E> {
    E elem;
    Node<E> next;

    Node(E x, Node<E> node) {
      elem = x;
      next = node;
    }
  }

  // INVARIANTS: Nodes for elements included in the set are kept in a sorted
  //             linked structure (sorted in ascending order with respect to
  //             their elements). In addition, there should be no repetitions
  //             of elements in the linked structure.
  private Node<T> first; // Reference to first (with the smallest element) node
  private int size;      // Number of elements in this set

  // Constructs an empty set
  public SortedLinkedSet() {
    first = null;
    size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  private class Finder {
    boolean found;
    Node<T> previous, current;

    Finder(T elem) {
      //  An invocation of this constructor should
      //  search for elem in this class sorted linked structure.
      //  Attribute found should be set to true if elem was
      //  found or false otherwise.
      //  At the end of the search, current
      //  should be a reference to the node storing elem
      //  and previous should be a reference to node
      //  before current (or null if elem was found at first node).
      found = false;
      current = first;
      previous = null;
    // se usa compareTo pq equals no funciona siempre
      while(!found && current != null && current.elem.compareTo(elem) <= 0){ // el <= 0 es pq si el siguiente es mayor, que no siga buscando
                                                                                  // en el previo estara el elem inmediatamente menor que elem
        if(elem.compareTo(current.elem) == 0) {
          found = true;
        }
        else{
          previous = current;
          current = current.next;
        }
      }

    }
  }

  public void insert(T elem) {
    //  Implement insert by using Finder.
    //  insert should add a new node for elem
    //  to this class sorted linked structure
    //  if elem is not yet in this set.
    Finder f = new Finder(elem);

    if(!f.found){
      Node<T> nuevo = new Node(elem, f.current);
      if(f.previous == null){ // si el previous es null, no tengo elementos. Inserto en el primer
        first = nuevo;
      }
      else{
        f.previous.next = nuevo;
      }
      size++;
    }

  }

  public boolean isElem(T elem) {
    Finder f = new Finder(elem);
    //  Implement isElem by using Finder.
    //  isElem should return true is elem
    //  is in this class sorted linked structure
    //  or false otherwise.
    return f.found;
  }

  public void delete(T elem) {
    //  Implement delete by using Finder.
    //  delete should remove the node containing elem
    //  from this class sorted linked structure
    //  if elem is in this set.

    Finder f = new Finder(elem);
    if(f.found){
      if(f.previous==null){
        first = f.current.next;
      }
      else{
        f.previous.next = f.current.next;
      }
      size--;
    }
  }

  public String toString() {
    String className = getClass().getSimpleName();
    StringJoiner sj = new StringJoiner(", ", className + "(", ")");
    for (Node<T> node = first; node != null; node = node.next)
      sj.add(node.elem.toString());
    return sj.toString();
  }

  /**
   * Iterator over elements in this set.
   * Note that {@code remove} method is not supported. Note also
   * that linked structure should not be modified during iteration as
   * iterator state may become inconsistent.
   *
   * @see java.lang.Iterable#iterator()
   */
  public Iterator<T> iterator() {
    return new LinkedSetIterator();
  }

  private class LinkedSetIterator implements Iterator<T> {
    Node<T> current; // A reference to node with value that will be iterated next

    public LinkedSetIterator() {
      //   Initialize iterator by making current a reference to first node
      current = first;
    }

    public boolean hasNext() {
      //  Check if all elements have already been returned by this iterator
      return current!=null;
    }

    public T next() {
      //  Check if there are still more elements to be returned (raise
      //  NoSuchElementException otherwise), return next element and
      //  advance iterator to next node for next iteration.
      if(!hasNext()) {
        throw new NoSuchElementException("There's no next element");
      }
      T elem = current.elem;
      current = current.next;
      return elem;
    }
  }

  // private constructor for building a SortedLinkedSet
  // by providing a reference to first node and size
  private SortedLinkedSet(Node<T> first, int size) {
    this.first = first;
    this.size = size;
  }

  // a buffer can be used to construct a SortedLinkedSet
  // efficiently in an incremental way by appending elements
  // in ascending order
  private static class SortedLinkedSetBuffer<T extends Comparable<? super T>> {
    Node<T> first, last; // references to first and last nodes in buffer
    int size;            // number of elements in buffer

    // Builds an empty buffer
    SortedLinkedSetBuffer() {
      first = null;
      last = null;
      size = 0;
    }

    // Adds a new element at the end of buffer.
    // precondition: elem should be larger than any element
    // currently in buffer
    void append(T elem) {
      assert first == null || elem.compareTo(last.elem) > 0 : "SortedLinkedSetBuffer.append: precondition failed";
      Node<T> node = new Node<>(elem, null);
      if (first == null) {
        first = node;
      } else {
        last.next = node;
      }
      last = node;
      size++;
    }

    // Builds a SortedLinkedSet using this buffer.
    SortedLinkedSet<T> toSortedLinkedSet() {
      return new SortedLinkedSet<>(first, size);
    }
  }

  // Copy constructor: builds a new SortedLinkedSet with the same
  // elements as parameter sortedSet.
  public SortedLinkedSet(SortedSet<T> sortedSet) {
    //  Implement this copy constructor using a SortedLinkedSetBuffer
    SortedLinkedSetBuffer buffer = new SortedLinkedSetBuffer();
    Iterator<T> it = sortedSet.iterator(); //iterador para recorrer el SortedSet

    while(it.hasNext()){
      buffer.append(it.next()); //mete todos los elementos en el buffer y va avanzando con el iterator (next)
    }

    SortedLinkedSet<T> newSet = buffer.toSortedLinkedSet();
    this.first=newSet.first;
    this.size=newSet.size;

    // no sé por qué esto funciona

  }

  public static <T extends Comparable<? super T>>
  SortedLinkedSet<T> union(SortedLinkedSet<T> set1, SortedLinkedSet<T> set2) {
    //      Should compute a new SortedLinkedSet including all elements which are
    //      in set1 or in set2.
    //      Neither set1 nor set2 should be modified.
    //      Implement this method by using a SortedLinkedSetBuffer.
    SortedLinkedSetBuffer buffer = new SortedLinkedSetBuffer();
    Node<T> n1 = set1.first;
    Node<T> n2 = set2.first;

    while(n1!=null && n2!=null){    // mientras no haya recorrido todos los conjuntos
      T elem = null;
      if(n1.elem.compareTo(n2.elem) == 0){  // si son iguales
        elem = n1.elem;
        n1 = n1.next;
        n2 = n2.next; // avanzo los 2
      } else if (n1.elem.compareTo(n2.elem) < 0) {  // si n1 es el más peque
        elem = n1.elem;
        n1 = n1.next;
      } else if (n1.elem.compareTo(n2.elem) > 0) { // si n2 es el más peque
        elem = n2.elem;
        n2 = n2.next;
      } else if (n1==null && n2==null) {
        // no hago nada, el else if se encarga de que no se entra en las demás
      } else if (n1==null) {
        elem = n2.elem;
        n2 = n2.next;
      } else if (n2==null) {
        elem = n1.elem;
        n1 = n1.next;
      }

      buffer.append(elem);
    }

    return buffer.toSortedLinkedSet();
  }

  public static <T extends Comparable<? super T>>
  SortedLinkedSet<T> intersection(SortedLinkedSet<T> set1, SortedLinkedSet<T> set2) {
    //      Should compute a new SortedLinkedSet including only common elements in
    //      set1 and in set2.
    //      Neither set1 nor set2 should be modified.
    //      Implement this method by using a SortedLinkedSetBuffer.
    SortedLinkedSetBuffer<T> buffer = new SortedLinkedSetBuffer<>();
    Iterator<T> it1 = set1.iterator();
    Iterator<T> it2 = set2.iterator();

    T el1 = null;
    T el2 = null;

    // inicializo los elementos
    if (it1.hasNext()) {
      el1 = it1.next();
    }

    if (it2.hasNext()) {
      el2 = it2.next();
    }

    while (el1 != null && el2 != null) {
      if (el1.compareTo(el2) < 0) { // si el mas peque es 1, avanzo el 1
        if (it1.hasNext()) {
          el1 = it1.next();
        } else {
          el1 = null;
        }
      }
      else if (el1.compareTo(el2) > 0) { // si el mas peque es 2, avanzo el 2
        if (it2.hasNext()) {
          el2 = it2.next();
        } else {
          el2 = null;
        }
      }
      else if (el1.compareTo(el2) == 0) { // si son iguales, se añade. Avanzo los 2
        buffer.append(el1);

        if (it1.hasNext()) {
          el1 = it1.next();
        } else {
          el1 = null;
        }

        if (it2.hasNext()) {
          el2 = it2.next();
        } else {
          el2 = null;
        }
      }
    }
    return buffer.toSortedLinkedSet();
  }

  public static <T extends Comparable<? super T>>
  SortedLinkedSet<T> difference(SortedLinkedSet<T> set1, SortedLinkedSet<T> set2) {
    // todo Should compute a new SortedLinkedSet including all elements in
    //      set1 which are not in set2.
    //      Neither set1 nor set2 should be modified.
    //      Implement this method by using a SortedLinkedSetBuffer.
    SortedLinkedSetBuffer<T> buffer = new SortedLinkedSetBuffer<>();
    Iterator<T> it1 = set1.iterator();
    Iterator<T> it2 = set2.iterator();

    T el1 = null;
    T el2 = null;

    // inicializo los elementos
    if (it1.hasNext()) {
      el1 = it1.next();
    }

    if (it2.hasNext()) {
      el2 = it2.next();
    }

    while (el1 != null) {
      if (el2== null || el1.compareTo(el2) < 0) { // si el mas peque es 1 o el 2 es null, SE AÑADE y avanzo el 1
        buffer.append(el1);
        if (it1.hasNext()) {
          el1 = it1.next();
        } else {
          el1 = null;
        }
      }
      else if (el1.compareTo(el2) > 0) { // si el mas peque es 2, avanzo el 2
        if (it2.hasNext()) {
          el2 = it2.next();
        } else {
          el2 = null;
        }
      }
      else if (el1.compareTo(el2) == 0) { // si son iguales, NO añade. Avanzo los 2

        if (it1.hasNext()) {
          el1 = it1.next();
        } else {
          el1 = null;
        }

        if (it2.hasNext()) {
          el2 = it2.next();
        } else {
          el2 = null;
        }
      }
    }
    return buffer.toSortedLinkedSet();
  }

  public void union(SortedSet<T> sortedSet) {
    //  Should modify this set so that it becomes the union of
    //  this set and parameter sortedSet.
    //  Parameter sortedSet should not be modified.
    //  Should reuse current nodes in this set and should create new nodes for
    //  elements copied from sortedSet.
    this.union(this, new SortedLinkedSet<>(sortedSet));

  }
}


