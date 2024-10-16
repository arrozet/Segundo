
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
    	//to do
        Dictionary<Character, Integer> d = new AVLDictionary<>();
        List<Character> l = new ArrayList<>();

        for(int i = 0; i<s.length(); i++)
        {
            l.append(s.charAt(i));
        }

        while(!l.isEmpty())
        {
            char c = l.get(0);
            l.remove(0);

            int contador = 1;

            for(int i = 0; i<l.size(); i++)
            {
                if (c == l.get(i))
                {
                    contador++;
                    l.remove(i);
                }
            }

            d.insert(c, contador);

        }


        return d;
    }

    // Exercise 2.a
    public static PriorityQueue<WLeafTree<Character>> huffmanLeaves(String s) {
    	//to do

        PriorityQueue<WLeafTree<Character>> PQ = new BinaryHeapPriorityQueue<>();

        WLeafTree<Character> wLeaf;

        Dictionary<Character, Integer> d = weights(s);

        for(Tuple2<Character, Integer> x : d.keysValues())
        {
            wLeaf = new WLeafTree<>(x._1(), x._2());
            PQ.enqueue(wLeaf);

        }

        return PQ;
    }

    // Exercise 2.b
    public static WLeafTree<Character> huffmanTree(String s) {
    	//to do

        List<Character> l = new ArrayList<>();
        PriorityQueue<WLeafTree<Character>> PQ = huffmanLeaves(s);
        boolean encontrado = false;
        WLeafTree<Character> arbol = null;


        for (int i = 0; i<s.length(); i++)
        {
            l.append(s.charAt(i));
        }

        char c = l.get(0);

        for(int i = 0; i<l.size(); i++)
        {
            if(c==l.get(i))
            {

            } else {
                encontrado = true;
            }
        }

        if(!encontrado)
        {
            throw new HuffmanException("Cadena con menos de 2 caracteres");
        }

        while(!PQ.isEmpty())
        {
            WLeafTree<Character> w1 = PQ.first();
            PQ.dequeue();
            if (!PQ.isEmpty())
             {
                WLeafTree<Character> w2 = PQ.first();
                PQ.dequeue();
                WLeafTree<Character> extra = merge(w1, w2);
                PQ.enqueue(extra);
                arbol = extra;
            }


        }

    	return arbol;
    }

    private static WLeafTree<Character> merge(WLeafTree<Character> w1, WLeafTree<Character> w2) {
        WLeafTree<Character> wC = new WLeafTree<>(w1, w2);

        return wC;
    }


    // Exercise 3.a
    public static Dictionary<Character, List<Integer>> joinDics(Dictionary<Character, List<Integer>> d1, Dictionary<Character, List<Integer>> d2) {
        //to do

        Dictionary<Character, List<Integer>> d = new AVLDictionary<>();

        for(Tuple2<Character, List<Integer>> x : d1.keysValues())
        {
            d.insert(x._1(), x._2());
        }

        for(Tuple2<Character, List<Integer>> x : d2.keysValues())
        {
            d.insert(x._1(), x._2());
        }

    	return d;
    }

    // Exercise 3.b
    public static Dictionary<Character, List<Integer>> prefixWith(int i, Dictionary<Character, List<Integer>> d) {
        //to do
        Dictionary<Character, List<Integer>> dic = new AVLDictionary<>();

        for(Tuple2<Character, List<Integer>> x : d.keysValues())
        {
            List<Integer> l = new ArrayList<>();
            l = x._2();
            l.insert(0,i);
            dic.insert(x._1(),l);
        }


    	return dic;
    }

    // Exercise 3.c
    public static Dictionary<Character, List<Integer>> huffmanCode(WLeafTree<Character> ht) {
        //to do

        Dictionary<Character, List<Integer>> d = new AVLDictionary<>();
        List<Integer> l = new ArrayList<>();
        WLeafTree<Character> aux;

        d = huffmanCodeRec (ht, l);

    	return d;
    }


    public static Dictionary<Character, List<Integer>> huffmanCodeRec(WLeafTree<Character> ht, List<Integer> l) {
        //to do

        Dictionary<Character, List<Integer>> d = new AVLDictionary<>();
        WLeafTree<Character> aux;

        if(ht.isLeaf())
        {
            d.insert(ht.elem(), l);
        } else
        {
            List<Integer> l1 = new ArrayList<>();


            List<Integer> l2 = new ArrayList<>();


            for(int i = 0; i<l.size(); i++)
            {
                l1.append(l.get(i));
                l2.append(l.get(i));
            }
            l1.append(0);
            l2.append(1);

            d = joinDics (huffmanCodeRec(ht.leftChild(), l1), huffmanCodeRec(ht.rightChild(), l2));
        }

        return d;
    }



    // Exercise 4
    public static List<Integer> encode(String s, Dictionary<Character, List<Integer>> hc) {
        //to do

        List<Character> l = new ArrayList<>();
        List<Integer> code = new ArrayList<>();

        for(int i = 0; i<s.length(); i++)
        {
            l.append(s.charAt(i));
        }

        while(!l.isEmpty())
        {
            char c = l.get(0);
            l.remove(0);

            for(Tuple2<Character, List<Integer>> x : hc.keysValues())
            {
                if(c == x._1())
                {
                    for(int i = 0; i<x._2().size(); i++)
                    {
                        code.append(x._2().get(i));
                    }
                }
            }

        }


    	return code;
    }

    // Exercise 5
    public static String decode(List<Integer> bits, WLeafTree<Character> ht) {
        //to do

        List<Integer> l = bits;
        List<Integer> l2 = new ArrayList<>();
        Dictionary<Character, List<Integer>> d = huffmanCode(ht);

        String palabra = "";

        while(!l.isEmpty())
        {
            l2.append(l.get(0));
            l.remove(0);
            boolean igual = true;
            boolean agregado = false;

            for(Tuple2<Character,List<Integer>> x : d.keysValues())
            {
                if (x._2().equals(l2))
                {
                    palabra = palabra + x._1();
                    l2 = new ArrayList<>();
                }
            }

        }

    	return palabra;
    }
}
