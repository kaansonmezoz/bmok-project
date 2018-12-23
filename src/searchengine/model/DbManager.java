package searchengine.model;


import java.sql.*;


public class DbManager {

    private static DbManager dbManager;

    private final String JDBC_DRIVER;
    private final String DB_URL;
    private final String USER;
    private  final String PASS;

    private Connection conn;

    private DbManager(){
        JDBC_DRIVER = "com.mysql.jdbc.Driver";
        DB_URL = "jdbc:mysql://localhost/BLM_3110";
        USER = "root";
        PASS = "";

        init();
    }

    public static DbManager getDbManager(){
        if(dbManager == null){
            dbManager = new DbManager();
        }

        return dbManager;
    }

    public void init(){
        try {
            Class.forName(JDBC_DRIVER);
            this.conn = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ResultSet executeQuery (String query) {
        ResultSet resultSet = null;
        try{
            Statement stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet executeOperation (String query) {
        ResultSet resultSet = null;
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            resultSet = stmt.getGeneratedKeys();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet executeOperation (PreparedStatement statement ) {
        ResultSet resultSet = null;
        try{
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return resultSet;
    }


    public PreparedStatement prepareStatement(String query) throws SQLException{
        return conn.prepareStatement(query);
    }

    public Connection getConn() {
        return conn;
    }

}
