package BPlusTree;

public class TreeNode extends Node{
    private int minSlot;
    private int maxSlot;
    private int currentChildCount;

    // still unsure about this two
    private TreeNode leftSibling;
    private TreeNode rightSibling;

    public String[] keys;
    public Node[] childs;

    public TreeNode (int n, String[] keys) {
        this.maxSlot = n;
        this.minSlot = (int) Math.ceil(n/2.0);
        this.currentChildCount = 0;
        this.keys = keys;
        this.childs = new Node[this.maxSlot+1];
    }
    public TreeNode (int n, String[] keys, Node[] childs) {
        this.maxSlot = n;
        this.minSlot = (int) Math.ceil(n/2.0);
        this.keys = keys;
        this.currentChildCount = BPlusTree.linierLNullSearch(keys);
        this.childs = childs;
    }

    public boolean isFull () { return (this.currentChildCount == this.maxSlot); }
    public boolean isOverfull () { return (this.currentChildCount > this.maxSlot); }
    public boolean isDeficient () { return (this.currentChildCount < this.minSlot); }
    public boolean isLendable () { return (this.currentChildCount > this.minSlot); }
    public boolean isMergeable () { return (this.currentChildCount == this.minSlot); }

    public void appendChild (Node child) {
        this.childs[this.currentChildCount] = child;
        this.currentChildCount++;
    }

    public void preprendChild (Node child) {
        for (int i = this.currentChildCount -1; i>=0; i--) { this.childs[i+1] = this.childs[i]; }
        this.childs[0] = child;
        this.currentChildCount++;
    }

    public void inserChild (Node child, int index) {
        for (int i = this.currentChildCount -1; i>=index; i--) { this.childs[i+1] = this.childs[i]; }
        this.childs[index] = child;
        this.currentChildCount++;
    }

    public int findChildIndex (Node child) {
        for (int i=0; i<this.childs.length; i++) {
            if (child == this.childs[i])    return i;
        }
        return -1;
    }

    public void removeKey (int index) {
        this.keys[index] = null;
    }

    public void removeChild (int index) {
        this.childs[index] = null;
        this.currentChildCount--;
    }

    public void removeChild (Node child) {
        for (int i = 0; i<this.currentChildCount; i++) {
            if (this.childs[i] == child) { this.childs[i] = null; }
        }
        this.currentChildCount--;
    }
}
























