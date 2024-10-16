/*
Expected output:

Tree(Node<Node<Node<null,18,null>,20,Node<null,25,null>>,30,Node<Node<null,50,null>,60,Node<null,70,null>>>)
makeRoot 25: Tree(Node<Node<Node<null,18,null>,20,null>,25,Node<null,30,Node<Node<null,50,null>,60,Node<null,70,null>>>>)
makeRoot 60: Tree(Node<Node<Node<Node<null,18,null>,20,Node<null,25,null>>,30,Node<null,50,null>>,60,Node<null,70,null>>)

 */
public class DemoTree {

    public static void main(String[] args) {

        int[] xs = new int[]{30, 20, 18, 25, 60, 50, 70};

        Tree<Integer> tree1 = new Tree<>();
        for (Integer x : xs)
            tree1.insert(x);
        System.out.println(tree1);

        tree1.makeRoot(25);
        System.out.println("makeRoot 25: " + tree1);

        tree1 = new Tree<>();
        for (Integer x : xs)
            tree1.insert(x);

        tree1.makeRoot(60);
        System.out.println("makeRoot 60: " + tree1);
    }
}
