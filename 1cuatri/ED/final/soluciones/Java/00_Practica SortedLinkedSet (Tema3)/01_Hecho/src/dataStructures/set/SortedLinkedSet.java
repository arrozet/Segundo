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
      // todo
      //  An invocation of this constructor should
      //  search for elem in this class sorted linked structure.
      //  Attribute found should be set to true if elem was
      //  found or false otherwise.
      //  At the end of the search, current
      //  should be a reference to the node storing elem
      //  and previous should be a reference to node
      //  before current (or null if elem was found at first node).


      found = false;
      previous = null;
      current = first;

      while(current != null && elem.compareTo(current.elem) > 0)
      {
        previous = current;
        current = current.next;
      }

      if(current != null && elem.equals(current.elem))
      {
        found = true;
      }

    }
  }

  public void insert(T elem) {
    // todo
    //  Implement insert by using Finder.
    //  insert should add a new node for elem
    //  to this class sorted linked structure
    //  if elem is not yet in this set.

    Finder f = new Finder(elem);


    if(f.found)
    {

    } else
    {
      Node<T> nodo = new Node (elem, f.current);

      if(f.previous != null)
      {
        f.previous.next = nodo;
        size++;
      } else
      {
        first = nodo;
        size++;
      }

    }

  }

  public boolean isElem(T elem) {
    // todo
    //  Implement isElem by using Finder.
    //  isElem should return true is elem
    //  is in this class sorted linked structure
    //  or false otherwise.

    Finder f = new Finder(elem);

    return f.found;
  }

  public void delete(T elem) {
    // todo
    //  Implement delete by using Finder.
    //  delete should remove the node containing elem
    //  from this class sorted linked structure
    //  if elem is in this set.

    Finder f = new Finder(elem);

    if (f.found)
    {
      if(f.previous != null)
      {
        f.previous.next = f.current.next;
        size--;
      } else
      {
        first = null;
      }


    } else
    {

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
      // todo
      //   Initialize iterator by making current a reference to first node
      current = first;
    }

    public boolean hasNext() {
      // todo
      //  Check if all elements have already been returned by this iterator
      return current != null;
    }

    public T next() {
      // todo
      //  Check if there are still more elements to be returned (raise
      //  NoSuchElementException otherwise), return next element and
      //  advance iterator to next node for next iteration.
      if (!hasNext()) {
        throw new NoSuchElementException("No more elements in the set");
      }

      T result = current.elem; // Get the element of the current node
      current = current.next; // Move the iterator to the next node
      return result;
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
    // todo
    //  Implement this copy constructor using a SortedLinkedSetBuffer
    SortedLinkedSetBuffer<T> s = new SortedLinkedSetBuffer<>();

    for(T x : sortedSet)
    {
      s.append(x);
    }

    SortedLinkedSet<T> sol = s.toSortedLinkedSet();

    this.first = sol.first;
    this.size = sol.size;

  }

  public static <T extends Comparable<? super T>>
  SortedLinkedSet<T> union(SortedLinkedSet<T> set1, SortedLinkedSet<T> set2) {
    // todo Should compute a new SortedLinkedSet including all elements which are
    //      in set1 or in set2.
    //      Neither set1 nor set2 should be modified.
    //      Implement this method by using a SortedLinkedSetBuffer.
    SortedLinkedSet<T> s = new SortedLinkedSet<>();

    for(T x : set1)
    {
      s.insert(x);
    }

    for(T x : set2)
    {
      s.insert(x);
    }


    return s;
  }

  public static <T extends Comparable<? super T>>
  SortedLinkedSet<T> intersection(SortedLinkedSet<T> set1, SortedLinkedSet<T> set2) {
    // todo Should compute a new SortedLinkedSet including only common elements in
    //      set1 and in set2.
    //      Neither set1 nor set2 should be modified.
    //      Implement this method by using a SortedLinkedSetBuffer.

    SortedLinkedSet<T> result = new SortedLinkedSet<>();
    Node<T> current1 = set1.first;
    Node<T> current2 = set2.first;

    while (current1 != null && current2 != null) {

      int comparison = current1.elem.compareTo(current2.elem);

      if (comparison == 0) {
        // Elements are equal, add to the result and move both pointers
        result.insert(current1.elem);
        current1 = current1.next;
        current2 = current2.next;

      } else if (comparison < 0) {
        // Element in set1 is smaller, move pointer in set1

        current1 = current1.next;

      } else {
        // Element in set2 is smaller, move pointer in set2
        current2 = current2.next;
      }
    }


    return result;
  }

  public static <T extends Comparable<? super T>>
  SortedLinkedSet<T> difference(SortedLinkedSet<T> set1, SortedLinkedSet<T> set2) {
    // todo Should compute a new SortedLinkedSet including all elements in
    //      set1 which are not in set2.
    //      Neither set1 nor set2 should be modified.
    //      Implement this method by using a SortedLinkedSetBuffer.
    SortedLinkedSet<T> result = new SortedLinkedSet<>();
    Node<T> current1 = set1.first;
    Node<T> current2 = set2.first;

    while (current1 != null) {
      if (current2 == null || current1.elem.compareTo(current2.elem) < 0) {
        // Element in set1 is not in set2, add to the result
        result.insert(current1.elem);
        current1 = current1.next;
      } else if (current1.elem.compareTo(current2.elem) == 0) {
        // Element in set1 is also in set2, move both pointers
        current1 = current1.next;
        current2 = current2.next;
      } else {
        // Element in set2 is smaller, move pointer in set2
        current2 = current2.next;
      }
    }

    return result;
  }

  public void union(SortedSet<T> sortedSet) {
    // todo Should modify this set so that it becomes the union of
    //  this set and parameter sortedSet.
    //  Parameter sortedSet should not be modified.
    //  Should reuse current nodes in this set and should create new nodes for
    //  elements copied from sortedSet.

    for(T x : sortedSet)
    {
      insert(x);
    }

  }
}


