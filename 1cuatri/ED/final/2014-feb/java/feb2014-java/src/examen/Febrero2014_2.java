package examen;

import java.util.Iterator;

import dataStructures.graph.DepthFirstTraversal;
import dataStructures.graph.DiGraph;
import dataStructures.graph.DictionaryDiGraph;
import dataStructures.set.Set;
import dataStructures.set.HashSet;
import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;

public class Febrero2014_2<V> {

	public static <V> DiGraph<V> reverseDiGraph(DiGraph<V> g) {
		
		DiGraph<V> sol = new DictionaryDiGraph<V>();
		
		Iterator<V> iter = g.vertices().iterator();
		
		while(iter.hasNext()) {
			V vertex = iter.next();
			sol.addVertex(vertex);
			Set<V> set = g.successors(vertex);
			
			Iterator<V> iter2 = set.iterator();
			
			while(iter2.hasNext()) {
				V vertex2 = iter2.next();
				sol.addVertex(vertex2);
				sol.addDiEdge(vertex2, vertex);
			}
			
		}
		
		return sol;
		
	}
	
	public static <V> DiGraph<V> restrictDiGraph(DiGraph<V> g, Set<V> vs) {
		DiGraph<V> sol = new DictionaryDiGraph<V>();
		
		Iterator<V> iter = vs.iterator();
		
		while(iter.hasNext()) {
			V vertex = iter.next();
			sol.addVertex(vertex);
			Iterator<V> iter2 = g.successors(vertex).iterator();
			
			while(iter2.hasNext()) {
				V aux = iter2.next();
				if(vs.isElem(aux)) {
					sol.addVertex(aux);
					sol.addDiEdge(vertex, aux);
				}
			}
		}
		
		return sol;
	}
	
	public static <V> Set<V> sccOf (DiGraph<V> g, V src) {
		DepthFirstTraversal<V> dft = new DepthFirstTraversal<V>(g, src);
		Set<V> vs = new HashSet<V>();
		
		Iterator<V> iter = dft.vertices().iterator();
		
		while(iter.hasNext()) {
			vs.insert(iter.next());
		}
		
		DiGraph<V> auxiliarG = reverseDiGraph(restrictDiGraph(g, vs));
		
		vs =new HashSet<V>();
		
		DepthFirstTraversal<V> newDft = new DepthFirstTraversal<V>(auxiliarG, src);
		
		iter = newDft.verticesIterator();
		
		while(iter.hasNext()) {
			vs.insert(iter.next());
		}
		return vs;
	}
	
	public static <V> Set<Set<V>> stronglyConnectedComponentsDiGraph(DiGraph<V> g) {
		DiGraph<V> aux = restrictDiGraph(g, g.vertices());
		
		Set<Set<V>> sol = new HashSet<>();
		
		while(!aux.vertices().isEmpty()) {
			Set<V> partSol = new HashSet<>();
			V src = firstVertex(aux.vertices());
			partSol = sccOf(aux, src);
			sol.insert(partSol);
			Iterator<V> iter = partSol.iterator();
			while(iter.hasNext()) {
				aux.deleteVertex(iter.next());
			}
		}
		return sol;
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
