package searchengine.view;

import searchengine.controller.IndexesScreenController;
import searchengine.controller.InputScreenController;
import searchengine.controller.OutputScreenController;
import searchengine.controller.SearchScreenController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Gui extends JFrame{
    private static Gui gui;

    private IndexesScreen indexesScreen;
    private InputScreen inputScreen;
    private OutputScreen outputScreen;
    private SearchScreen searchScreen;

    private JTabbedPane tappedPane;

    public Gui(){
        setTitle("Metin Indeksleme");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tappedPane = new JTabbedPane();

        initScreens();

        tappedPane.add(searchScreen.getSearchPanel(), "Arama");
        tappedPane.add(inputScreen.getInputPanel(),"Metin Girisi");
        tappedPane.add(outputScreen.getOutputPanel(),"Cıktı");
        tappedPane.add(indexesScreen.getIndexesPanel(),"Index goruntuleme");

        add(tappedPane);
    }

    private void initScreens(){
        indexesScreen = IndexesScreen.getScreen();
        inputScreen = InputScreen.getScreen();
        outputScreen = OutputScreen.getScreen();
        searchScreen = SearchScreen.getScreen();
    }

    public static void start(){
        if(gui == null){
            gui = new Gui();
        }
        gui.setSize(1500, 600);
        gui.setVisible(true);

        InputScreenController inputScreenController = new InputScreenController();
        inputScreenController.addSearchButtonActionListener();

        SearchScreenController searchScreenController = new SearchScreenController();
        searchScreenController.addSearchButtonActionListener();
    }

    private void addTappedPaneOnChange(){
        tappedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) { // burada tablar arasında geçiş yapıldığında verilerin silinme islemi yapilmasi gerekiyor
                SearchScreenController searchScreenController = new SearchScreenController();
                InputScreenController inputScreenController = new InputScreenController();
                OutputScreenController outputScreenController = new OutputScreenController();
                IndexesScreenController indexesScreenController = new IndexesScreenController();

                String activeTabTitle = tappedPane.getTitleAt(tappedPane.getSelectedIndex());

                System.out.println(activeTabTitle);
                // Search Ekranı temizleniyor
                if(activeTabTitle != tappedPane.getTitleAt(0)){
                    searchScreenController.clearScreen();
                }

                if(activeTabTitle != tappedPane.getTitleAt(1)) { // Index olusturma ekranını baslangica dondurmek icin
                    inputScreenController.clearScreen();
                }

                if(activeTabTitle == tappedPane.getTitleAt(3)){ // Index goruntuleme ekrani
                    indexesScreenController.clearOutputScreen();
                    indexesScreenController.outputAllIndexesWithUrl();
                }
            }
        });

    }
}
