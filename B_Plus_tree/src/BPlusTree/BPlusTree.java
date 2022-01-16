package BPlusTree;

import Dictionaries.WordMeaning;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;

public class BPlusTree {
//    private Dictionary dictionary = new Dictionary();
    public int n = 5;

    public TreeNode root;
    public LeafNode firstLeaf;

//    public  BPlusTree () throws FileNotFoundException {}
    public  BPlusTree (int n) throws FileNotFoundException {
        this.n = n;
        this.root = null;
//        this.firstLeaf = new LeafNode();
    }

    private int searchInsideNodes (WordMeaning[] wordMeanings, int listSize, String target) {
        int targetIndex = -1;
        for (int i=0; i<listSize; i++) {
            if (target.compareTo(wordMeanings[i].engWord) == 0) { targetIndex = i; }
        }
        return targetIndex;
    }

    private LeafNode findLeafNode (TreeNode startNode, String key) {
//        String[] keys = startNode.keys;
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
            halfKeys[i - splitPoint - 1] = initialKeys[i];
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
            parent.insertChild(newSiblingNode, newChildIndex);

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

    /**
      * API function: Search
      * @param key:The key to be searched within the B+ tree
      * The functions outputs a line in the console containing the key-value pair
      */
    public void search (String key) {
        if (isEmpty()) {
            System.out.println("Could not find word in dictionary");
            return;
        }

        LeafNode leafNode;
        if (this.root == null) leafNode = this.firstLeaf;
        else leafNode = findLeafNode(key);

        WordMeaning[] words = leafNode.wordMeanings;
        int index = searchInsideNodes(words, leafNode.currentWordCount, key);

        if (index < 0)
            System.out.println("Could not find word in dictionary");
        else
            words[index].print();
    }

    /**
     * API function: Insert
     * @param key: A String key to be used to search the inserted value in the tree
     * @param value: the value associated with the given key
     * Given a key-value pair the function performs insertion on the B+ tree
     * @return true if insert was successful false if insert failed
     */
    public boolean insert (String key, String value) {
        boolean insertSuccess = false;

        if (isEmpty()) {

            // Create a new Leaf Node if the tree is empty

            this.firstLeaf = new LeafNode(this.n, new WordMeaning(key, value));
            insertSuccess = true;

        } else {

            // Finds leaf node to insert the key-value pair

            LeafNode leafNode;
            if (this.root == null) leafNode = this.firstLeaf;
            else leafNode = findLeafNode(key);

            /*
              Inserts the key-value pair in the B+ tree.
              Insertion fails if the leaf node is full
              The if claus handles the splitting and modification of the nodes
             */
            if (!leafNode.insert(new WordMeaning(key, value))) {

                // Insert the new key-value pair and sort the whole word list in the leaf node

                leafNode.wordMeanings[leafNode.currentWordCount] = new WordMeaning(key, value);
                leafNode.currentWordCount++;
                sortWords(leafNode.wordMeanings);

                // Split the sorted words into two halves

                int midpoint = getMidPoint();
                WordMeaning[] halfWords = splitWordsFromLeafNode(leafNode, midpoint);

                /*
                  If the parent of the leaf node is null it means the tree has only one node
                  while inserting this leaf will be splitted into two leaf node
                  a new tree node will be created to become the parent of the two leaf node
                 */
                if (leafNode.parent == null) {
                    String[] parent_Keys = new String[this.n];
                    parent_Keys[0] = halfWords[0].engWord;
                    TreeNode parent = new TreeNode(this.n, parent_Keys);
                    leafNode.parent = parent;
                    parent.appendChild(leafNode);

                /*
                * If parent exists then
                * the first word from right sibling is inserted in the parent as key
                * */
                } else {
                    String newParentKey = halfWords[0].engWord;
                    leafNode.parent.keys[leafNode.parent.currentChildCount-1] = newParentKey;
                    Arrays.sort(leafNode.parent.keys, 0, leafNode.parent.currentChildCount);
                }

                // Create a new leaf node to hold the latter half of word list
                LeafNode siblingLeafNode = new LeafNode(this.n, halfWords, leafNode.parent);

                // Update the child list on parent node
                int siblingChildIndex = leafNode.parent.findChildIndex(leafNode) + 1;
                leafNode.parent.insertChild(siblingLeafNode, siblingChildIndex);

                // Make the two leaf nodes siblings of one another
                siblingLeafNode.rightSibling = leafNode.rightSibling;
                if (siblingLeafNode.rightSibling != null) {
                    siblingLeafNode.rightSibling.leftSibling = siblingLeafNode;
                }
                leafNode.rightSibling = siblingLeafNode;
                siblingLeafNode.leftSibling = leafNode;

                /*
                * Sets up parent for the two new leaf nodes
                * modifies the intermediate tree nodes if root exists
                * If not then sets the parent as the root of the tree
                * */
                if (this.root == null) {
                    this.root = leafNode.parent;

                    /*
                    * If parent is overfull then
                    * Split tree node into two and insert the first key in parent
                    * repeat the process until no is deficiency found
                    * */
                } else {
                    TreeNode treeNode = leafNode.parent;

                    // Goes up until the root of the tree is reached
                    while (treeNode != null) {
                        if (treeNode.isOverfull()) {
                            splitTreeNode(treeNode);
                        } else {
                            break;
                        }
                        treeNode = treeNode.parent;
                    }
                }
            }
        }

        return insertSuccess;
    }
}





































