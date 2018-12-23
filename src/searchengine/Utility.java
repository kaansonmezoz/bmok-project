package searchengine;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Utility {

    public static void removeStopWords(ArrayList<String> words){
        String[] stopWords = new String[]{"vs","a","the","of","from","to", "someone"};

        ArrayList<String> stopWordsList = new ArrayList<>(Arrays.asList(stopWords));

        for(int i = 0; i < words.size(); i++){
            if(stopWordsList.indexOf(words.get(i)) != -1){
                words.remove(i);
            }
        }
    }

    public static void clearPunctionMarks(ArrayList<String> words){
        String[] punctionMarks = new String[]{",", ".", "!", "?", ":", "\"", ";", "-"};

        for(int i = 0; i < words.size(); i++){
            for(int j = 0; j < punctionMarks.length; j++){
                words.set(i, words.get(i).replaceAll(punctionMarks[j], "")); // cumleler arasinda whitespace oldugu dusunuldu.
            }
        }
    }
}
