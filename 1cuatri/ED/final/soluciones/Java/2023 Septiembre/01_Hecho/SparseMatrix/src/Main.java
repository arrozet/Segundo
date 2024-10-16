import dataStructures.list.ArrayList;
import dataStructures.list.List;
import sparseMatrix.SparseMatrix;


public class Main {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for(int i : new int[]{2,0,3, 1,2,4, 3,1,-1, 1,1,2, 1,2,-1, 0,0,2, 1,2,-3, 3,1,1})
            list.append(i);

        List<Integer> list2 = new ArrayList<>();
        for(int i : new int[]{0,0,3, 1,2,2, 1,3,-1, 1,1,8, 0,0,-1, 0,1,1})
            list2.append(i);
        
        SparseMatrix m = SparseMatrix.fromList2(4,5,list);
        SparseMatrix m2 = SparseMatrix.fromList(3,3,list2);

        System.out.println(m);
        System.out.println(m2);
        System.out.println(SparseMatrix.add(m,m));
        System.out.println(m.transpose());
    }
}
