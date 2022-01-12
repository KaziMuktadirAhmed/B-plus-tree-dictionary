package MainPac;

import java.io.FileNotFoundException;
import java.util.*;
import Dictionaries.*;
import Dictionaries.Dictionary;

public class MainClass {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("hello");
        test();
    }
    public static void test () throws FileNotFoundException {
        Dictionary dc = new Dictionary();

        Scanner scan = new Scanner(System.in);
        String str = new String();
        str = scan.nextLine();
        System.out.println("Scanned: "+str+" ইংরেজী বর্ণমালার আদ্যাক্ষর");

        dc.test_from_file();
    }
}
