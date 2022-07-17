package DatabaseHandler;

import Song.Song;

import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

public class DBHandler {

    private Connection con;
    private String url;
    private Properties prop;

    public DBHandler(Connection con, String url, Properties prop){
        this.con = con;
        this.url = url;
        this.prop = prop;
    }

    public boolean execute_insert_query(String[] values){
        String query = "insert into song values (?, ?, ?, ?)";
        try{
            PreparedStatement statement = con.prepareStatement(query);
            System.out.println(Arrays.toString(values));
            statement.setString(1, values[0]);
            statement.setString(2, values[1]);
            statement.setInt(4, Integer.parseInt(values[2]));
            statement.setString(3, values[3]);

            System.out.println("Statement: " + statement.toString());

            ResultSet rSet = statement.executeQuery();
        }catch(SQLException sqle){sqle.printStackTrace();return false;}
        return true;
    }

    public boolean execute_select_query(String[] params){
       String query = "select name from song where author = ?";
       try{
            PreparedStatement statement = con.prepareStatement(query);
            System.out.println(Arrays.toString(params));
            statement.setString(1, params[2]);
            System.out.println("Statement: " +statement.toString());
            ResultSet rSet = statement.executeQuery();

            while(rSet.next()){
                System.out.println("Song name: "+rSet.getString(1));
            }

       }catch(SQLException sqle){sqle.printStackTrace(); return false;}
       return true;
    }

}
