package searchengine.model;

import searchengine.Sentence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Contents {
    private DbManager dbManager;

    public Contents(){
        dbManager = DbManager.getDbManager();
    }

    public int saveContent(Sentence sentence) throws SQLException {
        String query = "INSERT INTO `Contents_of_Indexes` (`content`, `url`) " +
                "VALUES (?,?)";

        PreparedStatement statement = dbManager.prepareStatement(query);

        statement.setString(1, sentence.getFullSentence());
        statement.setString(2, sentence.getUrl());

        ResultSet result = null;

        try{
            result = dbManager.executeOperation(statement);
            result.next();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return result.getInt(1);
    }

    //TODO: HashMap degil obje dondurmek lazim aslinda
    public ArrayList<String> searchUrlBySentence(String[] words) throws SQLException{
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

        ArrayList<String> resultOutput = new ArrayList<String>();

        while (resultSet.next()) {
            resultOutput.add(resultSet.getString(2) + " ===> " + resultSet.getString(3));
        }

        return resultOutput;
    }

}
