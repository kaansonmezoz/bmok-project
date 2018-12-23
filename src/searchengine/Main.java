package searchengine;

import searchengine.model.DbService;
import searchengine.view.Gui;

public class Main {
    public static void main(String[] args){
        DbService dbService = new DbService();
        Gui.start();
    }
}
