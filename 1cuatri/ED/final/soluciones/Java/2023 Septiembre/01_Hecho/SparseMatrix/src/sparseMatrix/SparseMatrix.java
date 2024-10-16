/******************************************************************************
 * Student's name: ?????????????????????????????????????
 * Identity number (DNI if Spanish/passport if Erasmus): ???????????????????
 * Student's group: ?
 * PC code: ???
 *
 * Data Structures. Grados en Informatica. UMA.
 *****************************************************************************/

package sparseMatrix;

import dataStructures.dictionary.Dictionary;
import dataStructures.dictionary.HashDictionary;
import dataStructures.list.ArrayList;
import dataStructures.list.List;
import dataStructures.tuple.Tuple2;

import java.util.Iterator;

// | = Exercise a - SparseMatrix constructor
public class SparseMatrix {
    public final int rows;
    public final int columns;
    private Dictionary<Index, Integer> nonZeros;

    public SparseMatrix(int r, int c) {
        // todo

        rows = r;
        columns = c;
        nonZeros = new HashDictionary<>();

    }

    // | = Exercise b - value
    private int value(Index ind) {
        // todo

        nonZeros.valueOf(ind);

        return nonZeros.valueOf(ind);
    }

    // | = Exercise c - update
    private void update(Index ind, int value) {
        // todo

        for(Tuple2<Index, Integer> x : nonZeros.keysValues())
        {
            if(ind.compareTo(x._1()) == 0)
            {
                nonZeros.delete(x._1());
            }
        }

        nonZeros.insert(ind, value);

    }

    // | = Exercise d - index
    private Index index(int r, int c) {
        // todo

        if(r < 0 || r > rows || c < 0 || c > rows)
        {
            throw new RuntimeException("Indices fuera de rango");
        }

        Index i = new Index(r,c);

        return i;
    }

    // | = Exercise e - set
    public void set(int r, int c, int value) {
        // todo

        if(r < 0 || r > rows || c < 0 || c > rows)
        {
            throw new RuntimeException("Indices fuera de rango");
        }

        Index i = index(r,c);

        update(i,value);

    }

    // | = Exercise f - get
    public int get(int r, int c) {
        // todo

        if(r < 0 || r > rows || c < 0 || c > rows)
        {
            throw new RuntimeException("Indices fuera de rango");
        }

        Index i = index(r,c);

        return value(i);
    }

    // | = Exercise g - add
    public static SparseMatrix add(SparseMatrix m1, SparseMatrix m2) {
        // todo

        int filas = Math.max(m1.rows, m2.rows);
        int colum = Math.max(m1.columns, m2.columns);
        Dictionary<Index, Integer> dict = new HashDictionary<>();

        for(Tuple2<Index, Integer> x : m1.nonZeros.keysValues())
        {
            dict.insert(x._1(), x._2());
        }

        for(Tuple2<Index, Integer> x : m2.nonZeros.keysValues())
        {
            dict.insert(x._1(), x._2());
        }

        SparseMatrix sm = new SparseMatrix(filas, colum);
        sm.nonZeros = dict;

        return sm;
    }

    // | = Exercise h - transpose
    public SparseMatrix transpose() {
        // todo

        SparseMatrix s = new SparseMatrix(this.columns, this.rows);

        for(Tuple2<Index, Integer> x : nonZeros.keysValues())
        {
            Index i = x._1();
            Index iBueno = new Index(i.getColumn(), i.getRow());

            s.nonZeros.insert(iBueno, x._2());

        }

        return s;
    }

    // | = Exercise i - toString
    public String toString() {
        // todo

        String cad = "";
        int [] [] matriz = new int[rows+1][columns+1];

        for(Tuple2<Index, Integer> x : nonZeros.keysValues() )
        {
            matriz [x._1().getRow()] [x._1().getColumn()] = x._2();
        }


        for(int i=0; i < rows+1; i++)
        {
            for(int j=0; j<columns+1; j++)
            {
                cad = cad + matriz[i][j] + " ";
            }
            cad = cad + "\n";
        }


        return cad;
    }

    // | = Exercise j - fromList and fromList2
    // Complexity using get and ArrayList:
    // Complexity using get and LinkedList:
    public static SparseMatrix fromList(int r, int c, List<Integer> list) {
        // todo

        if(list.size() % 3 != 0)
        {
            throw new RuntimeException("La lista no es multiplo de 2");
        }

        List<Integer> l = list;
        SparseMatrix s = new SparseMatrix(r,c);

        while(!l.isEmpty())
        {
            int r1 = l.get(0);
            l.remove(0);
            int c1 = l.get(0);
            l.remove(0);
            int valor = l.get(0);
            l.remove(0);

            s.set(r1,c1,valor);
        }


        return s;
    }

    // Complexity using iterator and ArrayList:
    // Complexity using iterator and LinkedList:
    public static SparseMatrix fromList2(int r, int c, List<Integer> list) {
        // todo

        if(list.size() % 3 != 0)
        {
            throw new RuntimeException("La lista no es multiplo de 2");
        }

        SparseMatrix s = new SparseMatrix(r,c);
        Iterator<Integer> iterator = list.iterator();

        while(iterator.hasNext())
        {
            int r1 = iterator.next();
            int c1 = iterator.next();
            int valor = iterator.next();
            s.set(r1,c1,valor);
        }

        return s;
    }
}
