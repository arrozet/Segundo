package dataStructures.dictionary;

import dataStructures.set.AVLSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;

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
		// TODO

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
		// TODO

		for(Tuple2<K,V> x : bKeys.keysValues())
		{
			if(x == x._1())
			{
				bValues.delete(x._2());
			}
		}

			bKeys.insert(k, v);
			bValues.insert(v, k);

	}
	
	public V valueOf(K k) {
		// TODO

		V valor = null;

		for(Tuple2<K,V> x : bKeys.keysValues())
		{
			if (k.equals(x._1()))
			{
				valor = x._2();
			}
		}

		return valor;
	}
	
	public K keyOf(V v) {
		// TODO

		K key = null;

		for (Tuple2<V,K> x : bValues.keysValues())
		{
			if (x._1() == v)
			{
				key = x._2();
			}
		}


		return key;
	}
	
	public boolean isDefinedKeyAt(K k) {
		return bKeys.isDefinedAt(k);
	}
	
	public boolean isDefinedValueAt(V v) {
		return bValues.isDefinedAt(v);
	}
	
	public void deleteByKey(K k) {
		// TODO

		for(Tuple2<K,V> x : bKeys.keysValues())
		{
			if(k == x._1())
			{
				bKeys.delete(x._1());
				bValues.delete(x._2());
			}
		}

	}
	
	public void deleteByValue(V v) {
		// TODO

		for(Tuple2<V,K> x : bValues.keysValues())
		{
			if(v == x._1())
			{
				bKeys.delete(x._2());
				bValues.delete(x._1());
			}
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
	
		
	public static <K,V extends Comparable<? super V>> BiDictionary<K, V> toBiDictionary(Dictionary<K,V> dict) {
		// TODO

		HashBiDictionary<K, V> b = new HashBiDictionary<>();

		if(!b.inyectivo(dict))
		{
			throw new IllegalArgumentException("No es inyectivo");
		}

		for(Tuple2<K,V> x : dict.keysValues())
		{
			b.insert(x._1(), x._2());
		}

		return b;
	}

	public boolean inyectivo (Dictionary<K, V> dic)
	{
		boolean inyect = true;

		List<V> l = new ArrayList<>();

		for(Tuple2<K,V> x : dic.keysValues())
		{
			l.add(x._2());
		}

		while(!l.isEmpty())
		{
			V valor = l.get(0);
			l.remove(0);

			if(l.contains(valor))
			{
				inyect = false;
			}

		}

		return inyect;
	}

	public <W> BiDictionary<K, W> compose(BiDictionary<V,W> bdic) {
		// TODO

		BiDictionary<K, W> b = new HashBiDictionary<>();

		for(Tuple2<K,V> x : bKeys.keysValues())
		{
			for (Tuple2<V,W> y : bdic.keysValues())
			{
				if(x._2() == y._1())
				{
					b.insert(x._1(), y._2());
				}

			}

		}

		return b;
	}
		
	public static <K extends Comparable<? super K>> boolean isPermutation(BiDictionary<K,K> bd) {
		// TODO

		boolean permu = true;

		List<K> l1 = new ArrayList<>();
		List<K> l2 = new ArrayList<>();

		for (Tuple2<K,K> x : bd.keysValues())
		{
			l1.add(x._1());
			l2.add(x._2());
		}

		while(!l1.isEmpty() && !l2.isEmpty())
		{
			K key = l1.get(0);
			l1.remove(0);

			if(l2.contains(key))
			{
				l2.remove(key);
			} else
			{
				permu = false;
			}

		}

		return permu;
	}
	
	// Solo alumnos con evaluación por examen final.
    // =====================================
	
	public static <K extends Comparable<? super K>> List<K> orbitOf(K k, BiDictionary<K,K> bd) {
		// TODO

		List<K> l = new ArrayList<>();

		if (!isPermutation(bd))
		{
			throw new IllegalArgumentException("No es permutacion");
		}

		K nuevoK = k;

		for(Tuple2<K,K> x : bd.keysValues())
		{
			if(!l.contains(k))
			{
				if(k.equals(x._1()))
				{
					l.add(x._1());
					k = x._2();
				}
			}
		}

		return l;
	}
	
	public static <K extends Comparable<? super K>> List<List<K>> cyclesOf(BiDictionary<K,K> bd) {
		// TODO

		List<List<K>> lc = new ArrayList<>();

		if (!isPermutation(bd))
		{
			throw new IllegalArgumentException("No es permutacion");
		}

		while(!bd.isEmpty())
		{
			List<K> l = new ArrayList<>();
			K prim = null;

			for(Tuple2<K,K> x : bd.keysValues())
			{
				if (prim == null)
				{
					prim = x._1();
				}
			}

			l = orbitOf(prim, bd);

			lc.add(l);

			for(Tuple2<K,K> x : bd.keysValues())
			{
				if (l.contains(x._1()))
				{
					bd.deleteByKey(x._1());
				}
			}


		}

		return lc;
	}

    // =====================================
	
	
	@Override
	public String toString() {
		return "HashBiDictionary [bKeys=" + bKeys + ", bValues=" + bValues + "]";
	}
	
	
}
