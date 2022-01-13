package BPlusTree;

public class TreeNode extends Node{
    private int minNode;
    private int maxNode;
    private int currentNodeCount;

    public String key[] = new String[maxNode-1];
    public Node child[] = new Node[maxNode];

    public TreeNode() {

    }
}
