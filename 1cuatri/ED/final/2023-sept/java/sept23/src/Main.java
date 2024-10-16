import dataStructures.list.ArrayList;
import dataStructures.list.List;
import sparseMatrix.SparseMatrix;


public class Main {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
//        for(int i : new int[]{2,0,3, 1,2,4, 3,1,-1, 1,1,2, 1,2,-1, 0,0,2, 1,2,-3, 3,1,1})
//            list.append(i);
        for(int i : new int[]{0,1,11, 3,0,30, 3,1,31})
            list.append(i);
        
        SparseMatrix m = SparseMatrix.fromList(5,5,list);

        System.out.println(m);
        System.out.println(SparseMatrix.add(m,m));
        System.out.println(m.transpose());
    }
}
