/******************************************************************************
 * Student's name:
 * Student's group:
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package dataStructures.vector;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SparseVector<T> implements Iterable<T> {

    protected interface Tree<T> {

        T get(int sz, int i);

        Tree<T> set(int sz, int i, T x);
    }

    // Unif Implementation

    protected static class Unif<T> implements Tree<T> {

        private T elem;

        public Unif(T e) {
            elem = e;
        }

        @Override
        public T get(int sz, int i) {
            // TODO
            return elem;
        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
            // TODO

            if(elem.equals(x))
            {
                return this;
            }

            if(sz == 1)
            {
                return new Unif<>(x);
            } else
            {

                if(i < (sz/2))
                {
                    return new Node<>(set((sz/2), i, x), this);
                } else
                {

                    return new Node<>(this, set((sz/2), i-(sz/2), x));
                }

            }

        }

        @Override
        public String toString() {
            return "Unif(" + elem + ")";
        }
    }

    // Node Implementation

    protected static class Node<T> implements Tree<T> {

        private Tree<T> left, right;

        public Node(Tree<T> l, Tree<T> r) {
            left = l;
            right = r;
        }

        @Override
        public T get(int sz, int i) {
            // TODO

            if(i < sz/2)
            {
                return left.get(sz/2,i);
            } else
            {
                return right.get(sz/2,i-(sz/2));
            }

        }

        @Override
        public Tree<T> set(int sz, int i, T x) {
            // TODO

            if(i < sz/2)
            {
                left = left.set(sz/2,i, x);
            } else
            {
                right = right.set(sz/2,i-(sz/2), x);
            }


            this.simplify();

            return this;
        }

        protected Tree<T> simplify() {
            // TODO


            if(left instanceof Unif<?> && right instanceof Unif<?> && left.get(1,0).equals(right.get(1, 0)))
            {
                    return new Unif(left.get(1,0));
            } else
            {
                return this;
            }

        }

        @Override
        public String toString() {
            return "Node(" + left + ", " + right + ")";
        }
    }

    // SparseVector Implementation

    private int size;
    private Tree<T> root;

    public SparseVector(int n, T elem) {
        // TODO
        if(n < 0)
        {
            throw new VectorException("Indice negativo");
        }

        size = (int) Math.pow(2,n);
        root = new Unif<>(elem);

    }

    public int size() {
        // TODO
        return size;
    }

    public T get(int i) {
        // TODO
        if(i < 0 || i>(size)-1)
        {
            throw new VectorException("Indice fuera de rango");
        }
        return root.get(size, i);
    }

    public void set(int i, T x) {
        // TODO
        if(i < 0 || i>(size)-1)
        {
            throw new VectorException("Indice fuera de rango");
        }

        root.set(size,i,x);
    }

    @Override
    public Iterator<T> iterator() {
        return new IterVector();
    }

    private class IterVector implements Iterator<T> {
        // TODO

        int current;

        public IterVector() {
            current = 0;
        }

        public boolean hasNext() {
            // TODO
            return current < size;
        }

        public T next() {
            // TODO

            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T x = root.get(size, current);
            current++;
            return x;

        }
    }

    @Override
    public String toString() {
        return "SparseVector(" + size + "," + root + ")";
    }
}
