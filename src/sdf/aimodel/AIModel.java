package sdf.aimodel;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AIModel { 

    private static Integer pick(Integer max) {
        Random r = new Random();
        return r.nextInt(max);
    }

    public static boolean getRandomBoolean(float probability) {
        double randomValue = Math.random()*100;  //0.0 to 99.9
        return randomValue <= probability;
    }
    
    private static Map<String, Map<String, Integer>> wordMap = new HashMap<>();
   
    public static void main(String[] args) throws IOException {

        if (args.length > 0) {
            TrainAI trained = new TrainAI(args[0]);
            wordMap = trained.getWordMap();

        } else {
            System.out.println("Please input folder with to train AI with");
            System.exit(0);
        }

        Console cons = System.console();
        String input = "";
        System.out.println("Please enter word to start sentence and number of words in sentence");
        input = cons.readLine("> ");

        while (!input.startsWith("quit")) {

            String request[] = input.trim().split(",");

            if (!wordMap.containsKey(request[0])) {
                input = cons.readLine("Invalid word, please select another word\n> ");
            
            } else if (request.length != 2) {
                input = cons.readLine("Invalid command, try again\n> ");
            
            } else {

                List<String> sentence = new ArrayList<>();
                sentence.add(request[0]);

                int num = Integer.parseInt(request[1].trim());

                for (int i = 0; i < (num-1); i++) {

                    String w = sentence.get(i);
                    Map<String, Integer> words = new HashMap<>();

                    if (wordMap.containsKey(w)) {
                        words = wordMap.get(w);
                    } else {
                        List<String> randWords = new ArrayList<>(wordMap.keySet());
                        String randWord = randWords.get(pick(randWords.size()));
                        words = wordMap.get(randWord);
                    }

                    List<String> wordlist  = new ArrayList<>(words.keySet());
                    List<Integer> intList = new ArrayList<>(words.values());

                    if (getRandomBoolean(80f)) {
                        int maxValue = Integer.MIN_VALUE;
                        for (Integer integer : intList) {
                            if (integer > maxValue) {
                                maxValue = integer;
                            }
                        }
                        
                        List<Integer> valueList = new ArrayList<>();
                        for (Integer integer : intList) {
                            if (integer == maxValue) {
                                valueList.add(intList.indexOf(integer));
                            }
                        }
                        int position = 0;
                        if (valueList.size() > 1) {
                        position = pick(valueList.size());
                        } else {
                        position = valueList.get(0);
                        }

                        sentence.add(wordlist.get(position));
                    } else {
                        sentence.add(wordlist.get(pick(wordlist.size())));
                    }
                }

                for (int a = 0; a < sentence.size(); a++) {
                    System.out.printf("%s ", sentence.get(a));
                }
                
                
            }

            input = cons.readLine("\n> ");
        }
    }          
}


