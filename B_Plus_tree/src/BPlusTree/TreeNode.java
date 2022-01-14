package BPlusTree;

public class TreeNode extends Node{
    private int minSlot;
    private int maxSlot;
    private int currentKeyCount;

    // still unsure about this two
    private TreeNode leftSibling;
    private TreeNode rightSibling;

    public String[] keys;
    public Node[] childs;

    public TreeNode (int n, String[] keys) {
        this.maxSlot = n;
        this.minSlot = (int) Math.ceil(n/2.0);
        this.currentKeyCount = 0;
        this.keys = keys;
        this.childs = new Node[this.maxSlot+1];
    }
    public TreeNode (int n, String[] keys, Node[] childs) {
        this.maxSlot = n;
        this.minSlot = (int) Math.ceil(n/2.0);
        this.keys = keys;
        this.currentKeyCount = BPlusTree.linierLNullSearch(keys);
        this.childs = childs;
    }

    public boolean isFull () { return (this.currentKeyCount == this.maxSlot); }
    public boolean isOverfull () { return (this.currentKeyCount > this.maxSlot); }
    public boolean isDeficient () { return (this.currentKeyCount < this.minSlot); }
    public boolean isLendable () { return (this.currentKeyCount > this.minSlot); }
    public boolean isMergeable () { return (this.currentKeyCount == this.minSlot); }

}
