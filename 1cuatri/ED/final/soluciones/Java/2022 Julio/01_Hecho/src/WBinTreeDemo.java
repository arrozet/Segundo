import exercises.WBinTree;

public class WBinTreeDemo {

    public static void main(String[] args) {
        WBinTree<Character> wtree = new WBinTree<>();
        String s = "KNTUH";
        for (int i = 0; i < s.length(); i++) {
            wtree.insert(s.charAt(i));
        }
        System.out.println(wtree);
        // Node(5, K, Node(2, N, Node(1, U, null, null), null), Node(2, T, Node(1, H, null, null), null))
        
        System.out.println(wtree.isWeightBalanced()); // true
    }
}
