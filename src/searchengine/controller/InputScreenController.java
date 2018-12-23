package searchengine.controller;

import searchengine.Engine;
import searchengine.Sentence;
import searchengine.view.InputScreen;
import searchengine.view.OutputScreen;
import searchengine.view.SearchScreen;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

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

            engine.createIndex(url, fullSentence);
        });
    }
}
