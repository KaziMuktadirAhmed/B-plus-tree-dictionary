package BPlusTree;

import Dictionaries.Dictionary;
import Dictionaries.WordMeaning;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;

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

    private void sortWords (WordMeaning[] wordMeanings) {
        Arrays.sort(wordMeanings, new Comparator<WordMeaning>() {
            @Override
            public int compare(WordMeaning o1, WordMeaning o2) {
                if (o1 == null && o2 == null) { return 0; }
                else if (o1 == null) { return 1; }
                else if (o2 == null) { return -1; }
                return o1.compareTo(o2);
            }
        });
    }

    private Node[] splitChildsFromTreeNode (TreeNode treeNode, int splitPoint) {
        Node[] initialChilds = treeNode.childs;
        Node[] halfChilds = new Node[this.n + 1];

        for (int i=splitPoint+1; i<initialChilds.length; i++) {
            halfChilds[i - splitPoint - 1] = initialChilds[i];
            treeNode.removeChild(i);
        }

        return halfChilds;
    }

    private String[] splitKeysFromTreeNode (TreeNode treeNode, int splitPoint) {
        String[] initialKeys = treeNode.keys;
        String[] halfKeys = new String[this.n];

        for (int i=splitPoint+1; i<initialKeys.length; i++) {
            halfKeys[i - splitPoint - 1] =initialKeys[i];
            treeNode.removeKey(i);
        }
        
        return halfKeys;
    }

    private void splitTreeNode (TreeNode treeNode) {
        TreeNode parent = treeNode.parent;

        int midpoint = getMidPoint();
        String newParentKey = treeNode.keys[midpoint];
        String[] halfKeys = splitKeysFromTreeNode(treeNode, midpoint);
        Node[] halfChilds = splitChildsFromTreeNode(treeNode, midpoint);

        treeNode.currentChildCount = linierLNullSearch(treeNode.childs);

        TreeNode newSiblingNode = new TreeNode(this.n, halfKeys, halfChilds);
        for (int i=0; i<halfChilds.length; i++) {
            if(halfChilds[i] != null) { halfChilds[i].parent = newSiblingNode; }
        }

        treeNode.rightSibling = newSiblingNode;
        newSiblingNode.leftSibling = treeNode;

        if (parent == null) {
            String[] keys = new String[this.n];
            keys[0] = newParentKey;
            TreeNode newRoot = new TreeNode(this.n, keys);
            newRoot.appendChild(treeNode);
            newRoot.appendChild(newSiblingNode);

            this.root = newRoot;
            treeNode.parent = newRoot;
            newSiblingNode.parent = newRoot;
        }
        else {
            parent.keys[parent.currentChildCount-1] = newParentKey;
            Arrays.sort(parent.keys, 0, parent.currentChildCount);

            int newChildIndex = parent.findChildIndex(treeNode) + 1;
            parent.inserChild(newSiblingNode, newChildIndex);

            newSiblingNode.parent = parent;
        }
    }

    private WordMeaning[] splitWordsFromLeafNode (LeafNode leafNode, int splitPoint) {
        WordMeaning[] initialWords = leafNode.wordMeanings;
        WordMeaning[] halfWords = new WordMeaning[this.n];

        for (int i=splitPoint; i<initialWords.length; i++) {
            halfWords[i - splitPoint] = initialWords[i];
            leafNode.delete(i);
        }

        return halfWords;
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
