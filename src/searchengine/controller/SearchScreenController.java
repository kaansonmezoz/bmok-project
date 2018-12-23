package searchengine.controller;

import searchengine.model.Contents;
import searchengine.view.SearchScreen;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchScreenController {
    private SearchScreen screen;

    public SearchScreenController(){
        screen = SearchScreen.getScreen();
    }

    public void addSearchButtonActionListener(){
        JButton searchButton = screen.getSearchButton();
        JTextField searchField = screen.getSearchField();
        JList<String> searchResultField = screen.getSearchResults();

        searchButton.addActionListener( e -> {
            String searchSentence = searchField.getText().toString();

            ArrayList<String> searchResult = searchUrl(searchSentence);

            if(searchResult == null){
                return;
            }

            searchResultField.removeAll();
            searchResultField.setListData(searchResult.toArray(new String[0]));
        });
    }


    public ArrayList<String> searchUrl(String searchSentence) {
        ArrayList<String> results = null;
        String[] words = searchSentence.split(" ");
        Contents contentsModel = new Contents();

        try{
            results = contentsModel.searchUrlBySentence(words);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return results;
    }
}
