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
    return node==null ? 0 : node.size;
  }

  // Merges two heaps. Returns merged heap.
  // Parameters are references to roots of heaps that should be merged.
  // Result should be a reference to root of resulting merged heap.
  private static <T extends Comparable<? super T>>
          Node<T> merge(Node<T> h1, Node<T> h2) {
    if (h1 == null) { // para evitar nullPointerException
      return h2;
    }
    if (h2 == null) {
      return h1;
    }
    // Con el fin de hacerlo más eficiente en términos de memoria, reusaré nodos en lugar de crear uno nuevo

    // en lugar de crear un nodo auxiliar, debo reusar los nodos
//    Node<T> output = new Node<T>(); // lo pongo fuera para que no de error
//    if(h2.elem.compareTo(h1.elem)<=0){ // forzamos h1 clave menor
//      output.elem = h2.elem;
//      output.size = h1.size + h2.size;
//
//      // tengo que considerar mas nulls para que esto no de error (los del size de los hijos)
//      // uso la funcion size() en lugar del atributo
//      if(size(h1) >= size(h2.left) && size(h1) >= size(h2.right)){  // h1 es el mas grande
//        output.left = h1;
//        output.right = merge(h2.left, h2.right);
//      }
//      else if(size(h2.left) >= size(h1) && size(h2.left) >= size(h2.right)){  // h2.left es el mas grande
//        output.left = h2.left;
//        output.right = merge(h1, h2.right);
//      }
//      else{ // h2.right es el mas grande
//        output.left = h2.right;
//        output.right = merge(h1, h2.left);
//      }
//    }
//    else{ // le doy la vuelta, asi me ahorro poner dos compareTo
//      output = merge(h2,h1);
//    }
//    return output;

    if(h2.elem.compareTo(h1.elem)<=0){ // forzamos h2 clave menor
//      output.elem = h2.elem;

      if(size(h1) >= size(h2.left) && size(h1) >= size(h2.right)){  // h1 es el mas grande
        h2.right = merge(h2.left, h2.right);
        h2.left = h1;
        // EL ORDEN SÍ IMPORTA SI REUSAS NODOS,
        // SI CAMBIAS EL ORDEN DE ESTAS LINEAS, DA STACKOVERFLOW
        // PORQUE EL MERGE YA LO ESTARIA HACIENDO UN h2.left DISTINTO (lo estaria haciendo de h1 y h2.right)
      }
      else if(size(h2.left) >= size(h1) && size(h2.left) >= size(h2.right)){  // h2.left es el mas grande
//        h2.left = h2.left;
        h2.right = merge(h1, h2.right);
      }
      else{ // h2.right es el mas grande
        Node<T> aux = h2.right;
        h2.right = merge(h1, h2.left);
        h2.left = aux;
        // LO MISMO DE ARRIBA AQUÍ -> SI CAMBIO EL ORDEN, EL MERGE LO HAGO CON H2.RIGHT EN VEZ DE H2.LEFT
      }
      h2.size = h1.size+h2.size;
    }
    else{ // le doy la vuelta, asi me ahorro poner dos compareTo
      return merge(h2,h1);
    }
    return h2;


  }

  // Constructor for MaxiphobicHeap class. Creates an empty Maxiphobic heap
  public MaxiphobicHeap() {
    root = null;
  }

  // Returns true if this Maxiphobic heap is empty
  public boolean isEmpty() {
    return root == null;
  }

  // Returns total number of elements in this Maxiphobic heap
  public int size() {
    // todo
    return root==null ? 0 : root.size;
  }

  // Returns minimum element in this Maxiphobic heap
  public T minElem() {
    // todo
    if(isEmpty()) {
      throw new EmptyHeapException("minElem on empty heap");
    }
    return root.elem;
  }

  // Removes minimum element from this Maxiphobic heap
  public void delMin() {
    if(isEmpty()) {
      throw new EmptyHeapException("delMin on empty heap");
    }
    else{
      root = merge(root.left, root.right);
    }


  }

  // insert new element in this Maxiphobic heap
  public void insert(T elem) {
    // todo
    Node<T> m = new Node<>();
    m.size = 1;
    m.elem = elem;

    // merge crea un nuevo nodo, por lo que tengo que cambiar el pointer de la raiz
    root = merge(root,m);
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