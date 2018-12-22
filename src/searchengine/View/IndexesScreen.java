package searchengine.View;

import javax.swing.*;
import java.awt.*;

public class IndexesScreen {
    private static IndexesScreen screen;

    private JPanel indexesPanel;

    private IndexesScreen(){
        indexesPanel = new JPanel();
        indexesPanel.setLayout(new GridLayout(20,1));
    }

    public static IndexesScreen getScreen(){
        if(screen == null){
            screen = new IndexesScreen();
        }

        return screen;
    }

}
