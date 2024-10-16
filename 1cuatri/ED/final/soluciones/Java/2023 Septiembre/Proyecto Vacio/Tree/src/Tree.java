/******************************************************************************
 * Student's name: ?????????????????????????????????????
 * Identity number (DNI if Spanish/passport if Erasmus): ???????????????????
 * Student's group: ?
 * PC code: ???
 *
 * Data Structures. Grados en Informatica. UMA.
 *****************************************************************************/

public class Tree<E extends Comparable<? super E>> {

    private static class Node<K> {
        K value;
        Node<K> left;
        Node<K> right;

        public Node(K e) {
            value = e;
            left = null;
            right = null;
        }
    }

    private Node<E> root;

    // returns the tree rotated to the left at node
    private Node<E> rotateLeft(Node<E> node) {
        if (node == null || node.right == null) {
            return node;
        } else {
            Node<E> rt = node.right;
            node.right = rt.left;
            rt.left = node;
            return rt;
        }
    }

    // returns the tree rotated to the right at node
    private Node<E> rotateRight(Node<E> node) {
        if (node == null || node.left == null)
            return node;
        else {
            Node<E> lt = node.left;
            node.left = lt.right;
            lt.right = node;
            return lt;
        }
    }

    // DO NOT MODIFY CODE ABOVE

    // | = Exercise k - makeRoot

    public void makeRoot(E x) {
        // todo
    }

    // DO NOT MODIFY CODE BELOW

    // code to build a binary search tree

    public Tree() {
        root = null;
    }

    public void insert(E e) {
        root = insertRec(root, e);
    }

    private Node<E> insertRec(Node<E> node, E value) {
        if (node == null) {
            node = new Node<>(value);
        } else if (value.compareTo(node.value) < 0) {
            node.left = insertRec(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = insertRec(node.right, value);
        } else {
            node.value = value;
        }
        return node;
    }

    // code to print a binary search tree

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), toStringRec(this.root));
    }

    private String toStringRec(Node<E> node) {
        return node == null ? "null" : "Node<" + toStringRec(node.left) + ","
                + node.value + "," + toStringRec(node.right) + ">";
    }
}
