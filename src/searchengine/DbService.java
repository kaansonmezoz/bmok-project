package searchengine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DbService {
    private DbManager dbManager;

    public DbService(){
        dbManager = new DbManager();
        dbManager.init();
        if(!isTablesExists()){
            createTables();
        }
    }

    public void saveShiftedSentences(ArrayList<ShiftedSentence> sentences, int content_id){
        for(ShiftedSentence index: sentences){
            ResultSet result = dbManager.executeOperation("INSERT INTO `Indexes` (`processed_content`, `target_word`, `point`, `orig_content_id`) " +
                    "VALUES ('"+index.getProcessedSentence()+"', '"+index.getProcessedSentence().split(" ")[0]+"', '"+index.getScore()+"', '"+content_id+"')");
        }
    }

    public int saveFullSentence(Sentence sentence) throws SQLException {
        ResultSet result = null;
        try {
            result = dbManager.executeOperation("INSERT INTO `Contents_of_Indexes` (`content`, `url`) " +
                    "VALUES ('" + sentence.getFullSentence() + "', '" + sentence.getUrl() + "')");
            result.next();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result.getInt(1);
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

    public HashMap<String, ArrayList<String>> searchUrlBySentence(String[] words) throws SQLException{
        if (words.length == 0) {
            return null;
        }

        String query = " SELECT x.TOTAL, contents_of_indexes.content, contents_of_indexes.url FROM ( SELECT i.orig_content_id, SUM(i.point) AS TOTAL FROM ( ";

        for (int i = 0; i < words.length - 1; i++){
            query += "SELECT * FROM indexes i WHERE target_word = '" + words[i] + "' UNION ";
        }

        query += "SELECT * FROM indexes i WHERE target_word = '" + words[words.length-1] + "'";

        query += ") i GROUP BY i.orig_content_id ) x INNER JOIN contents_of_indexes ON contents_of_indexes.id = x.orig_content_id ORDER BY x.TOTAL DESC";

        ResultSet resultSet = null;

        try{
            resultSet = dbManager.executeQuery(query);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
        result.put("total", new ArrayList<String>());
        result.put("content", new ArrayList<String>());
        result.put("url", new ArrayList<String>());

        while (resultSet.next()) {
            result.get("total").add(resultSet.getString(1));
            result.get("content").add(resultSet.getString(2));
            result.get("url").add(resultSet.getString(3));
        }

        return result;
    }

    public ArrayList<String> getAllIndexesWithUrl() throws SQLException{
        String query = "SELECT i.processed_content, coi.url FROM indexes i, contents_of_indexes coi " +
                "WHERE coi.id = orig_content_id ORDER BY i.processed_content ASC";

        ResultSet resultSet = null;

        try{
            resultSet = dbManager.executeQuery(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        ArrayList<String> results = new ArrayList<String>();

        while(resultSet.next()){
            String result = "Index: " + resultSet.getString(1) + " Url: " + resultSet.getString(2);
            results.add(result);
        }

        return results;
    }
}
