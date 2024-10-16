/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package dataStructures.vector;

import dataStructures.list.ArrayList;
import dataStructures.list.List;

public class TreeVector<T> {

    private final int size;
    private final Tree<T> root;

    private interface Tree<E> {
        E get(int index);

        void set(int index, E x);

        List<E> toList();
    }

    private static class Leaf<E> implements Tree<E> {
        private E value;

        private Leaf(E x) {
            value = x;
        }

        @Override
        public E get(int index) {
        	//to do
            return value;
        }

        @Override
        public void set(int i, E x) {
        	//to do

            value = x;

        }

        @Override
        public List<E> toList() {
        	//to do

            List<E> l = new ArrayList<>();
            l.append(value);

            return l;
        }
    }

    private static class Node<E> implements Tree<E> {
        private Tree<E> left;
        private Tree<E> right;

        private Node(Tree<E> l, Tree<E> r) {
            left = l;
            right = r;
        }

        @Override
        public E get(int index) {
        	//to do

            if(index % 2 == 0)
            {
                return left.get(index/2);
            } else
            {
                return right.get(index/2);
            }

        }

        @Override
        public void set(int index, E x) {
        	//to do

            if(index % 2 == 0)
            {
                left.set(index/2, x);
            } else
            {
                right.set(index/2, x);
            }
        }

        @Override
        public List<E> toList() {
        	//to do

            return intercalate(left.toList(), right.toList());
        }
    }

    public TreeVector(int n, T value) {
    	//to do

        size = (int) Math.pow(2,n);
        root = crearArbol (n, value);


    }

    private Tree<T> crearArbol(int n, T value) {

        if(n == 0)
        {
            return new Leaf<>(value);
        } else
        {
            return new Node<>( crearArbol((n-1), value), crearArbol((n-1), value) );
        }

    }

    public int size() {
    	//to do
        return size;
    }

    public T get(int i) {
    	//to do

        if(i < 0 || i > size)
        {
            throw new VectorException("Indice fuera de rango");
        }

        return root.get(i);
    }

    public void set(int i, T x) {
    	//to do
        if(i < 0 || i > size)
        {
            throw new VectorException("Indice fuera de rango");
        }

        root.set(i, x);

    }

    public List<T> toList() {
    	//to do
        return root.toList();
    }

    protected static <E> List<E> intercalate(List<E> xs, List<E> ys) {
    	//to do

        List<E> l = new ArrayList<>();
        List<E> xs2 = new ArrayList<>();
        List<E> ys2 = new ArrayList<>();

        for(int i = 0; i<xs.size(); i++)
        {
            xs2.append(xs.get(i));
        }

        for(int i = 0; i<ys.size(); i++)
        {
            ys2.append(ys.get(i));
        }

        while(!xs2.isEmpty() && !ys2.isEmpty())
        {
            l.append(xs2.get(0));
            l.append(ys2.get(0));
            xs2.remove(0);
            ys2.remove(0);
        }

        return l;
    }

    
    // Only for students not taking continuous assessment

    static protected boolean isPowerOfTwo(int n) {
    	//to do

        if(n < 2)
        {
            return false;
        } else if (n == 2)
        {
            return true;
        } else if (n%2 == 0)
        {
            return isPowerOfTwo (n/2);
        } else
        {
            return false;
        }

    }

    public static <E> TreeVector<E> fromList(List<E> l) {
    	//to do
        if(!isPowerOfTwo(l.size()))
        {
            throw new VectorException("La lista no es potencia de 2");
        }

        double tamd = (Math.log(l.size() )  / (Math.log(2)) );
        int tam = (int) tamd;

        TreeVector<E> v = new TreeVector<>(tam, l.get(0));

        for(int i = 0; i<l.size(); i++)
        {
            v.set(i, l.get(i));
        }

        return v;
    }
}
