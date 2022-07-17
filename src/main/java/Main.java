import java.io.*;
import java.util.LinkedList;

import DatabaseHandler.DBHandler;
import Song.Song;
import java.sql.*;
import java.util.Properties;

public class Main {

    private static Connection getConnection() throws SQLException{
        final String url = "jdbc:postgresql://localhost/emotions";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "qwerty");

        return DriverManager.getConnection(url, props);
    }

    private static LinkedList<Song> getSongsFromFile(String path, String fileName){
        File inputFile = new File(path, fileName);
        LinkedList<Song> data = new LinkedList<Song>();
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(inputFile));
            String s = "";
            while((s = bReader.readLine()) != null){
                String[] tokens = s.split("<SEP>");
                data.add(new Song(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3]));
            }
        }catch(IOException ioe){ioe.printStackTrace();}
        return data;
    }

    public static void main(String args[]) throws IOException, SQLException {
        String fileName = "data.txt";
        final String url = "jdbc:postgresql://localhost/emotions";
        File inputFile = new File("/Users/mattiamac/Documents/GitHub/emotions/data/", fileName);
        System.out.println(inputFile.getName());
        String user = "postgres";
        String password = "qwerty";
        Properties prop = new Properties();
        prop.setProperty("user", "postgres");
        prop.setProperty("password", "qwerty");
        //LinkedList<Song> songs = getSongsFromFile("/Users/mattiamac/Documents/GitHub/emotions/data/", fileName);
        /**
        try{
            DBHandler dbHandler = new DBHandler(getConnection(), url, prop);
            for(Song s : songs){
                dbHandler.execute_insert_query(s.getValues());
                //String[] values = s.getValues();
                //for(String value : values) System.out.println(value);
            }
        }catch(SQLException sqle){sqle.printStackTrace();}
        **/

        try{
            DBHandler dbHandler = new DBHandler(getConnection(), url, prop);
            String[] params = {"name", "author", "Blind Lemon Jefferson"};
            dbHandler.execute_select_query(params);

            System.out.println("query complete.");
        }catch(SQLException sqle){sqle.printStackTrace();}


    }
}
