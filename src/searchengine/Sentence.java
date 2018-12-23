package searchengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sentence {
	private int wordCount; // Number of words in a sentence
	private String fullSentence;
	private String url;

	private ArrayList<ShiftedSentence> shiftedSentences;

	public Sentence(int wordCount, String url, ArrayList<ShiftedSentence> shiftedSentences, String fullSentence) {
		this.wordCount = wordCount;
		this.url = url;
		this.shiftedSentences = shiftedSentences;
		this.fullSentence = fullSentence;
	}

	public String[] shiftSentenceLeft(String[] words) {
		List<String> wordsList = new ArrayList<>(Arrays.asList(words));
		wordsList.add(wordsList.remove(0));

		return wordsList.toArray(new String[0]);
	}

	public int getWordCount() {
		return wordCount;
	}

	public String getShiftedSentence(int i) {
		return shiftedSentences.get(i).getProcessedSentence();
	}

	public ArrayList<ShiftedSentence> getShiftedSentences() {
		return shiftedSentences;
	}

	public String getFullSentence() {
		return fullSentence;
	}

	public void setFullSentence(String fullSentence) {
		this.fullSentence = fullSentence;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}