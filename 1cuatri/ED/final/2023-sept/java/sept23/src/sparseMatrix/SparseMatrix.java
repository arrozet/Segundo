/******************************************************************************
 * Student's name: ?????????????????????????????????????
 * Identity number (DNI if Spanish/passport if Erasmus): ???????????????????
 * Student's group: ?
 * PC code: ???
 *
 * Data Structures. Grados en Informatica. UMA.
 *****************************************************************************/

package sparseMatrix;

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.List;

    // | = Exercise a - SparseMatrix constructor
public class SparseMatrix {
    public final int rows;
    public final int columns;
    private Dictionary<Index, Integer> nonZeros;

    public SparseMatrix(int r, int c) {
        nonZeros = new AVLDictionary<>();
        rows = r;
        columns = c;
    }

    // | = Exercise b - value
    private int value(Index ind) {
        if(nonZeros.isDefinedAt(ind)){
            return nonZeros.valueOf(ind);
        }
        else{
            return 0;
        }
    }

    // | = Exercise c - update
    private void update(Index ind, int value) {
        nonZeros.insert(ind,value);
    }

    // | = Exercise d - index
    private Index index(int r, int c) {
        if(r>=rows || r<0 || c>=columns || c<0){
            throw new IllegalArgumentException("index: index is not valid");
        }
        return new Index(r,c);
    }

    // | = Exercise e - set
    public void set(int r, int c, int value) {
        Index index = index(r,c);
        this.update(index, value);
    }

    // | = Exercise f - get
    public int get(int r, int c) {
        Index index = index(r,c);
        return this.value(index);
    }

    // | = Exercise g - add
    public static SparseMatrix add(SparseMatrix m1, SparseMatrix m2) {
        SparseMatrix sm = new SparseMatrix(m1.rows, m1.columns);

        for(Index i : m1.nonZeros.keys()){
            sm.set(i.getRow(), i.getColumn(), m1.value(i) + m2.value(i));
        }

        return sm;
    }

    // | = Exercise h - transpose
    public SparseMatrix transpose() {
        SparseMatrix sm = new SparseMatrix(this.rows, this.columns);

        for(Index i : this.nonZeros.keys()){
            sm.set(i.getColumn(), i.getRow(), this.value(i));
            //this.nonZeros.delete(i);
        }

        return sm;
    }

    // | = Exercise i - toString
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i<rows; i++){
            for(int j = 0; j<columns; j++){
//                sb.append();

//                while(sb.toString().length() % 6 != 0){
//                    sb.append(" ");
//                }
                sb.append(String.format("%6d",this.value(index(i,j))));

            }
            sb.append('\n');
        }

        return sb.toString();
    }

    // | = Exercise j - fromList and fromList2
    // Complexity using get and ArrayList:
    // Complexity using get and LinkedList:
    public static SparseMatrix fromList(int r, int c, List<Integer> list) {
        if(list.size() % 3 != 0){
            throw new IllegalArgumentException("fromList: list is not multiple of 3");
        }
        SparseMatrix sm = new SparseMatrix(r,c);
        return auxFromList(sm,list);
    }

    private static SparseMatrix auxFromList(SparseMatrix sm, List<Integer> l){
        if(l.isEmpty()){
            return sm;
        }
        else{
            int f = l.get(0);
            l.remove(0);
            int c = l.get(0);
            l.remove(0);
            int v = l.get(0);
            l.remove(0);
            sm.set(f,c,v);

            return auxFromList(sm, l);
        }
    }

    // Complexity using iterator and ArrayList:
    // Complexity using iterator and LinkedList:
    public static SparseMatrix fromList2(int r, int c, List<Integer> list) {
        // todo
        return null;
    }
}
