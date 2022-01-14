package MainPac;

import Dictionaries.Dictionary;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("hello");
        test();
    }
    public static void test () throws FileNotFoundException {
        Dictionary dc = new Dictionary();
        Scanner inputscan = new Scanner(System.in);
        dc.printMeaningWithIndex(inputscan.nextInt());
    }
}
