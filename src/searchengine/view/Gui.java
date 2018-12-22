package searchengine.view;

import javax.swing.*;

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
    }
}
