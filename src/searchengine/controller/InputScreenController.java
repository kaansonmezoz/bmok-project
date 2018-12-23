package searchengine.controller;

import searchengine.Engine;
import searchengine.Sentence;
import searchengine.ShiftedSentence;
import searchengine.model.Contents;
import searchengine.model.Indexes;
import searchengine.view.InputScreen;
import searchengine.view.OutputScreen;
import searchengine.view.SearchScreen;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class InputScreenController {

    public InputScreenController(){

    }

    public void addSearchButtonActionListener(){
        InputScreen screen = InputScreen.getScreen();

        screen.getCreateButton().addActionListener(e -> {

            OutputScreenController outputController = new OutputScreenController();
            outputController.clearOutputScreen(); // Cikti ekrani temizlenir

            String url = screen.getUrlField().getText();
            String fullSentence = screen.getSentenceField().getText();

            Engine engine = new Engine();

            Sentence processedSentence = engine.createIndex(url, fullSentence);

            saveInput(processedSentence);

            processedSentence.getShiftedSentences().sort(new Comparator<ShiftedSentence>() {    // Sorting in an ascending order ignoring case sensitive
                @Override
                public int compare(ShiftedSentence o1, ShiftedSentence o2) {
                    return o1.getProcessedSentence().toLowerCase().compareTo(o2.getProcessedSentence().toLowerCase());
                }
            });

            int size = processedSentence.getShiftedSentences().size();

            for(int i = 0; i < size; i++){
                outputController.addOutputText(processedSentence.getShiftedSentence(i), url);
            }
        });
    }

    public void saveInput(Sentence sentence){
        Indexes indexesModel = new Indexes();
        Contents contentsModel = new Contents();

        try{
            int content_id = contentsModel.saveContent(sentence);
            indexesModel.saveShiftedSentences(sentence.getShiftedSentences(), content_id);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }
}
