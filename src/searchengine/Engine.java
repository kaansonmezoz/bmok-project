package searchengine;

import searchengine.controller.OutputScreenController;
import searchengine.model.DbService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Engine {
	private ArrayList<Sentence> sentences;
    private ArrayList<String> sorted;

	private int count;
	private int countsort;

	public Engine() {
        sentences = new ArrayList<Sentence>();
		sorted = new ArrayList<String>();
        count = 0;
		countsort = 0;
	}

	public Sentence createIndex(String url, String fullSentence){
		ArrayList<String> words = getIndexWords(fullSentence);

        ArrayList<ShiftedSentence> shiftedSentence = shiftSentence(words);

        return new Sentence(words.size(), url, shiftedSentence, fullSentence);
	}
	//TODO: şuanda indeksler oluşturuldu bunnlar bir db'ye kaydedilmeli ve ekrana basilmali bir tek buralar kaldi

	private ArrayList<String> getIndexWords(String fullSentence){
        String[] words =  fullSentence.split(" ");
	    ArrayList<String> indexWords = new ArrayList<String>(Arrays.asList(words));

	    Utility.removeStopWords(indexWords);
        Utility.clearPunctionMarks(indexWords);

        return indexWords;
    }

    private ArrayList<ShiftedSentence> shiftSentence(ArrayList<String> words){
        ArrayList<ShiftedSentence> sentences = new ArrayList<ShiftedSentence>();

        for(int i = 0; i < words.size(); i++){
            ShiftedSentence shifted = new ShiftedSentence(String.join(" ", words), calculateScore(i, words.size()));

            sentences.add(shifted);
            words = shiftSentenceLeft(words);
        }

        return sentences;
    }

    private float calculateScore(int shiftNo, int wordLength){
	    return (float)1 / (wordLength * (shiftNo + 1));
    }

    private ArrayList<String> shiftSentenceLeft(ArrayList<String> words){
	    ArrayList<String> shifted = new ArrayList<String>(words);

	    shifted.add(shifted.remove(0));

	    return shifted;
    }
}
