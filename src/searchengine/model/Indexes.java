package searchengine.model;

import searchengine.ShiftedSentence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Indexes {
    private DbManager dbManager;

    public Indexes(){
        dbManager = DbManager.getDbManager();
    }

    public void saveShiftedSentences(ArrayList<ShiftedSentence> sentences, int content_id) throws  SQLException{
        String query = "INSERT INTO `Indexes` (`processed_content`, `target_word`, `point`, `orig_content_id`) " +
                "VALUES (?, ?, ?, ?)";

        PreparedStatement statement = dbManager.prepareStatement(query);

        for(int i = 0; i < sentences.size(); i++){
            statement.setString(1, sentences.get(i).getProcessedSentence());
            statement.setString(2, sentences.get(i).getProcessedSentence().split(" ")[0]);
            statement.setFloat(3, sentences.get(i).getScore());
            statement.setInt(4, content_id);

            dbManager.executeOperation(statement);
        }
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
            String result = resultSet.getString(1) + " ===> " + resultSet.getString(2); // Aslında bunun böyle olmaması lazım sadece sonucu döndürmesi lazım ama uğraşamam şuan
            results.add(result);
        }

        return results;
    }

}
