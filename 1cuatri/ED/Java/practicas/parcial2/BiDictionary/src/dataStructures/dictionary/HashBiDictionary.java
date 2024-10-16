package dataStructures.dictionary;
import dataStructures.list.List;

import dataStructures.list.ArrayList;
import dataStructures.set.AVLSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

import java.text.Bidi;
import java.util.Iterator;

/**
 * Estructuras de Datos. Grados en Informatica. UMA.
 * Examen de septiembre de 2018.
 *
 * Apellidos, Nombre:
 * Titulacion, Grupo:
 */
public class HashBiDictionary<K,V> implements BiDictionary<K,V>{
	private Dictionary<K,V> bKeys;
	private Dictionary<V,K> bValues;
	
	public HashBiDictionary() {
		bKeys = new HashDictionary<>();
		bValues = new HashDictionary<>();
	}
	
	public boolean isEmpty() {
		// TODO
		return bKeys.isEmpty();
	}
	
	public int size() {
		// TODO
		return bKeys.size();
	}
	
	public void insert(K k, V v) {
		bKeys.insert(k,v);
		bValues.insert(v,k);
	}
	
	public V valueOf(K k) {
		return bKeys.valueOf(k);
	}
	
	public K keyOf(V v) {
		return bValues.valueOf(v);
	}
	
	public boolean isDefinedKeyAt(K k) {
		return bKeys.isDefinedAt(k);
	}
	
	public boolean isDefinedValueAt(V v) {
		return bValues.isDefinedAt(v);
	}
	
	public void deleteByKey(K k) {
		if(isDefinedKeyAt(k)){
			bValues.delete(valueOf(k));
			bKeys.delete(k);
		}
	}
	
	public void deleteByValue(V v) {
		if(isDefinedValueAt(v)){
			bKeys.delete(keyOf(v));
			bValues.delete(v);
		}
	}
	
	public Iterable<K> keys() {
		return bKeys.keys();
	}
	
	public Iterable<V> values() {
		return bValues.keys();
	}
	
	public Iterable<Tuple2<K, V>> keysValues() {
		return bKeys.keysValues();
	}
	
		
	public static <K,V extends Comparable<? super V>> BiDictionary<K, V> toBDictionary(Dictionary<K,V> dict) {
		BiDictionary<K,V> bidic = new HashBiDictionary<K, V>();
		Iterator<Tuple2<K,V>> it = dict.keysValues().iterator();

		while(it.hasNext()){
			Tuple2<K,V> tupla = it.next();
			if(bidic.isDefinedValueAt(tupla._2())){
				throw new IllegalArgumentException("toBiDictionary: the value is already defined: no injection");
			}
			bidic.insert(tupla._1(), tupla._2());
		}

		return bidic;
	}
	
	public <W> BiDictionary<K, W> compose(BiDictionary<V,W> bdic) {

		BiDictionary<K,W> b = new HashBiDictionary<>();

		for(K key : this.keys()){
			W value = bdic.valueOf(this.valueOf(key));
			b.insert(key,value);
		}
		return b;
	}
		
	public static <K extends Comparable<? super K>> boolean isPermutation(BiDictionary<K,K> bd) {
		boolean permutacion = true;

		for(K key : bd.keys()){
			permutacion = bd.isDefinedKeyAt(key) && bd.isDefinedValueAt(key);
		}

		return permutacion;
	}
	
	// Solo alumnos con evaluación por examen final.
    // =====================================
	
	public static <K extends Comparable<? super K>> List<K> orbitOf(K k, BiDictionary<K,K> bd) {
		if(!isPermutation(bd)){
			throw new IllegalArgumentException("orbitOf: no es permutación");
		}
		ArrayList<K> l = new ArrayList<>();
		l.append(k);
		K k_aux = bd.valueOf(k);
		while(!k.equals(k_aux)){
			l.append(k_aux);
			k_aux = bd.valueOf(k_aux);
		}

		return l;
	}
	
	public static <K extends Comparable<? super K>> List<List<K>> cyclesOf(BiDictionary<K,K> bd) {
		if(!isPermutation(bd)){
			throw new IllegalArgumentException("cyclesOf: no es permutación");
		}
		List<List<K>> l = new ArrayList<>();
//		ArrayList<K> l_aux = new ArrayList<>();
		Set<K> set = new AVLSet<>();

		for(K k : bd.keys()){
			set.insert(k);
		}

		cyclesOf(set,l,bd);

		return l;
	}

	// para poder hacer recursividad y que sea mas eficiente
	private static <K extends Comparable<? super K>> void cyclesOf(Set<K> set, List<List<K>> l,BiDictionary<K,K> bd) {
		while(!set.isEmpty()){
			Iterator<K> it = set.iterator();
			List<K> l_aux = orbitOf2(it.next(), bd);
			for(K elem : l_aux){
				set.delete(elem);
			}
			l.append(l_aux);
		}
	}

	// para evitar hacer 500 veces isPermutation (si ejecuto esto ya se que no lo es)
	private static <K extends Comparable<? super K>> List<K> orbitOf2(K k, BiDictionary<K,K> bd) {
		ArrayList<K> l = new ArrayList<>();
		l.append(k);
		K k_aux = bd.valueOf(k);
		while(!k.equals(k_aux)){
			l.append(k_aux);
			k_aux = bd.valueOf(k_aux);
		}

		return l;
	}

    // =====================================
	
	
	@Override
	public String toString() {
		return "HashBiDictionary [bKeys=" + bKeys + ", bValues=" + bValues + "]";
	}
	
	
}
