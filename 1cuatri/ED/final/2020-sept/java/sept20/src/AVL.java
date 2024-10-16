/**
 * Student's name:
 *
 * Student's group:
 */

import dataStructures.list.List;
import dataStructures.list.LinkedList;

import java.util.Arrays;
import java.util.Iterator;


class Bin {
    private int remainingCapacity; // capacity left for this bin
    private List<Integer> weights; // weights of objects included in this bin

    public Bin(int initialCapacity) {
        weights = new LinkedList<>();
        remainingCapacity = initialCapacity;
    }

    // returns capacity left for this bin
    public int remainingCapacity() {
        return remainingCapacity;
    }

    // adds a new object to this bin
    public void addObject(int weight) {
        if(weight > remainingCapacity){
            throw new IllegalArgumentException("addObject: the object cannot be contained in the bin");
        }
        weights.insert(0,weight);
        remainingCapacity-=weight;
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
            if(height(left)>height(right)){
                height = height(left)+1;
            }
            else{
                height = height(right)+1;
            }
        }

        // recomputes max capacity among bins in tree rooted at this node
        void setMaxRemainingCapacity() {
            int c1 = bin.remainingCapacity();
            int c2 = maxRemainingCapacity(left);
            int c3 = maxRemainingCapacity(right);

            if(c1>=c2 && c1 >= c3){
                maxRemainingCapacity = c1;
            }
            else if(c2>=c1 && c2 >= c3){
                maxRemainingCapacity = c2;
            }
            else{
                maxRemainingCapacity = c3;
            }
        }

        // left-rotates this node. Returns root of resulting rotated tree
        Node rotateLeft() {
            Node rt = this.right;
            Node rlt = rt.left;

            this.right = rlt;
            this.setHeight();
            this.setMaxRemainingCapacity();

            rt.left = this;
            rt.setHeight();
            rt.setMaxRemainingCapacity();

            return rt;
        }
    }

    private static int height(Node node) {
        if(node==null){
            return 0;
        }
        return node.height;
    }

    private static int maxRemainingCapacity(Node node) {
        if(node==null){
            return 0;
        }
        return node.maxRemainingCapacity;
    }

    private Node root; // root of AVL tree

    public AVL() {
        this.root = null;
    }

    // adds a new bin at the end of right spine.
    private void addNewBin(Bin bin) {
        root = addNewBinRec(bin,root);
    }

    private Node addNewBinRec(Bin bin, Node root){
        if(root==null){
            root = new Node();

            root.bin = bin;
            root.setHeight();
            root.setMaxRemainingCapacity();
            return root;

        }
        else if(root.left != null && root.right !=null && Math.abs(height(root.left) - height(root.right)) > 1){
            root.right = addNewBinRec(bin, root.right);
            root.rotateLeft();
            return root;
        }
        else{
            root.right = addNewBinRec(bin,root.right);

            root.setHeight();
            root.setMaxRemainingCapacity();
            return root;
        }

    }

    // adds an object to first suitable bin. Adds
    // a new bin if object cannot be inserted in any existing bin
    public void addFirst(int initialCapacity, int weight) {
        root = addFirstRec(initialCapacity, weight, root);
    }

    private Node addFirstRec(int initialCapacity, int weight, Node root){
        if(root==null || maxRemainingCapacity(root) < weight){
            Bin b = new Bin(initialCapacity);
            b.addObject(weight);
            root = addNewBinRec(b,root);
            return root;
        }
        else if (maxRemainingCapacity(root.left) >= weight) {
            root.left = addFirstRec(initialCapacity, weight, root.left);
            return root;
        }
        else if (root.bin.remainingCapacity() >= weight) {
            root.bin.addObject(weight);

            root.setHeight();
            root.setMaxRemainingCapacity();

            return root;
        }
        else{
            root.right = addFirstRec(initialCapacity, weight, root.right);
            return root;
        }

    }

    public void addAll(int initialCapacity, int[] weights) {
        for(int w : weights){
            addFirst(initialCapacity, w);
        }
    }

    public List<Bin> toList() {
        List<Bin> l = new LinkedList<>();
        toList(root,l);
        return l;
    }

    private void toList(Node root, List<Bin> sol){
        if(root!=null){
            toList(root.left, sol);
            sol.append(root.bin);
            toList(root.right, sol);
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