package examen;

import java.util.Iterator;

import dataStructures.graph.DepthFirstTraversal;
import dataStructures.graph.DiGraph;
import dataStructures.graph.DictionaryDiGraph;
import dataStructures.set.Set;
import dataStructures.set.HashSet;
import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;

public class Febrero2014<V> {

	public static <V> DiGraph<V> reverseDiGraph(DiGraph<V> g) {

		
		return null
		
	}
	
	public static <V> DiGraph<V> restrictDiGraph(DiGraph<V> g, Set<V> vs) {

		
		return null
	}
	
	public static <V> Set<V> sccOf (DiGraph<V> g, V src) {

		return null;
	}
	
	public static <V> Set<Set<V>> stronglyConnectedComponentsDiGraph(DiGraph<V> g) {

		return null;
	}
	
	private static <V> V firstVertex(Set<V> v) {
		Iterator<V> iter = v.iterator();
		return iter.next();
	}
	
	public static void main(String[] args) {
		DiGraph<Character> g = new DictionaryDiGraph<Character>(); 
		
		Character A = 'A';
		Character B = 'B';
		Character C = 'C';
		Character D = 'D';
		Character E = 'E';
		Character F = 'F';
		Character G = 'G';
		Character H = 'H';
		
		g.addVertex(A);
		g.addVertex(B);
		g.addVertex(C);
		g.addVertex(D);
		g.addVertex(E);
		g.addVertex(F);
		g.addVertex(G);
		g.addVertex(H);
		
		g.addDiEdge(A, B);
		g.addDiEdge(B, E);
		g.addDiEdge(B, F);
		g.addDiEdge(C, D);
		g.addDiEdge(C, G);
		g.addDiEdge(D, C);
		g.addDiEdge(D, H);
		g.addDiEdge(E, A);
		g.addDiEdge(E, F);
		g.addDiEdge(F, G);
		g.addDiEdge(G, F);
		g.addDiEdge(H, D);
		g.addDiEdge(H, G);
		
		System.out.println(g.toString());
		
		System.out.println(reverseDiGraph(g).toString());
		
		Set<Character> vs = new HashSet<Character>();
		
		vs.insert(A);
		vs.insert(B);
		vs.insert(E);
		vs.insert(F);
		
		System.out.println(restrictDiGraph(g, vs).toString());
		System.out.println(sccOf(g, A).toString());
		System.out.println(stronglyConnectedComponentsDiGraph(g).toString());
		
	}
	
}
