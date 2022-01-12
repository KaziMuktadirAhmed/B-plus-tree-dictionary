package Dictionaries;

import java.util.*;
import java.io.*;

public class Dictionary {
    public ArrayList <WordMeaning> wordList = new ArrayList<>();

    public Dictionary () throws FileNotFoundException {
        createDictionary("dictionary.txt");
    }

    public Dictionary (String filepath) throws FileNotFoundException {
        createDictionary(filepath);
    }

    public void createDictionary (String filepath) throws  FileNotFoundException {
        File inputFile = new File(filepath);
        Scanner scanFile = new Scanner(inputFile);

        String notParsedLine;
        ArrayList <String> record = new ArrayList<>();

        while (scanFile.hasNextLine()) {
            notParsedLine = scanFile.nextLine();
            record.addAll(List.of(notParsedLine.split("[|]",0)));
            wordList.add(new WordMeaning(record.get(1), record.get(2)));
            record.clear();
        }
    }

    public void printMeaningWithIndex (int i) {
        wordList.get(i).print();
    }
}
