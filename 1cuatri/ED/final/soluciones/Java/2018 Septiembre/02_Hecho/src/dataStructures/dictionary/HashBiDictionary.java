package dataStructures.dictionary;
import dataStructures.list.List;

import dataStructures.list.ArrayList;
import dataStructures.set.AVLSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

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

		if(bKeys.isDefinedAt(k))
		{
			V nuevoValor = bKeys.valueOf(k);

			bKeys.insert(k, v);

			bValues.delete(nuevoValor);
			bValues.insert(v,k);


		} else
		{
			bKeys.insert(k, v);
			bValues.insert(v,k);
		}

	}
	
	public V valueOf(K k) {
		// TODO

		V valor = null;

		if(bKeys.isDefinedAt(k))
		{
			valor = bKeys.valueOf(k);
		}


		return valor;
	}
	
	public K keyOf(V v) {
		// TODO

		K clave = null;

		if(bValues.isDefinedAt(v))
		{
			clave = bValues.valueOf(v);
		}

		return clave;
	}
	
	public boolean isDefinedKeyAt(K k) {
		return bKeys.isDefinedAt(k);
	}
	
	public boolean isDefinedValueAt(V v) {
		return bValues.isDefinedAt(v);
	}
	
	public void deleteByKey(K k) {
		// TODO

		if(bKeys.isDefinedAt(k))
		{
			V valor = bKeys.valueOf(k);

			bKeys.delete(k);
			bValues.delete(valor);

		} else
		{

		}

	}
	
	public void deleteByValue(V v) {
		// TODO

		if(bValues.isDefinedAt(v))
		{
			K key = bValues.valueOf(v);

			bValues.delete(v);
			bKeys.delete(key);

		} else
		{

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

		if(!inyectivo(dict))
		{
			throw new IllegalArgumentException("El diccionario no es inyectivo");
		}

		BiDictionary<K,V> bi = new HashBiDictionary<>();

		List<K> l = new ArrayList<>();

		for(Tuple2<K,V> x : dict.keysValues())
		{
			l.append(x._1());
		}

		while(!l.isEmpty())
		{
			K key = l.get(0);
			V value = null;

			l.remove(0);

			for(Tuple2<K,V> x : dict.keysValues())
			{
				if (key.equals(x._1()))
				{
					value = x._2();
				}
			}

			bi.insert(key, value);

		}

		return bi;
	}

	private static <V extends Comparable<? super V>, K> boolean inyectivo(Dictionary<K,V> dict) {

		boolean inyect = true;

		List<V> l = new ArrayList<>();

		for(Tuple2<K,V> x : dict.keysValues())
		{
			l.append(x._2());
		}

		while(!l.isEmpty())
		{
			V valor = l.get(0);
			l.remove(0);

			for(V x : l)
			{
				if(x == valor)
				{
					inyect = false;
				}
			}

		}


		return inyect;
	}

	public <W> BiDictionary<K, W> compose(BiDictionary<V,W> bdic) {
		// TODO

		BiDictionary<K,W> bi = new HashBiDictionary<>();

		for(Tuple2<K,V> x : bKeys.keysValues())
		{
			for(Tuple2<V,W> y : bdic.keysValues())
			{
				if(x._2() == y._1())
				{
					bi.insert(x._1(), y._2());
				}
			}
		}

		return bi;
	}
		
	public static <K extends Comparable<? super K>> boolean isPermutation(BiDictionary<K,K> bd) {
		// TODO

		List<K> l1 = new ArrayList<>();
		List<K> l2 = new ArrayList<>();
		boolean [] lista = null;
		boolean permu = false;

		for(Tuple2<K,K> x : bd.keysValues())
		{
			l1.append(x._1());
			l2.append(x._2());
		}

		if(l1.size() == l2.size())
		{
			lista = new boolean[l2.size()];

			int i = 0;
			while(!l1.isEmpty())
			{
				K key = l1.get(0);
				l1.remove(0);

				for(K x : l2)
				{
					if (key == x)
					{
						lista[i] = true;

						i++;

					}
				}

			}
		}


		permu = true;
		for(int i = 0; i< lista.length; i++)
		{
			if (lista[i] == !true)
			{
				permu = false;
				System.out.println("kaka");
			}
		}

		return permu;
	}
	
	// Solo alumnos con evaluación por examen final.
    // =====================================
	
	public static <K extends Comparable<? super K>> List<K> orbitOf(K k, BiDictionary<K,K> bd) {
		// TODO

		if(!isPermutation(bd))
		{
			throw new IllegalArgumentException("El diccionario no es permutacion");
		}

		List<K> l = new ArrayList<>();

		l.append(k);

		boolean encontrado = false;
		for(Tuple2<K,K> x : bd.keysValues())
		{
			if(k.equals(x._1()) && !encontrado)
			{
				k = x._2();
				encontrado = true;
			}
		}

		while(!k.equals(l.get(0)))
		{
			encontrado = false;
			l.append(k);

			for(Tuple2<K,K> x : bd.keysValues())
			{
				if(k.equals(x._1()) && !encontrado)
				{
					k = x._2();
					encontrado = true;
				}
			}

		}

		return l;
	}
	
	public static <K extends Comparable<? super K>> List<List<K>> cyclesOf(BiDictionary<K,K> bd) {
		// TODO

		List<List<K>> l = new ArrayList<>();
		List<K> lComprobar = new ArrayList<>();

		for(Tuple2<K,K> x : bd.keysValues())
		{
			lComprobar.append(x._1());
		}

		while(!lComprobar.isEmpty())
		{
			List<K> aux = orbitOf(lComprobar.get(0), bd);
			l.append(aux);

			for(int i = 0; i<lComprobar.size(); i++)
			{
				for(int i2 = 0; i2< aux.size(); i2++)
				{
					if (lComprobar.get(i).equals(aux.get(i2)))
					{
						lComprobar.remove(i);
					}
				}
			}

		}

		return l;
	}

    // =====================================
	
	
	@Override
	public String toString() {
		return "HashBiDictionary [bKeys=" + bKeys + ", bValues=" + bValues + "]";
	}
	
	
}
