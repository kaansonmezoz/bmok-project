package searchengine;

import java.sql.SQLException;
import java.util.ArrayList;
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
