package searchengine.controller;

import searchengine.view.OutputScreen;

import javax.swing.*;
import java.awt.*;

public class OutputScreenController {
    private OutputScreen screen;

    public OutputScreenController(){
        screen = OutputScreen.getScreen();
    }

    public void clearOutputScreen(){
        screen.getOutputPanel().removeAll();
    }

    public void addOutputText(String url, String index){
        JTextField outputText = new JTextField();
        JPanel outputPanel;

        outputText.setBackground(Color.gray);
        outputText.setText(index + " =====> " + url);
        outputText.setEditable(false);

        outputPanel = screen.getOutputPanel();

        outputPanel.add(outputText);
        outputPanel.revalidate();
        outputPanel.repaint();
    }

    public 
}
