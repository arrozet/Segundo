/**
 * Student's name:
 *
 * Student's group:
 */

import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.list.LinkedList;

import java.util.Iterator;


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

        if (weight > remainingCapacity)
        {
            throw new RuntimeException("El cubo no cabe");
        }

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

            } else if (left != null & right == null)
            {
                height = left.height+1;
            } else
            {
                height = Math.max(left.height, right.height)+1;
            }

        }

        // recomputes max capacity among bins in tree rooted at this node
        void setMaxRemainingCapacity() {
            // todo

            if(left == null && right == null)
            {
                maxRemainingCapacity = bin.remainingCapacity();
            } else if (left != null && right == null)
            {
                maxRemainingCapacity = Math.max(bin.remainingCapacity(), left.maxRemainingCapacity);
            } else if ( left == null && right != null)
            {
                maxRemainingCapacity = Math.max(bin.remainingCapacity(), right.maxRemainingCapacity);
            } else
            {
                maxRemainingCapacity = Math.max( bin.remainingCapacity(), Math.max(right.maxRemainingCapacity, left.maxRemainingCapacity) );
            }

        }

        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {
            // todo

            Node rootBueno = right; //root a devolver
            this.right = rootBueno.left;
            rootBueno.left = this;

            this.setMaxRemainingCapacity();
            this.setHeight();

            rootBueno.setHeight();
            rootBueno.setMaxRemainingCapacity();

            return rootBueno;
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

        root = addnewBinRec(bin, root);

    }

    private Node addnewBinRec (Bin bin, Node nodo)
    {
        if(nodo == null)
        {
            Node nuevoNodo = new Node();
            nuevoNodo = new Node();
            nuevoNodo.bin = bin;
            nuevoNodo.maxRemainingCapacity = bin.remainingCapacity();
            nuevoNodo.height = 1;

            return nuevoNodo;

        } else
        {
            nodo.right = addnewBinRec(bin, nodo.right);

            if(nodo.left != null && nodo.right != null)
            {
                if(nodo.left.height+1 < nodo.right.height)
                {
                    nodo.rotateLeft();
                }
            }
            nodo.setMaxRemainingCapacity();
            nodo.setHeight();

            return nodo;
        }


    }

    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
        // todo
        addFirstRec(initialCapacity, weight, root);
    }

    public void addFirstRec(int initialCapacity, int weight, Node nodo) {
        // todo

        if(nodo == null || weight > nodo.maxRemainingCapacity)
        {
            Bin bin = new Bin (initialCapacity);
            bin.addObject(weight);
            addNewBin(bin);
        } else if (nodo.left != null && nodo.left.maxRemainingCapacity >= weight)
        {
            addFirstRec(initialCapacity, weight, nodo.left);
        } else if (nodo.bin.remainingCapacity() >= weight)
        {
            nodo.bin.addObject(weight);
        } else
        {
            addFirstRec(initialCapacity, weight, nodo.right);
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
        enOrden(l, root);

        return l;
    }

    private void enOrden (List<Bin> l, Node nodo)
    {

        if(nodo != null)
        {
            System.out.println(nodo.bin);
            if(nodo.left != null)
            {
                enOrden(l, nodo.left);
            }

            l.append(nodo.bin);

            if(nodo.right != null)
            {
                enOrden(l, nodo.right);
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