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
		/*
		String [] parts = fullSentence.split("\\."); // Burada noktayı kaldırıyorlar. Onun yerine tüm punctutationlar kaldırılmalı.
		sentenceList = new ArrayList<Sentence>();

		for(int i = 0; i < parts.length; i++) {
			String[] words = parts[i].split("\\s+"); // cumleyi kelime kelime ayırıyorlar.

			for (int j = 0; j< words.length; j++) {
				// You   may want to check for a non-word character before blindly
				// performing a replacement
				// It may also be necessary to adjust the character class
				words[j] = words[j].replaceAll("[^\\w]", "");
			}

			Sentence wordiex = new Sentence(words, url);

			try{
				int content_id = dbService.saveFullSentence(wordiex);
				dbService.saveShiftedSentences(wordiex.getShiftedSentences(), content_id);
			}catch(SQLException exception){ //Eğer bu hata yenirse db de eklenemedi tarzından uyarı verilecek. ama sistem çaısmaya devam edecek
				exception.printStackTrace();
			}

			sentenceList.add(wordiex);
		}


		for(int i = 0; i < sentenceList.size(); i++) {
			searchEngine.addSentence(sentenceList.get(i));
		}

		//searchEngine.listItems();
		searchEngine.sort();

		for(int i = 0; i < searchEngine.getCountSort(); i++) {
			OutputScreenController outputController = new OutputScreenController();

			outputController.addOutputText(searchEngine.getIndex(i), url);
		}

		searchEngine.clearAllFields(); // searcEngine'i countsort ve count degiskenlerinden kurtarırsak buna gerek kalmayabilir gerci arraylistlerini gene temizlememiz gerek o yüzden bu gerekli

		*/

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

	public void sortArrayCreate() {
	    for(int i = 0; i < count; i++) {
			for(int j = 0; j < sentences.get(i).getWordCount(); j++) {
				sorted.add("");
				sorted.set(countsort, sorted.get(countsort).concat(sentences.get(i).getShiftedSentence(j)));
				countsort++;
			}
		}
		
	}
	public void sort() {
		sortArrayCreate();
		char temp[] = new char[countsort];

		for(int i = 0; i < countsort; i++) {
			temp[i] = sorted.get(i).toLowerCase().charAt(1);
		}

		int n = countsort;
		  
		// One by one move boundary of unsorted subarray
		for (int i = 0; i < n-1; i++) {
			// Find the minimum element in unsorted array
			int min_idx = i;

			for (int j = i+1; j < n; j++) {
				int s = temp[j];
				int o = temp[min_idx];
		            	
				if (s < o) {
					min_idx = j;
				}
			}

			// Swap the found minimum element with the first
			// element
			char temp2 = temp[min_idx];

			temp[min_idx] =temp[i];
			temp[i] = temp2;
		            
			String temp3 = sorted.get(min_idx);

            sorted.set(min_idx, sorted.get(i));
            sorted.set(i, temp3);
		}
	}
	
	public void addSentence(Sentence  sentencex) {
		sentences.add(sentencex);
		count++;
	}
	
	public HashMap<String, ArrayList<String>> searchUrl(String searchSentence) {
        String[] words = searchSentence.split(" ");

        DbService db = new DbService();

        HashMap<String, ArrayList<String>> searchResult = null;

        try{
            searchResult = db.searchUrlBySentence(words);
        }catch(SQLException e){
            e.printStackTrace();
        }

        return searchResult;
	}

	public ArrayList<String> getAllIndexesWithUrl(){
		DbService db = new DbService();

		ArrayList<String> indexes = null;

		try{
			indexes = db.getAllIndexesWithUrl();
		}catch(SQLException e){
			e.printStackTrace();
		}

		return indexes;
	}
	
	public String getIndex(int i) {
		return sorted.get(i);
	}

	public int getCountSort() {
		return countsort;
	}

	public void clearAllFields(){
	    sentences = new ArrayList<Sentence>();
	    sorted = new ArrayList<String>();
	    count = 0;
	    countsort = 0;
    }
}
