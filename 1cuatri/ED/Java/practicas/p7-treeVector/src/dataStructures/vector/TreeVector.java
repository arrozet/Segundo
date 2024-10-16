/******************************************************************************
 * Student's name: Rubén Oliva Zamora
 * Student's group: A (Ingeniería del Software)
 * Data Structures. Grado en Informática. UMA.
******************************************************************************/

package dataStructures.vector;

import dataStructures.list.ArrayList;
import dataStructures.list.LinkedList;
import dataStructures.list.List;

import java.util.Iterator;

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
            ArrayList<E> l = new ArrayList<>();
            l.append(value);
            return l;
        }
    }

    private static class Node<E> implements Tree<E> {   // el nodo es un tipo de arbol (es un subarbol)
        private Tree<E> left;
        private Tree<E> right;

        private Node(Tree<E> l, Tree<E> r) {
            left = l;
            right = r;
        }

        @Override
        public E get(int index) {
        	if(index%2==0){ // si es par
                // me voy por la izquierda partido por 2
                return left.get(index/2);
            }
            else{   // si es par
                //  me voy por la derecha partido por 2
                return right.get(index/2);
            }
        }

        @Override
        public void set(int index, E x) {
        	//to do
            if(index%2==0){ // si es par
                // me voy por la izquierda partido por 2
                left.set(index/2,x);
            }
            else{   // si es par
                //  me voy por la derecha partido por 2
                right.set(index/2,x);
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
        if(n<0){
            throw new VectorException("n no puede ser negativo");
        }
        size = (int) Math.pow(2,n);
        root = instantiateTreeVector(n,value);
    }

    // este lo he hecho yo
    private Tree<T> instantiateTreeVector(int exp, T value){
        if(exp==0){
            return new Leaf<>(value);
        }
        // esto rellena _todo el vector de values (el mismo value)
        return new Node<>(instantiateTreeVector(exp-1,value),instantiateTreeVector(exp-1,value));
    }

    public int size() {
    	//to do
        if(root==null){
            return 0;
        }
        return size;
    }

    public T get(int i) {
    	//to do
        if(root == null || i < 0 || i >= size()){   // si es null o el indice esta mal
            throw new VectorException("Arbol nulo o indice mal");
        }
        return root.get(i);
    }

    public void set(int i, T x) {
    	//to do
        if(root == null || i < 0 || i >= size()){   // si es null o el indice esta mal
            throw new VectorException("Arbol nulo o indice mal");
        }
        root.set(i,x);
    }

    public List<T> toList() {
    	//to do
        if(root == null){   // si es null o el indice esta mal
            return null;
        }
        return root.toList();
    }

    protected static <E> List<E> intercalate(List<E> xs, List<E> ys) {
    	//to do
        ArrayList<E> l = new ArrayList<>();
        Iterator<E> iteL = xs.iterator();
        Iterator<E> iteR = ys.iterator();

        while(iteL.hasNext() && iteR.hasNext()){
            l.append(iteL.next());
            l.append(iteR.next());
        }
        return l;
    }

    
    // Only for students not taking continuous assessment

    // Dado entero no negativo
    static protected boolean isPowerOfTwo(int n) {
    	if(n==0){
            return false;
        }
        else{
            while(n%2==0){
                n/=2;
            }
            return n==1;
        }
    }

    public static <E> TreeVector<E> fromList(List<E> l) {   // hacer ESTO IMPORTANTE
    	if(!isPowerOfTwo(l.size())){
            throw new VectorException("The list is not power of two");
        }
        else{
            int nivel = (int) (Math.log(l.size()) / Math.log(2));   // no existe log en base 2, asi que tengo que hacer con la formula

            return new TreeVector<>(nivel, aux(l));
        }
    }

    private static <E> Tree<E> aux(List<E> l){
        if(l.size()==1){
            return new Leaf<>(l.get(0));
        }

        LinkedList<E> mitad1 = new LinkedList<>();
        LinkedList<E> mitad2 = new LinkedList<>();

        // mismo que intercalate
        for(int i=0; i<l.size(); i++){
            if(i%2==0){
                mitad1.append(l.get(i));
            }
            else{
                mitad2.append(l.get(i));
            }
        }

        return new Node<>(aux(mitad1), aux(mitad2));
    }

    private TreeVector(int nivel, Tree<T> t){
        size = (int) Math.pow(2,nivel);
        root = t;
    }
//
//    private Tree<T> instantiateTreeVector2(int lvl, List<T> valores, int ini, int fin){
//        if(lvl==0){
//            return new Leaf<>(valores.get(ini));
//        }
//        int mid=ini + (fin - ini) / 2;    // esto se va a la izq
//        return new Node<>(instantiateTreeVector2(lvl-1,valores,ini,mid),instantiateTreeVector2(lvl-1,valores,mid+1,fin));
//    }
}
