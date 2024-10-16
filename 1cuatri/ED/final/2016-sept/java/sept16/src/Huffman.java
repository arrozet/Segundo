
/**
 * Huffman trees and codes.
 *
 * Data Structures, Grado en Informatica. UMA.
 *
 *
 * Student's name:
 * Student's group:
 */

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.ArrayList;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.priorityQueue.BinaryHeapPriorityQueue;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.tuple.Tuple2;

public class Huffman {

    // Exercise 1
    public static Dictionary<Character, Integer> weights(String s) {
    	Dictionary<Character, Integer> d = new AVLDictionary<>();
        for(int i=0; i<s.length(); i++){
            if(d.valueOf(s.charAt(i))==null){
                d.insert(s.charAt(i), 1);
            }
            else{
                d.insert(s.charAt(i), d.valueOf(s.charAt(i))+1);
            }
        }
        return d;
    }

    // Exercise 2.a
    public static PriorityQueue<WLeafTree<Character>> huffmanLeaves(String s) {
    	PriorityQueue<WLeafTree<Character>> pq = new BinaryHeapPriorityQueue<>();
        Dictionary<Character, Integer> d = weights(s);
        for(Tuple2<Character,Integer> t : d.keysValues()){
            pq.enqueue(new WLeafTree<>(t._1(), t._2()));
        }
        return pq;
    }

    // Exercise 2.b
    public static WLeafTree<Character> huffmanTree(String s) {
        Dictionary<Character, Integer> d = weights(s);
        if(d.size() < 2){
            throw new HuffmanException("huffmanTree: the string must have at least two different symbols");
        }
    	return huffmanTree(huffmanLeaves(s));
    }
    private static WLeafTree<Character> huffmanTree(PriorityQueue<WLeafTree<Character>> pq) {
        WLeafTree<Character> aux = pq.first();
        pq.dequeue();
        if(pq.isEmpty()){   // si solo 1 elem
            return aux;
        }
        else{
            pq.enqueue(aux);
            WLeafTree<Character> arbol1 = pq.first();
            pq.dequeue();
            WLeafTree<Character> arbol2 = pq.first();
            pq.dequeue();
            WLeafTree<Character> res = new WLeafTree<>(arbol1, arbol2);
            pq.enqueue(res);
            return huffmanTree(pq);

        }
    }
    // Exercise 3.a
    public static Dictionary<Character, List<Integer>> joinDics(Dictionary<Character, List<Integer>> d1, Dictionary<Character, List<Integer>> d2) {
        for(Tuple2<Character,List<Integer>> t : d1.keysValues()){
            d2.insert(t._1(), t._2());
        }
    	return d2;
    }

    // Exercise 3.b
    public static Dictionary<Character, List<Integer>> prefixWith(int i, Dictionary<Character, List<Integer>> d) {
        for(Tuple2<Character,List<Integer>> t : d.keysValues()){
            List<Integer> l = t._2();
            if(l.isEmpty()){
                l.append(i);
            }
            else{
                l.insert(0,i);
            }

            d.insert(t._1(),l);
        }
        return d;
    }

    // Exercise 3.c
    public static Dictionary<Character, List<Integer>> huffmanCode(WLeafTree<Character> ht) {
        Dictionary<Character, List<Integer>> d = new AVLDictionary<>();
    	return huffmanCode(ht, d);
    }

    private static Dictionary<Character, List<Integer>> huffmanCode(WLeafTree<Character> ht, Dictionary<Character, List<Integer>> d) {
        if(ht.isLeaf()){
            d.insert(ht.elem(), new LinkedList<>());
            return d;
        }else{
            Dictionary<Character, List<Integer>> d1 = new AVLDictionary<>();
            Dictionary<Character, List<Integer>> d2 = new AVLDictionary<>();
            Dictionary<Character, List<Integer>> sol_izq = prefixWith(0, huffmanCode(ht.leftChild(), d1));
            Dictionary<Character, List<Integer>> sol_der = prefixWith(1, huffmanCode(ht.rightChild(), d2));
            return joinDics(sol_izq,sol_der);
        }

    }

    // Exercise 4
    public static List<Integer> encode(String s, Dictionary<Character, List<Integer>> hc) {
        //to do 
    	return null;
    }

    // Exercise 5
    public static String decode(List<Integer> bits, WLeafTree<Character> ht) {
        //to do 
    	return null;
    }
}
