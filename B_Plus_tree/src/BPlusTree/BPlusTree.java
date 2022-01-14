package BPlusTree;

import Dictionaries.Dictionary;
import Dictionaries.WordMeaning;

import java.io.FileNotFoundException;

public class BPlusTree {
    private Dictionary dictionary = new Dictionary();
    public static int n = 5;

    public Node root;
    public LeafNode firstLeaf;

    public  BPlusTree () throws FileNotFoundException {}
    public  BPlusTree (int n) throws FileNotFoundException {
        this.n = n;
        this.root = null;
//        this.firstLeaf = new LeafNode();
    }

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
