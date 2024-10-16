/**
 * Student's name:
 *
 * Student's group:
 */

import dataStructures.list.ArrayList;
import dataStructures.list.List;


class Bin {
    private int remainingCapacity; // capacity left for this bin
    private List<Integer> weights; // weights of objects included in this bin

    public Bin(int initialCapacity) {
        // todo

        remainingCapacity = initialCapacity;
        weights = new ArrayList<>();
    }

    // returns capacity left for this bin
    public int remainingCapacity() {
        // todo
        return remainingCapacity;
    }

    // adds a new object to this bin
    public void addObject(int weight) {
        // todo

       /*if(weight > remainingCapacity)
        {
            throw new RuntimeException("El objeto no cabe en el cubo");
        }*/

        weights.append(weight);
        remainingCapacity = remainingCapacity - weight;

    }

    // returns an iterable through weights of objects included in this bin
    public Iterable<Integer> objects() {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        sb.append(remainingCapacity);
        sb.append(", ");
        sb.append(weights.toString());
        sb.append(")");
        return sb.toString();
    }
}

// Class for representing an AVL tree of bins
public class AVL {
    static private class Node {
        Bin bin; // Bin stored in this node
        int height; // height of this node in AVL tree
        int maxRemainingCapacity; // max capacity left among all bins in tree rooted at this node
        Node left, right; // left and right children of this node in AVL tree

        // recomputes height of this node
        void setHeight() {
            // todo
            if(left == null && right == null)
            {
                height = 1;
            } else if (left == null && right != null)
            {
                height = right.height+1;
            } else if (left != null && right == null)
            {
                height = left.height+1;
            } else
            {
                if(left.height > right.height)
                {
                    height = left.height+1;
                } else
                {
                    height = right.height+1;
                }
            }
        }

        // recomputes max capacity among bins in tree rooted at this node
        void setMaxRemainingCapacity() {
            // todo

            if(left == null && right == null)
            {
                maxRemainingCapacity = bin.remainingCapacity();
            } else if (left == null && right != null)
            {
              maxRemainingCapacity = Math.max(bin.remainingCapacity(), right.maxRemainingCapacity);
            } else if (left != null && right == null)
            {
                maxRemainingCapacity = Math.max(bin.remainingCapacity(), left.maxRemainingCapacity);
            } else
            {
                maxRemainingCapacity = Math.max(bin.remainingCapacity(), Math.max(left.maxRemainingCapacity, right.maxRemainingCapacity));
            }

        }

        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {
            // todo

            Node aux = this.right;
            this.right = aux.left;

            this.setMaxRemainingCapacity();
            this.setHeight();

            aux.left = this;

            aux.setMaxRemainingCapacity();
            aux.setHeight();

            return aux;
        }
    }

    private static int height(Node node) {
        // todo
        return node.height;
    }

    private static int maxRemainingCapacity(Node node) {
        // todo
        return node.maxRemainingCapacity;
    }

    private Node root; // root of AVL tree

    public AVL() {
        this.root = null;
    }

    // adds a new bin at the end of right spine.
    private void addNewBin(Bin bin) {
        // todo
        root = addNewBinRec(root,bin);
    }

    private Node addNewBinRec(Node nodo, Bin bin) {

        if(nodo == null)
        {
            Node nuevoNodo = new Node();
            nuevoNodo.bin = bin;
            nuevoNodo.setHeight();
            nuevoNodo.setMaxRemainingCapacity();
            nodo = nuevoNodo;
        } else
        {
            nodo.right = addNewBinRec(nodo.right, bin);
        }

        nodo.setHeight();
        nodo.setMaxRemainingCapacity();

        if(nodo.left != null && nodo.right != null)
        {
            if(nodo.left.height+1 < nodo.right.height)
            {
                nodo.rotateLeft();
            }
        }

        nodo.setHeight();
        nodo.setMaxRemainingCapacity();

        return nodo;

    }

    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
        // todo

        addFirstRec(root, initialCapacity, weight);
    }

    private void addFirstRec(Node nodo, int initialCapacity, int weight) {

        if(nodo == null || nodo.maxRemainingCapacity < weight)
        {
            Bin b = new Bin(initialCapacity);
            b.addObject(weight);
            addNewBin(b);
        } else if (nodo.left != null && nodo.left.maxRemainingCapacity >= weight)
        {
            addFirstRec(nodo.left, initialCapacity, weight);
        } else if(nodo.bin.remainingCapacity() >= weight)
        {
            nodo.bin.addObject(weight);
        } else
        {
            addFirstRec(nodo.right, initialCapacity, weight);
        }
    }

    public void addAll(int initialCapacity, int[] weights) {
        // todo

        for(int i = 0; i<weights.length; i++)
        {
            addFirst(initialCapacity, weights[i]);
        }

    }



    public List<Bin> toList() {
        // todo

        List<Bin> l = new ArrayList<>();

        toListRec(root, l);

        return l;
    }

    private void toListRec(Node nodo, List<Bin> l) {

        if(nodo != null)
        {

            if(nodo.left != null)
            {
                toListRec(nodo.left,l);
            }

            l.append(nodo.bin);

            if(nodo.right != null)
            {
                toListRec(nodo.right, l);
            }
        }



    }

    public String toString() {
        String className = getClass().getSimpleName();
        StringBuilder sb = new StringBuilder(className);
        sb.append("(");
        stringBuild(sb, root);
        sb.append(")");
        return sb.toString();
    }

    private static void stringBuild(StringBuilder sb, Node node) {
        if(node==null)
            sb.append("null");
        else {
            sb.append(node.getClass().getSimpleName());
            sb.append("(");
            sb.append(node.bin);
            sb.append(", ");
            sb.append(node.height);
            sb.append(", ");
            sb.append(node.maxRemainingCapacity);
            sb.append(", ");
            stringBuild(sb, node.left);
            sb.append(", ");
            stringBuild(sb, node.right);
            sb.append(")");
        }
    }
}

class LinearBinPacking {
    public static List<Bin> linearBinPacking(int initialCapacity, List<Integer> weights) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;
    }
	
	public static Iterable<Integer> allWeights(Iterable<Bin> bins) {
        // todo
        //  SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
        //  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
        return null;		
	}
}