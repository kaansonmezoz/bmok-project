package searchengine;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Utility {

    public static ArrayList<String> removeStopWords(ArrayList<String> words){
        String[] stopWords = new String[]{"vs","a","the","of","from","to", "someone"};
        ArrayList<String> stopWordsList = new ArrayList<>(Arrays.asList(stopWords));
        ArrayList<String> validWords = new ArrayList<String>();

        for(int i = 0; i < words.size(); i++){
            System.out.println("removeStopWords " + words.get(i));
            System.out.println(stopWordsList.indexOf(words.get(i)) != -1);
            if(stopWordsList.indexOf(words.get(i)) == -1){
                validWords.add(words.get(i));
            }
        }

        return validWords;
    }

    public static void clearPunctionMarks(ArrayList<String> words){ //TODO: burada bir hata var

        for(int i = 0; i < words.size(); i++){
            words.get(i).replaceAll("[^a-zA-Z ]", "");
        }
    }
}
