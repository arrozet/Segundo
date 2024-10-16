/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Maxiphobic Heaps
 */

package dataStructures.heap;

public class MaxiphobicHeap<T extends Comparable<? super T>> implements	Heap<T> {

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
    // todo
    return node.size;
  }

  // Merges two heaps. Returns merged heap.
  // Parameters are references to roots of heaps that should be merged.
  // Result should be a reference to root of resulting merged heap.
  private static <T extends Comparable<? super T>>
          Node<T> merge(Node<T> h1, Node<T> h2) {
    // todo

    int max = 0;
    Node<T> nodo = new Node<>();

    if (h1 == null) {
      return h2;
    } else if (h2 == null) {
      return h1;

    } else {

      nodo.size = h1.size + h2.size;

      if (h1.elem.compareTo(h2.elem) <= 0) {

        max = Math.max(h1.left != null ? h1.left.size : 0, Math.max(h1.right != null ? h1.right.size : 0, h2.size));
        int h1lefttam = h1.left != null ? h1.left.size : 0;
        int h1righttam = h1.right != null ? h1.right.size : 0;
        int h2tam = h2.size;

        if(h1lefttam == max)
        {
          nodo.elem = h1.elem;
          nodo.left = h1.left;
          nodo.right = merge(h1.right, h2);

        } else if (h1righttam == max)
        {
          nodo.elem = h1.elem;
          nodo.left = h1.right;
          nodo.right = merge(h1.left, h2);
        } else
        {
          nodo.elem = h1.elem;
          nodo.left = h2;
          nodo.right = merge(h1.left, h1.right);
        }


      } else
      {
        max = Math.max(h2.left != null ? h2.left.size : 0, Math.max(h2.right != null ? h2.right.size : 0, h1.size));
        int h2lefttam = h2.left != null ? h2.left.size : 0;
        int h2righttam = h2.right != null ? h2.right.size : 0;
        int h1tam = h1.size;

        if(h2lefttam == max)
        {
          nodo.elem = h2.elem;
          nodo.left = h2.left;
          nodo.right = merge(h2.right, h1);
        } else if (h2righttam == max)
        {
          nodo.elem = h2.elem;
          nodo.left = h2.right;
          nodo.right = merge(h2.left, h1);
        } else
        {
          nodo.elem = h2.elem;
          nodo.left = h1;
          nodo.right = merge(h2.left, h2.right);
        }

      }

    }
    return nodo;
  }







  // Constructor for MaxiphobicHeap class. Creates an empty Maxiphobic heap
  public MaxiphobicHeap() {
    root = new Node();
    root.size = 0;
  }

  // Returns true if this Maxiphobic heap is empty
  public boolean isEmpty() {
    return root == null;
  }

  // Returns total number of elements in this Maxiphobic heap
  public int size() {
    // todo
    return root.size;
  }

  // Returns minimum element in this Maxiphobic heap
  public T minElem() {
    // todo
    return root.elem;
  }

  // Removes minimum element from this Maxiphobic heap
  public void delMin() {
    // todo

    root = merge(root.left, root.right);

  }

  // insert new element in this Maxiphobic heap
  public void insert(T elem) {
    // todo

    Node<T> nodo = new Node<>();
    nodo.elem = elem;
    nodo.size = 1;
    nodo.right = null;
    nodo.left = null;


    if(root == null)
    {
      root = nodo;
    } else
    {
      root = merge(root, nodo);
    }



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