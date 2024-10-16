package examen;

import java.util.Iterator;

import dataStructures.graph.*;
import dataStructures.set.Set;
import dataStructures.set.HashSet;
import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.list.ArrayList;

public class Febrero2014<V> {

	public static <V> DiGraph<V> reverseDiGraph(DiGraph<V> g) {
		DiGraph<V> reversed = new DictionaryDiGraph<>();
		Set<V> vertices = g.vertices();

		Iterator<V> it = vertices.iterator();
		while(it.hasNext()){
			V orgVertex = it.next();
			reversed.addVertex(orgVertex);	// añado vertice

			Iterator<V> it2 = g.successors(orgVertex).iterator();
			while(it2.hasNext()){
				// si no hago esto, da error ya que intento conectar un vertice que no esta en el grafo
				V new_vertex = it2.next();
				reversed.addVertex(new_vertex);
				reversed.addDiEdge(new_vertex, orgVertex);
			}
		}

		return reversed;
	}
	
	public static <V> DiGraph<V> restrictDiGraph(DiGraph<V> g, Set<V> vs) {
		DiGraph<V> restricted = new DictionaryDiGraph<>();

		Iterator<V> it = g.vertices().iterator();
		while(it.hasNext()){
			V vertex = it.next();
			if(vs.isElem(vertex)){
				restricted.addVertex(vertex);

				Iterator<V> it2 = g.successors(vertex).iterator();
				while(it2.hasNext()){
					V new_vertex = it2.next();
					if(vs.isElem(new_vertex)){
						restricted.addVertex(new_vertex);
						restricted.addDiEdge(vertex, new_vertex);
					}
				}
			}
		}

		return restricted;
	}
	
	public static <V> Set<V> sccOf (DiGraph<V> g, V src) {
		Traversal<V> dft = new DepthFirstTraversal<>(g, src); // paso 1
		Set<V> vs = new HashSet<>();
		for(V vertex : dft.vertices()){
			vs.insert(vertex);
		}

		DiGraph<V> gr = restrictDiGraph(g,vs); // paso 2
		DiGraph<V> g2 = reverseDiGraph(gr);	// paso 3

		Traversal<V> dftSol = new DepthFirstTraversal<>(g2, src); // paso 4
		Set<V> sol = new HashSet<>();
		Iterator<V> it2 = dftSol.verticesIterator();
		for(V vertex : dftSol.vertices()){
			sol.insert(vertex);
		}

		return sol;
	}

	public static <V> Set<Set<V>> stronglyConnectedComponentsDiGraph(DiGraph<V> g) {
		Set<Set<V>> sol = new HashSet<>();
		DiGraph<V> gCopy = restrictDiGraph(g, g.vertices());	// para crear un copia


		return stronglyConnectedComponentsDiGraph(gCopy, sol);
	}

	private static <V> Set<Set<V>> stronglyConnectedComponentsDiGraph(DiGraph<V> g, Set<Set<V>> sol) {
		if(g.numVertices() == 0){
			return sol;
		}
		else{
			Iterator<V> it = g.vertices().iterator();
			V primer = it.next();
			Set<V> scc = sccOf(g,primer);
			sol.insert(scc);
			for(V v : scc){
				g.deleteVertex(v);
			}
			return stronglyConnectedComponentsDiGraph(g,sol);
		}
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

		System.out.println("\nReversed");
		System.out.println(reverseDiGraph(g).toString());
		
		Set<Character> vs = new HashSet<Character>();
		
		vs.insert(A);
		vs.insert(B);
		vs.insert(E);
		vs.insert(F);
		vs.insert(G);

		System.out.println("\nRestricted to ABEFG");
		System.out.println(restrictDiGraph(g, vs).toString());
		System.out.println(sccOf(g, A).toString());
		System.out.println(stronglyConnectedComponentsDiGraph(g).toString());
		
	}
	
}
