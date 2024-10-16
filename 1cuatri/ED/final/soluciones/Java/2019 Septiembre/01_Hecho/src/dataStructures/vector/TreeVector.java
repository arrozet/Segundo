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
    private Tree<T> root;

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

        if(n<0)
        {
            throw new VectorException("No se puede cosntruir con un numero negativo");
        }

        size = (int) Math.pow(2,n);
        root = crearArbol (n, value);

    }

    private Tree<T> crearArbol (int n, T value)
    {

        if (n == 0)
        {
            Tree<T> l = new Leaf(value);
            return l;
        } else
        {
            return new Node(crearArbol ((n-1), value), crearArbol ((n-1), value));
        }

    }

    public int size() {
    	//to do
        return size;
    }

    public T get(int i) {
    	//to do

        if(i > (size-1))
        {
            throw new VectorException("El indice esta fuera de rango");
        }

        return root.get(i);
    }

    public void set(int i, T x) {
    	//to do
        if(i > (size-1))
        {
            throw new VectorException("El indice esta fuera de rango");
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

        while(!xs.isEmpty() && !ys.isEmpty())
        {
            l.append(xs.get(0));
            l.append(ys.get(0));
            xs.remove(0);
            ys.remove(0);
        }

        return l;
    }

    
    // Only for students not taking continuous assessment

    static protected boolean isPowerOfTwo(int n) {
    	//to do

        boolean potencia = true;

        while(n > 2 && potencia)
        {
            if( n % 2 == 0)
            {
                n = n/2;
            } else
            {
                potencia = false;
            }
        }

        if(n < 2)
        {
            potencia = false;
        }

        return potencia;
    }

    public static <E> TreeVector<E> fromList(List<E> l) {
    	//to do

        if(!isPowerOfTwo(l.size()))
        {
            throw new VectorException("No es potencia de 2");
        }

        TreeVector<E> vec = new TreeVector<>((int)Math.sqrt(l.size()), l.get(0));

        vec.root = construirArbolLista (l);


        return vec;
    }

    private static <E> Tree<E> construirArbolLista(List<E> l) {

        if(l.size() == 1)
        {
            return new Leaf(l.get(0));
        } else
        {
            List<E> l1 = new ArrayList<>();
            List<E> l2 = new ArrayList<>();
            List<E> lC = new ArrayList<>();

            l1 = dividirListasPrim(l);
            l2 = dividirListasSec(l);

            lC = intercalate(l1, l2);

            l1 = dividirListasPrim(lC);
            l2 = dividirListasSec(lC);

            return new Node ( ( construirArbolLista (l2) ), ( construirArbolLista (l1) ) );
        }


    }

    private static <E> List<E> dividirListasSec(List<E> l) {
        List<E> l1 = new ArrayList<>();
        for(int i = 0; i<(l.size()/2); i++)
        {
            l1.append(l.get(i));
        }
        return l1;
    }

    private static <E> List<E> dividirListasPrim(List<E> l) {
        List<E> l2 = new ArrayList<>();
        for(int i = (l.size()/2); i<l.size(); i++)
        {
            l2.append(l.get(i));
        }
        return l2;
    }


}
