package sdf.day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class CountWordsFromFile {

    public static void main(String[] args) throws Exception {
    // Open the file
    FileReader fr = new FileReader(args[0]);
    BufferedReader br = new BufferedReader(fr);

    String line;
    int sum = 0;

    //Intuitive method
    while ((line = br.readLine()) != null) {
        if (line.length() > 0) {
            String[] words = line.split(" ");
            sum += words.length;
        }
    }
    
    System.out.printf("The total number of words is %d\n", sum);
    br.close();

    //Stream method
    sum = 0;
    FileReader nfr = new FileReader(args[0]);
    BufferedReader nbr = new BufferedReader(nfr);
    List<String> list = new LinkedList<>();
    while ((line = nbr.readLine()) != null) {
        if (line.length() > 0) {
        list.add(line);
        }
    }
    sum = 
        list.stream()
            // .filter(v -> (!v.startsWith("")))
            .map(v -> {          
                String[] words = v.split(" ");
                return words.length;
            })
            .reduce(0, (acc, v) -> acc + v); 
    

    System.out.printf("The total number of words is %d", sum);
    nbr.close();
      
   }
   
}