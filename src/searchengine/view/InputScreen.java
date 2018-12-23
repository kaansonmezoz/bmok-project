package searchengine.view;

import searchengine.controller.InputScreenController;

import javax.swing.*;
import java.awt.*;

public class InputScreen {
    private static InputScreen screen;

    private InputScreenController controller;

    private JPanel inputPanel;
    private JTextField urlField;
    private JTextField sentenceField;
    private JButton createButton;

    private InputScreen(){
        inputPanel = new JPanel(new BorderLayout());
        urlField = new JTextField("Url giriniz");
        sentenceField = new JTextField("Metin giriniz");
        createButton = new JButton("olustur");

        inputPanel.add(urlField,BorderLayout.PAGE_START);
        inputPanel.add(sentenceField,BorderLayout.CENTER);
        inputPanel.add(createButton,BorderLayout.PAGE_END);

        controller = new InputScreenController();
        controller.addSearchButtonActionListener();
    }

    public static InputScreen getScreen(){
        if(screen == null){
            screen = new InputScreen();
        }

        return screen;
    }

    public static void setScreen(InputScreen screen) {
        InputScreen.screen = screen;
    }

    public JPanel getInputPanel() {
        return inputPanel;
    }

    public void setInputPanel(JPanel inputPanel) {
        this.inputPanel = inputPanel;
    }

    public JTextField getUrlField() {
        return urlField;
    }

    public void setUrlField(JTextField urlField) {
        this.urlField = urlField;
    }

    public JTextField getSentenceField() {
        return sentenceField;
    }

    public void setSentenceField(JTextField sentenceField) {
        this.sentenceField = sentenceField;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public void setCreateButton(JButton createButton) {
        this.createButton = createButton;
    }
}
