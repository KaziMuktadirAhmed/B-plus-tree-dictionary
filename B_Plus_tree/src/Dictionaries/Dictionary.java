package Dictionaries;

import java.util.*;
import java.io.*;

public class Dictionary {
    public ArrayList <ArrayList<String>> wordList = new ArrayList<ArrayList<String>>();
    public ArrayList<String> record = new ArrayList<String>();

    public void test_from_file () throws FileNotFoundException {
//        Scanner scan;
        String str = new String();
        File inputFile = new File("dictionary.txt");
        Scanner scan = new Scanner(inputFile);

        while (scan.hasNextLine()) {
            str = scan.nextLine();
            System.out.println(str);
        }
    }

    public void test () {
        Scanner scan = new Scanner(System.in);
        String str = new String();

        str = scan.nextLine();
        record.add(str);
        record.add("Intake 1");
        wordList.add(record);

        for (int i=0; i<wordList.size(); ++i) {
            for (int j=0; j<wordList.get(i).size(); ++j) {
                System.out.print(wordList.get(i).get(j));
            }
        }
    }
}
