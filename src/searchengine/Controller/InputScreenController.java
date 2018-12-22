package searchengine.Controller;

import searchengine.Sentence;
import searchengine.View.SearchScreen;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class InputScreenController {

    public InputScreenController(){

    }

    public void addSearchButtonActionListener(){
        SearchScreen screen = SearchScreen.getScreen();

        screen.getSearchButton().addActionListener(e -> {
            sentenceList = new ArrayList<Sentence>();

            outputPanel.removeAll(); // Cikti ekrani temizlenir

            String url = urlField.getText();
            fullSentence = sentenceField.getText();
            String [] parts = fullSentence.split("\\."); // Burada noktayı kaldırıyorlar. Onun yerine tüm punctutationlar kaldırılmalı.
            //System.out.println(String.join(" ", parts));
            for(int i = 0; i < parts.length; i++) {
                String[] words = parts[i].split("\\s+"); // cumleyi kelime kelime ayırıyorlar.
                for (int j = 0; j< words.length; j++) {
                    // You may want to check for a non-word character before blindly
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
            /*��kt� sayfas�nda for i�inde print i�lemini buraya yap*/
            for(int i = 0; i < searchEngine.getCountSort(); i++) {
                JTextField text3 = new JTextField();
                text3.setBackground(Color.gray);
                text3.setText("Index: " + searchEngine.getIndex(i) + " Url: " + url);
                text3.setEditable(false);

                outputPanel.add(text3);
                outputPanel.revalidate();
                outputPanel.repaint();
            }

            searchEngine.clearAllFields(); // searcEngine'i countsort ve count degiskenlerinden kurtarırsak buna gerek kalmayabilir gerci arraylistlerini gene temizlememiz gerek o yüzden bu gerekli

        });
    }
}
