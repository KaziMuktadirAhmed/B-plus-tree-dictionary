package BPlusTree;

import Dictionaries.WordMeaning;

import java.util.Arrays;

public class LeafNode extends Node{
    public int maxSlot;
    public int minSlot;
    public int currentWordCount;

    public LeafNode leftSibling;
    public LeafNode rightSibling;

    public WordMeaning[] wordMeanings;

    public LeafNode (int n, WordMeaning[] keys, TreeNode parent) {
        this.maxSlot = n - 1;
        this.minSlot = (int) (Math.ceil(n/2) - 1);
        this.wordMeanings = keys;
        this.currentWordCount = BPlusTree.linierLNullSearch(keys);
        this.parent = parent;
    }
    public LeafNode (int n, WordMeaning wm) {
        this.maxSlot = n - 1;
        this.minSlot = (int) (Math.ceil(n/2) - 1);
        this.currentWordCount = 0;
        this.wordMeanings = new WordMeaning[n];
        this.insert(wm);
    }

    public boolean isFull () { return (this.currentWordCount == this.maxSlot); }
    public boolean isOverfull () { return (this.currentWordCount > this.maxSlot); }
    public boolean isDeficient () { return (this.currentWordCount < this.minSlot); }
    public boolean isLendable () { return (this.currentWordCount > this.minSlot); }
    public boolean isMergeable () { return (this.currentWordCount == this.minSlot); }

    public boolean insert (WordMeaning wm) {
        if(this.isFull()) return false;
        else {
            this.wordMeanings[currentWordCount] = wm;
            this.currentWordCount++;
            Arrays.sort(this.wordMeanings, 0, this.currentWordCount);
            return true;
        }
    }

    public void delete (int index) {
        this.wordMeanings[index] = null;
        currentWordCount--;
    }
}
