import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import DatabaseHandler.DBHandler;
import Song.Song;
import Song.Genre;
import emotion.Emotion;

import java.sql.*;

public class Main {

    private final static String pathToData = "/Users/mattiamac/Documents/GitHub/emotions/data/";
    private final static String firstGenreFile = "msd_tagtraum_cd1.cls";
    private final static String secondGenreFile = "msd_tagtraum_cd2.cls";
    private final static String thirdGenreFile = "msd_tagtraum_cd2c.cls";

    private static Connection getConnection() throws SQLException{
        final String url = "jdbc:postgresql://localhost/emotions";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "qwerty");

        return DriverManager.getConnection(url, props);
    }

    private static ConcurrentHashMap<String, Song> getSongsFromFile(String path, String fileName){
        File inputFile = new File(path, fileName);
        ConcurrentHashMap<String, Song> dataMap = new ConcurrentHashMap<String, Song>();
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(inputFile));
            String s = "";
            while((s = bReader.readLine()) != null){
                String[] tokens = s.split("<SEP>");
                dataMap.put(tokens[1], new Song(Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3]));
            }
            bReader.close();
        }catch(IOException ioe){ioe.printStackTrace();}
        return dataMap;
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
            bReader.close();
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
            bReader.close();
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

    private static String hashString(String s){
        String hashedString = "";
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(s.getBytes("UTF-8"));
            BigInteger bi = new BigInteger(1, digest);
            hashedString = bi.toString(16);
        }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){e.printStackTrace();}
        return hashedString;
    }


    //returns a linked list containing Strings like:
    //track_id genre_1 genre_2
    private static LinkedList<String> readTrackGenre(String path, String filename){
        LinkedList<String> tracksWithGenre = new LinkedList<String>();
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(new File(path, filename)));
            String s = "";
            while((s = bReader.readLine()) != null){
                tracksWithGenre.add(s);
            }
        }catch(IOException ioe){ioe.printStackTrace();}
        return tracksWithGenre;
    }

    private static Song setSongGenres(Song s, String[] values){
        //TODO
        return null;
    }



    private static ConcurrentHashMap<String, Song> buildSongWithGenre(String path, String filename){
        ConcurrentHashMap<String, Song> songsWithGenre = new ConcurrentHashMap<String, Song>();
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(new File(path, filename)));
            String s = "";
            while((s = bReader.readLine()) != null){
                String[] values = s.split("\t");
                if(values.length > 2){
                    Song song = new Song(values[0], values[1], values[1]);
                    songsWithGenre.put(values[0], song);
                }else{
                    Song song = new Song(values[0], values[1], "");
                    songsWithGenre.put(values[0], song);
                }
            }
            bReader.close();
        }catch(IOException ioe){ioe.printStackTrace();}
        return songsWithGenre;
    }

    private static ConcurrentHashMap<String, Song> readSongsFile(String path, String filename){
        ConcurrentHashMap<String, Song> songs = new ConcurrentHashMap<String, Song>();
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(new File(path, filename)));
            String line = "";
            while((line = bReader.readLine()) != null){
                String[] values = line.split( ",");
                Song s = new Song(values);
                songs.put(s.getCode(), s);
            }
        }catch(IOException ioe){ioe.printStackTrace();}
        return songs;
    }


    private static ConcurrentHashMap<String, Genre> readGenreFile(String path, String filename){
        ConcurrentHashMap<String, Genre> genres = new ConcurrentHashMap<String, Genre>();

        File inputFile = new File(path + filename);
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(inputFile));
            String s = "";
            while((s = bReader.readLine()) != null){
                String[] values = s.split("	");
                String code = values[0];
                String majority_genre = values[1];
                String minority_genre = "";
                if(values.length > 2){ // majority genre & minority genre
                    genres.put(code, new Genre(code, majority_genre, values[2]));
                }else{
                    genres.put(code, new Genre(code, majority_genre, minority_genre));
                }
            }
        }catch(IOException ioe){ioe.printStackTrace();}

        return genres;
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

        final String pathToDataDirectory = "/Users/mattiamac/Documents/Github/emotions/data/";
        File input_cd1 = new File(pathToDataDirectory+ "msd_tagtraum_cd1.cls");
        File input_cd2 = new File(pathToDataDirectory+ "msd_tagtraum_cd2.cls");


        ConcurrentHashMap<String, Song> songs = readSongsFile(pathToData, "complete_songs.csv");
        //for(Song s : songs.values()) System.out.println(s.toString());


        ConcurrentHashMap<String, Genre> genreMap_1 =
                readGenreFile(pathToDataDirectory, "msd_tagtraum_cd1.cls");
        ConcurrentHashMap<String, Genre> genreMap_2 =
                readGenreFile(pathToDataDirectory, "msd_tagtraum_cd2.cls");

        int commonSongs_1= 0;
        int commonSongs_2 = 0;
        for(String key : songs.keySet()){
            if(genreMap_1.containsKey(key)){
                System.out.println("Found song in genre1:\n"+songs.get(key).toString());
                commonSongs_1++;
            }
            else if(genreMap_2.containsKey(key)){
                System.out.println("Found song in genre2:\n"+songs.get(key).toString());
                commonSongs_2++;
            }
        }

        System.out.println("Total songs: " + songs.size());
        System.err.println("Common songs in 1: " +commonSongs_1);
        System.err.println("Common songs in 2: " +commonSongs_2);


        System.err.println("Done");


    }
}
