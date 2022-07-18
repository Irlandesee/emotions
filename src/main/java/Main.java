import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;

import DatabaseHandler.DBHandler;
import Song.Song;
import emotion.Emotion;

import java.sql.*;
import java.util.Properties;
import java.util.Random;

public class Main {

    private final static String pathToData = "/Users/mattiamac/Documents/GitHub/emotions/data/";

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


    private static LinkedList<String> getEmotionsFromFile(String path, String filename){
        File inputFile = new File(path, filename);
        LinkedList<String> emotions = new LinkedList<String>();
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(inputFile));
            String s = "";
            while((s = bReader.readLine()) != null){
                emotions.add(s);
            }
        }catch(IOException ioe){ioe.printStackTrace();}
        return emotions;
    }

    private static boolean findEmotion(LinkedList<String> encodedEmotions, String encodedEmotion){
        if(encodedEmotions.contains(encodedEmotion)) return true;
        return false;
    }

    private static LinkedList<String> hashEmotions(LinkedList<String> emotions) throws NoSuchAlgorithmException{
        LinkedList<String> hashedEmotions = new LinkedList<String>();
        while(!emotions.isEmpty()){
            String md5 = "";
            String emotion = emotions.pop();
            MessageDigest digest = MessageDigest.getInstance("MD5");
            try{
                digest.update(emotion.getBytes("UTF-8"));;
            }catch(UnsupportedEncodingException uee){uee.printStackTrace();}
            byte[] md5digest = digest.digest();
            BigInteger bi = new BigInteger(1, md5digest);
            md5 = bi.toString(16);
            hashedEmotions.add(md5);
        }
        return hashedEmotions;
    }

    private static void write_file(String path, String filename, LinkedList<String> data){
        File outputFile = new File(path, filename);
        try{
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(outputFile));
            while(!data.isEmpty()){
                String s = data.pop();
                bWriter.write(s);
                bWriter.write("\n");
            }
            bWriter.close();
        }catch(IOException ioe){ioe.printStackTrace();}
        System.out.println("done");;
    }

    private static LinkedList<Emotion> readCompleteEmotions(String path, String filename, String regex){
        LinkedList<Emotion> emotions = new LinkedList<Emotion>();
        File inputFile  = new File(path, filename);
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(inputFile));
            String s = "";
            while((s = bReader.readLine()) != null){
                String[] data = s.split(regex);
                Emotion e = new Emotion(data[0], data[1], data[2], data[3]);
                emotions.add(e);
            }
        }catch(IOException ioe){ioe.printStackTrace();}
        return emotions;
    }

    private static String hashEmotion(String emotion) throws NoSuchAlgorithmException{
        String hashedEmotion = "";
        MessageDigest md = MessageDigest.getInstance("MD5");
        try{
            byte[] digest = md.digest(emotion.getBytes("UTF-8"));
            BigInteger bi = new BigInteger(1, digest);
            hashedEmotion = bi.toString(16);
        }catch(UnsupportedEncodingException uee){uee.printStackTrace();}

        return hashedEmotion;
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

        /**
        try{
            DBHandler dbHandler = new DBHandler(getConnection(), url, prop);
            String[] params = {"name", "author", "Blind Lemon Jefferson"};
            dbHandler.execute_select_query(params);

            System.out.println("query complete.");
        }catch(SQLException sqle){sqle.printStackTrace();}
        **/
        /**
        LinkedList<String> emotionsFromFile = getEmotionsFromFile(pathToData, "emotions.txt");
        LinkedList<String> encodedEmotionsFromFile = getEmotionsFromFile(pathToData, "hashed_emotions.txt");
        String randomEmotion = emotionsFromFile.get(new Random().nextInt(emotionsFromFile.size()) - 1);
        try{
            String encodedRandomEmotion = hashEmotion(randomEmotion);
            System.out.println("Random emotion: " + randomEmotion);
            System.out.println("Encoded Random emotion: " + encodedRandomEmotion);
            boolean found = findEmotion(encodedEmotionsFromFile, encodedRandomEmotion);
            if(!found) System.out.println("NoSuchEmotion");
            else System.out.println("Emotion found");
        }catch(NoSuchAlgorithmException nsae){nsae.printStackTrace();}
         **/

        LinkedList<Emotion> emotions = readCompleteEmotions(pathToData, "emotion_complete.csv", ",");
        for(Emotion e : emotions){
            try{
                DBHandler dbHandler = new DBHandler(getConnection(), url, prop);
                dbHandler.execute_insert_emotions(e.getValues());
            }catch(SQLException sqle){sqle.printStackTrace();}
        }

        System.out.println("Done");


    }
}
