package Dictionaries;

public class WordMeaning implements Comparable<WordMeaning>{
    public String engWord;
    public String bngMeaning;

    public WordMeaning () {
        this.engWord = null;
        this.bngMeaning = null;
    }

    public WordMeaning (String engword, String bngmeaning) {
        this.engWord = engword;
        this.bngMeaning = bngmeaning;
    }

    public void print () {
        System.out.println(engWord + " = " + bngMeaning);
    }

    @Override
    public int compareTo(WordMeaning o) {
        int comapareValue = this.engWord.compareTo(o.engWord);
        if (comapareValue  == 0)    return 0;
        else if (comapareValue > 0)     return 1;
        else    return -1;
    }
}
