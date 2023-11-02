package sdf.day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AIModel {

    public static Map<String, Map<String, Integer>> wordMap = new HashMap<>();
    
    public static void main(String[] args) throws Exception{

        FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr); 

        List<String[]> lines = br.lines()
            .map(line -> line.trim().replaceAll("[^\\sa-zA-Z0-9]", "").toLowerCase())
            .filter(line -> line.length() > 0)
            .map(line -> line.split(" "))
            .collect(Collectors.toList());

        br.close();
        
        for (int i = 0; i < lines.size(); i++) {
            String[] line = lines.get(i);
            for (int j = 0; j < line.length; j++) {
                while (j+1 < line.length) {
                    if (wordMap.containsKey(line[j])) {
                        Map<String, Integer> secondMap = wordMap.get(line[j]);
                        if (secondMap.containsKey(line[j+1])) {
                            secondMap.put(line[j+1], secondMap.get(line[j+1])+1);
                        } else {
                            secondMap.put(line[j+1], 1);
                        }
                        wordMap.put(line[j], secondMap);
                    } else {
                        Map<String, Integer> secondMap = new HashMap<>();
                        secondMap.put(line[j+1], 1);
                        wordMap.put(line[j], secondMap);
                    }
                    break;
                }
            }
        }
        
        System.out.println(wordMap);
    }          
}


