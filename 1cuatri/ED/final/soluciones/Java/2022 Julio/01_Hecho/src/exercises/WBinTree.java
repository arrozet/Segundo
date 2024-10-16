/* ------------------------------------------------------------------------------
   -- Student's name:
   -- Student's group:
   -- Identity number (DNI if Spanish/passport if Erasmus):
   --
   -- Data Structures. Grado en Informática. UMA.
   -------------------------------------------------------------------------------
*/

package exercises;

public class WBinTree<T> {

    private static class Node<E> {
        private E item;
        private int weight;
        private Node<E> left, right;

        Node(E x, int w, Node<E> lt, Node<E> rt) {
            item= x;
            weight = w;
            left= lt;
            right = rt;
        }
    }

    private Node<T> root;

    public WBinTree() {
        root = null;
    }

    // -- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
    // -- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
    // -- EXERCISE 4

    public boolean isWeightBalanced() {
        // TODO
        return true;
    }

    public void insert(T x) {
        // TODO
    }

    // -- DO NOT MODIFY CODE BELOW -----------------------------------------------------------
    // -- NO MODIFIQUES DEBAJO ---------------------------------------------------------------

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        toStringRec(root, sb);
        return sb.toString();
    }

    private static <T> void toStringRec(Node<T> node, StringBuffer sb) {
        if (node == null) {
            sb.append("null");
        }
        else {
            sb.append(String.format("Node(%d, %s, ", node.weight, node.item));
            toStringRec(node.left, sb);
            sb.append(", ");
            toStringRec(node.right, sb);
            sb.append(")");
        }
    }
}
