package Dictionaries;

public class WordMeaning {
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
}
