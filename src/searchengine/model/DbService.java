package searchengine.model;

import searchengine.Sentence;
import searchengine.ShiftedSentence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DbService {
    private DbManager dbManager;

    public DbService(){
        dbManager = DbManager.getDbManager();

        if(!isTablesExists()){
            createTables();
        }
    }

    private boolean isTablesExists(){
        boolean finded = false;
        try{
            ResultSet result = dbManager.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE 'Indexes'");
            if(result.next()){
                if(result.getRow() < 2){
                    finded = true;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return finded;
    }
    private void createTables(){
        ResultSet result = null;

        try{
            result = dbManager.executeOperation("CREATE TABLE `Indexes` " +
                    "( `id` INT NOT NULL AUTO_INCREMENT, " +
                    "`processed_content` TEXT NOT NULL , " +
                    "`orig_content_id` INT NOT NULL , " +
                    "`target_word` TEXT NOT NULL , " +
                    "`point` FLOAT NOT NULL , " +
                    "PRIMARY KEY (`id`)) ENGINE = InnoDB;");

            result = dbManager.executeOperation("CREATE TABLE `Contents_of_Indexes` " +
                    "( `id` INT NOT NULL AUTO_INCREMENT, " +
                    "`content` LONGTEXT NOT NULL , " +
                    "`url` TEXT NOT NULL , " +
                    "PRIMARY KEY (`id`)) ENGINE = InnoDB;");
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}

