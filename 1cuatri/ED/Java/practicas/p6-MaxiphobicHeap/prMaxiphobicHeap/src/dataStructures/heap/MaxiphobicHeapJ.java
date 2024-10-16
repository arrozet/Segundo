/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Maxiphobic Heaps
 */

package dataStructures.heap;

public class MaxiphobicHeapJ<T extends Comparable<? super T>> implements	Heap<T> {

  // A node for an augmented binary tree
  private static class Node<E> {
    private E elem;        // the element
    private int size;      // the weight of tree rooted at this node
    private Node<E> left;  // left child (null if no left child)
    private Node<E> right; // right child  (null if no right child)
  }

  // Attribute for MaxiphobicHeap class
  private Node<T> root; // reference to root node of this Maxiphobic heap.
                        // null is heap is empty


  // Returns number of elements in tree stored at node
  private static int size(Node<?> node) {
    if (node == null){
      return 0;
    } else {
      return node.size;
    }
  }

  // Merges two heaps. Returns merged heap.
  // Parameters are references to roots of heaps that should be merged.
  // Result should be a reference to root of resulting merged heap.
  private static <T extends Comparable<? super T>>
          Node<T> merge(Node<T> h1, Node<T> h2) {
    if (h1 == null){
      return h2;
    }
    if (h2 == null){
      return h1;
    }
    if (h2.elem.compareTo(h1.elem) < 0){
      Node <T> aux = h1;
      h1 = h2;
      h2 = aux;
    }
    if (size(h2) >= size(h1.left) && h2.size >= size(h1.right)){
      h1.right = merge(h1.right,h1.left);
      h1.left = h2;
    } else if (size(h1.left) >= h2.size && size(h1.left) >= size(h1.right)){
      h1.right = merge(h2,h1.right);
    } else {
      Node<T> aux = h1.right;
      h1.right = merge(h1.left,h2);
      h1.left = aux;
    }
    h1.size = h1.size+ h2.size;
    return h1;
  }

  // Constructor for MaxiphobicHeap class. Creates an empty Maxiphobic heap
  public MaxiphobicHeapJ() {
    root = null;
  }

  // Returns true if this Maxiphobic heap is empty
  public boolean isEmpty() {
    return root == null;
  }

  // Returns total number of elements in this Maxiphobic heap
  public int size() {
    if (isEmpty()){
      return 0;
    } else {
      return size(root);
    }
  }

  // Returns minimum element in this Maxiphobic heap
  public T minElem() {
    if (isEmpty()){
      throw new EmptyHeapException("el árbol esta vacio");
    } else {
      return root.elem;
    }
  }

  // Removes minimum element from this Maxiphobic heap
  public void delMin() {
    root = merge(root.left, root.right);
  }

  // insert new element in this Maxiphobic heap
  public void insert(T elem) {
    Node<T> aux = new Node<>();
    aux.elem = elem;
    aux.size = 1;
    root = merge(root,aux);
  }


  /**
   * Returns representation of this Maxiphobic heap as a String.
   */
  @Override public String toString() {
    String className = getClass().getSimpleName();
    StringBuilder sb = new StringBuilder();
    sb.append(className);
    sb.append("(");
    toStringRec(sb, root);
    sb.append(")");

    return sb.toString();
  }

  private static void toStringRec(StringBuilder sb, Node<?> node) {
    if(node == null) {
      sb.append("null");
    } else {
      sb.append("Node(");
      toStringRec(sb, node.left);
      sb.append(", ");
      sb.append(node.elem);
      sb.append(", ");
      toStringRec(sb, node.right);
      sb.append(")");
    }
  }
}