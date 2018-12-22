package searchengine.View;

import javax.swing.*;
import java.awt.*;
//TODO: aslında screenler en ustte bir base classi extend etse abstract bir classi nihayetinde alayinda constructor calisacak ortak olan ise getScreen() tarzinda bir metod var polymorphism yapıbilir belki de bu sayede daha mantıklı bir cikarim yapılabilir genelleştirilebilecek ne gibi özellikleri var bilmiyorum ama
public class SearchScreen {
    private static SearchScreen searchScreen;
    private JPanel searchPanel;

    private JTextField searchField;

    private JScrollPane jScrollPane1;
    private JList<String> searchResults;

    private JButton searchButton;
    // Singleton class design pattern --> ne de olsa sadece bir tane panel olacak bu şekilde
    private SearchScreen(){
        searchPanel = new JPanel(new BorderLayout());
        searchResults = new JList<String>();
        searchField = new JTextField("Aranacak kelimeleri giriniz");
        jScrollPane1 = new JScrollPane();
        searchButton = new JButton("Ara");

        jScrollPane1.setViewportView(searchResults);

        searchPanel.add(searchField, BorderLayout.PAGE_START);
        searchPanel.add(searchResults, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.LINE_END);
    }

    public static SearchScreen getScreen(){
        if(searchScreen == null){
            searchScreen = new SearchScreen();
        }

        return searchScreen;
    }


    public JPanel getSearchPanel(){
        return getSearchPanel();
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public JList<String> getSearchResults() {
        return searchResults;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public void setSearchButton(JButton searchButton) {
        this.searchButton = searchButton;
    }

    public void setSearchPanel(JPanel searchPanel) {
        this.searchPanel = searchPanel;
    }

    public void setSearchField(JTextField searchField) {
        this.searchField = searchField;
    }

    public void setSearchResults(JList<String> searchResults) {
        this.searchResults = searchResults;
    }

}
