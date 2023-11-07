package sdf.aimodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TrainAI {

    private Map<String, Map<String, Integer>> wordMap = new HashMap<>();

    public Map<String, Map<String, Integer>> getWordMap() {return this.wordMap;}

    public TrainAI(String fileDir) throws IOException {
        this.wordMap = train(fileDir);
    }

    FileFilter filter = new FileFilter() { 
        public boolean accept(File f) { 
            return f.getName().endsWith("txt"); 
        } 
    };

    private Map<String, Map<String, Integer>> train(String fileDir) throws IOException {
    
        File dir = new File (fileDir);
        File[] files = dir.listFiles(filter);      
        
        for (File file : files) {
            if (file.isFile()) {

                FileReader fr = new FileReader(file);
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

            }
            
        }
        return wordMap;
    }
}

