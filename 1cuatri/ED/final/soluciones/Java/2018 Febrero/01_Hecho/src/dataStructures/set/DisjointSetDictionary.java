/**
 * Estructuras de Datos. Grados en Informatica. UMA.
 * Examen de febrero de 2018.
 *
 * Apellidos, Nombre:
 * Titulacion, Grupo:
 */

package dataStructures.set;

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.tuple.Tuple2;

public class DisjointSetDictionary<T extends Comparable<? super T>> implements DisjointSet<T> {

    private Dictionary<T, T> dic;

    /**
     * Inicializa las estructuras necesarias.
     */
    public DisjointSetDictionary() {
        // TODO
        dic = new AVLDictionary<>();
    }

    /**
     * Devuelve {@code true} si el conjunto no contiene elementos.
     */
    @Override
    public boolean isEmpty() {
        // TODO

        return dic.isEmpty();
    }

    /**
     * Devuelve {@code true} si {@code elem} es un elemento del conjunto.
     */
    @Override
    public boolean isElem(T elem) {
        // TODO
        return dic.isDefinedAt(elem);
    }

    /**
     * Devuelve el numero total de elementos del conjunto.
     */

    @Override
    public int numElements() {
        // TODO
        return dic.size();
    }

    /**
     * Agrega {@code elem} al conjunto. Si {@code elem} no pertenece al
     * conjunto, crea una nueva clase de equivalencia con {@code elem}. Si
     * {@code elem} pertencece al conjunto no hace nada.
     */
    @Override
    public void add(T elem) {
        // TODO

        if(isElem(elem))
        {

        } else
        {
            dic.insert(elem, elem);
        }

    }

    /**
     * Devuelve el elemento canonico (la raiz) de la clase de equivalencia la
     * que pertenece {@code elem}. Si {@code elem} no pertenece al conjunto
     * devuelve {@code null}.
     */
    private T root(T elem) {
        // TODO

        if(isElem(elem))
        {
            if(elem == dic.valueOf(elem))
            {
                return elem;
            } else
            {
                return root(dic.valueOf(elem));
            }
        } else
        {
            return null;
        }

    }

    /**
     * Devuelve {@code true} si {@code elem} es el elemento canonico (la raiz)
     * de la clase de equivalencia a la que pertenece.
     */
    private boolean isRoot(T elem) {
        // TODO
        if(elem == dic.valueOf(elem))
        {
            return true;
        } else
        {
            return false;
        }

    }

    /**
     * Devuelve {@code true} si {@code elem1} y {@code elem2} estan en la misma
     * clase de equivalencia.
     */
    @Override
    public boolean areConnected(T elem1, T elem2) {
        // TODO

        if(root(elem1) == root(elem2) && root(elem1) != null)
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * Devuelve una lista con los elementos pertenecientes a la clase de
     * equivalencia en la que esta {@code elem}. Si {@code elem} no pertenece al
     * conjunto devuelve la lista vacia.
     */
    @Override
    public List<T> kind(T elem) {
        // TODO

        List<T> aux = new ArrayList<>();
        List<T> l = new ArrayList<>();
        T rootElem = root(elem);

        for (Tuple2<T,T> x : dic.keysValues())
        {
            aux.append(x._1());
        }

        while(!aux.isEmpty())
        {
            if(root(aux.get(0)).equals(rootElem))
            {
                l.append(aux.get(0));
            }
            aux.remove(0);
        }

        return l;
    }

    /**
     * Une las clases de equivalencias de {@code elem1} y {@code elem2}. Si
     * alguno de los dos argumentos no esta en el conjunto lanzara una excepcion
     * {@code IllegalArgumenException}.
     */
    @Override
    public void union(T elem1, T elem2) {
        // TODO

        T root1 = root(elem1);
        T root2 = root(elem2);

        if(root1.compareTo(root2) > 0)
        {
            dic.insert(root1, root2);
        } else
        {
            dic.insert(root2, root1);
        }

    }

    // ====================================================
    // A partir de aqui solo para alumnos a tiempo parcial
    // que no sigan el proceso de evaluacion continua.
    // ====================================================

    /**
     * Aplana la estructura de manera que todos los elementos se asocien
     * directamente con su representante canonico.
     */
    @Override
    public void flatten() {
        // TODO

        List<T> aux = new ArrayList<>();

        for(Tuple2<T,T> x : dic.keysValues())
        {
            aux.append(x._1());
        }

        for(T x : aux)
        {
            dic.insert(x, root(x));
        }

    }

    /**
     * Devuelve una lista que contiene las clases de equivalencia del conjunto
     * como listas.
     */
    @Override
    public List<List<T>> kinds() {
        // TODO
        List<T> aux = new ArrayList<>();
        List<List<T>> l = new ArrayList<>();
        boolean encontrado = false;
        flatten();

        for(Tuple2<T,T> x : dic.keysValues())
        {
            encontrado = false;
            for(T y : aux)
            {
                if(x._2().equals(y))
                {
                    encontrado = true;
                }
            }

            if(!encontrado)
            {
                aux.append(x._1());
            }

        }

        while(!aux.isEmpty())
        {
            l.append(kind(aux.get(0)));
            aux.remove(0);
        }

        return l;
    }

    /**
     * Devuelve una representacion del conjunto como una {@code String}.
     */
    @Override
    public String toString() {
        return "DisjointSetDictionary(" + dic.toString() + ")";
    }
}
