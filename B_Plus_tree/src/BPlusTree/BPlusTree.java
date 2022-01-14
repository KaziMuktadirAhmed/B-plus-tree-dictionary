package BPlusTree;

import Dictionaries.Dictionary;
import Dictionaries.WordMeaning;

import java.io.FileNotFoundException;

public class BPlusTree {
    private Dictionary dictionary = new Dictionary();
    public static int n = 5;

    public TreeNode root;
    public LeafNode firstLeaf;

    public  BPlusTree () throws FileNotFoundException {}
    public  BPlusTree (int n) throws FileNotFoundException {
        this.n = n;
        this.root = null;
//        this.firstLeaf = new LeafNode();
    }

    private int searchInsideNodes (WordMeaning[] wordMeanings, String target) {
        int targetIndex = -1;
        for (int i=0; i<wordMeanings.length; i++) {
            if (target.compareTo(wordMeanings[i].engWord) == 0) { targetIndex = i; }
        }
        return targetIndex;
    }

    private LeafNode findLeafNode (TreeNode startNode, String key) {
        String[] keys = startNode.keys;
        int i;

        for (i=0; i<startNode.currentChildCount-1; i++) {
            if (key.compareTo(startNode.keys[i]) < 0) { break; }
        }

        Node child = startNode.childs[i];
        if (child instanceof LeafNode) {
            return (LeafNode) child;
        } else {
            return findLeafNode((TreeNode) child, key);
        }
    }

    private LeafNode findLeafNode (String key) {
        String[] keys = this.root.keys;
        int i;

        for (i=0; i<this.root.currentChildCount-1; i++) {
            if (key.compareTo(keys[i]) < 0) { break; }
        }

        Node child = this.root.childs[i];
        if (child instanceof LeafNode) {
            return (LeafNode) child;
        } else {
            return findLeafNode((TreeNode) child, key);
        }
    }

    private int findChildIndex (Node[] childs, LeafNode target) {
        int i;
        for (i=0; i<childs.length; i++) {
            if (childs[i] == target) { break; }
        }
        return  i;
    }

    private int getMidPoint () { return (int) Math.ceil((this.n + 1)/2.0) - 1; }
    private boolean isEmpty() { return (firstLeaf == null); }

    public static int linierLNullSearch (WordMeaning[] wms) {
        for (int i = 0; i <  wms.length; i++)
            if (wms[i] == null) { return i; }
        return -1;
    }

    public static int linierLNullSearch (String[] keys) {
        for (int i = 0; i <  keys.length; i++)
            if (keys[i] == null) { return i; }
        return -1;
    }

    public static int linierLNullSearch (Node[] childs) {
        for (int i = 0; i <  childs.length; i++)
            if (childs[i] == null) { return i; }
        return -1;
    }
}
