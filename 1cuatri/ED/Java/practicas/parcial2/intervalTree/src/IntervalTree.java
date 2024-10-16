//package dataStructures.interval;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
public class IntervalTree {
    private static class Node{
        Interval interval;
        int max;
        Node lt, rt;
        /* (1.25 ptos) Constructor de Node
         */
        public Node (Interval interval, Node lt, Node rt){
            this.interval = interval;
            this.lt = lt;
            this.rt = rt;
            this.max = maxRec(interval.high(), lt, rt);
        }
        private int maxRec(int max, Node lt, Node rt){
            if(lt == null && rt == null){
                return max;
            }
            else if (lt==null){
                return Math.max(max,rt.max);
            }
            else if(rt==null){
                return Math.max(max,lt.max);
            }
            else{
                return Math.max(max,Math.max(rt.max,lt.max));
            }
        }
        public Node(Interval interval) {
            this(interval,null, null);
        }
    }
    /* Atributos de IntervalTree */
    Node root; //raíz del árbol
    int size; //número de intervalos almacenados
    /* (0.75 pto) Constructor del árbol de intervalo vacío
     */
    public IntervalTree(){
        root = null;
        size = 0;
    }
    /* (0.5 ptos) Devuelve true si el árbol de intervalo está vacío
     */
    public boolean isEmpty() {
        return root == null || size == 0;
    }
    /* (0.5 ptos) Devuelve el número de intervalos almacenados en el
   árbol de intervalo
    */
    public int size() {
        return size;
    }
 /* (2 ptos) Inserta el intervalo x en el árbol de intervalos.
 El árbol de intrevalo resultante debe mantener la propiedad de
orden.
 Es similar a un BST: los nodos del árbol están están ordenados
 por el valor inferior del intervalo que almacenan.
 Si existe un nodo cuyo intervalo tiene el mismo valor inferior
que x,
 actualiza el límite superior del intervalo almacenado en el
árbol.

  */
    public void insert(Interval x) {
        root = insert(root,x);
    }

    private Node insert(Node root, Interval x){
        if(root==null){
            size++;
            root = new Node(x);
        }
        else{
            if(x.compareTo(root.interval) < 0){ // si es menor, se va por la izquierda
                root.max = Math.max(x.high(), root.max);    // si se va por la izq o derecha, el max del padre debe cambiar al max general, considerando el insertado
                root.lt = insert(root.lt, x);
            }
            else if(x.compareTo(root.interval) > 0){ // si es mayor, se va por la derecha
                root.max = Math.max(x.high(), root.max);    // si se va por la izq o derecha, el max del padre debe cambiar al max general, considerando el insertados
                root.rt = insert(root.rt, x);
            }
            else{   // cambio el límite superior -> lo hace node automáticamente
                root = new Node(x,root.lt, root.rt);
            }
        }

        return root;
    }
    /* (0.5 ptos) Devuelve true si se satisface la condición 1:
    Condición C1: el nodo n no es nulo y su atributo max
    es mayor o igual que el límite inferior del
   intervalo x
    */
    private boolean condicionC1(Node n, Interval x) {
        return n!=null && n.max >= x.low();
    }
    /* (0.5 ptos) Devuelve true si se satisface la condición 2
    Condición C2: el hijo derecho de n no es nulo y el límite inferior
   de n
    es menor o igual que el límite superior del intervalo
   x
    */
    private boolean condicionC2(Node n, Interval x) {
        return n!=null && n.rt != null && n.interval.low() <= x.high();
    }
    /* (2 ptos) Devuelve un intervalo del árbol que solapa con x.
    Debe ser una implementación eficiente que aproveche la propiedad de
   orden.
    Si no solapa con ningún intervalo del árbol devuelve null.
    En las transparencias 13-16 hay ejemplos de como debe explorarse el
   árbol.
    */
    public Interval searchOverlappingInterval(Interval x) {
        return searchOverlappingInterval(root,x);
    }
    private Interval searchOverlappingInterval(Node root, Interval x) {
        if(condicionC1(root,x)){
            if(root.interval.overlap(x)){
                return root.interval;
            }
            else{
//                Interval posible = searchOverlappingInterval(root.lt,x);
                Interval posible = null;
                if(root.lt!=null){
                    posible = searchOverlappingInterval(root.lt,x);
                }
                if(posible==null){
                    if(condicionC2(root, x)){
                        posible = searchOverlappingInterval(root.rt,x);
                    }
                }
                return posible;
            }
        }
        return null;
    }
    /* (2 ptos) Devuelve una lista con todos los intervalos del árbol que
   solapan con x.
    Debe ser una implementación eficiente que aproveche la propiedad
   de orden.
    Si no solapa con ninguno devuelve una lista vacía.
    En la transparencia 17 hay un ejemplo.
    */
    public List<Interval> allOverlappingIntervals(Interval x){
        LinkedList<Interval> l = new LinkedList<>();
        allOverlappingIntervals(root,x,l);
        return l;
    }
    private void allOverlappingIntervals(Node n, Interval x, LinkedList<Interval> l){
        if(n==null){
            ;
        }
        else{
            if(condicionC1(n,x)){
                if(n.interval.overlap(x)){
                    l.append(n.interval);
                }
                if(n.lt!=null){
                    allOverlappingIntervals(n.lt,x,l);
                }
                if(condicionC2(n,x)){
                    allOverlappingIntervals(n.rt,x,l);
                }
            }
        }
    }

    /* ---------- No modificar esta parte ---------- */
    @Override
    public String toString() {
        return toStringRec(root);
    }
    private String toStringRec(Node root) {
        if(root!=null){
            return toStringRec(root.lt) + root.interval.toString()+"( "+
                    root.max +" )"+ toStringRec(root.rt);
        }
        return "";
    }
}
