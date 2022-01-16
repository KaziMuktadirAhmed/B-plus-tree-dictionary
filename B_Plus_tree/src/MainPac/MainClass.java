package MainPac;

import BPlusTree.BPlusTree;
import Dictionaries.Dictionary;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) throws FileNotFoundException {
//        System.out.println("hello");
//        test();
        run();
    }
    public static void test () throws FileNotFoundException {
        String str = "keyword";
        System.out.println(str.compareTo("keyword"));
    }
    
    public static void run () throws FileNotFoundException {
        Dictionary dictionary = new Dictionary();
        BPlusTree bPlusTree = new BPlusTree(10);

        for (int i=0; i<dictionary.wordList.size(); i++) {
            String key = dictionary.wordList.get(i).engWord;
            String value = dictionary.wordList.get(i).bngMeaning;
            bPlusTree.insert(key, value);
        }

        Scanner scanInput = new Scanner(System.in);

        System.out.println("Input keyword for search: ");
        String keyInput = scanInput.nextLine();

        bPlusTree.search(keyInput);
    }
}
