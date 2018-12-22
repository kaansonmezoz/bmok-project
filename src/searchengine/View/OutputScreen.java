package searchengine.View;

import javax.swing.*;
import java.awt.*;

public class OutputScreen {
    private static OutputScreen screen;

    private JPanel outputPanel;

    private OutputScreen(){
        outputPanel = new JPanel();
        outputPanel.setLayout(new GridLayout(20,1));
    }

    public static OutputScreen getScreen(){
        if(screen == null){
            screen = new OutputScreen();
        }

        return screen;
    }

    public static void setScreen(OutputScreen screen) {
        OutputScreen.screen = screen;
    }

    public JPanel getOutputPanel() {
        return outputPanel;
    }

    public void setOutputPanel(JPanel outputPanel) {
        this.outputPanel = outputPanel;
    }
}
