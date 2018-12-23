package searchengine.controller;

import searchengine.model.Indexes;
import searchengine.view.IndexesScreen;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class IndexesScreenController {
    IndexesScreen screen;

    public IndexesScreenController(){
        screen = IndexesScreen.getScreen();
    }

    private void addOutputText(String output){
        System.out.println(output.length());

        JTextField text4 = new JTextField();
        text4.setBackground(Color.gray);
        text4.setText(output);
        text4.setEditable(false);

        screen.getIndexesPanel().add(text4);
        screen.getIndexesPanel().revalidate();
        screen.getIndexesPanel().repaint();
    }

    public void outputAllIndexesWithUrl(){
        ArrayList<String> outputList = getAllIndexesWithUrls();

        if(outputList == null){
            return;
        }

        for(int i = 0 ; i < outputList.size(); i++) {
            addOutputText(outputList.get(i));
        }
    }

    public ArrayList<String> getAllIndexesWithUrls(){
        Indexes indexesModel = new Indexes();

        ArrayList<String> result = null;

        try{
            result = indexesModel.getAllIndexesWithUrl();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    public void clearOutputScreen(){
        screen.getIndexesPanel().removeAll();
    }
}
