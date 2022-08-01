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

        ConcurrentHashMap<String, Song> songsMap = getSongsFromFile(pathToData, "data.txt");

        ConcurrentHashMap<String, Song> first = buildSongWithGenre(pathToData, firstGenreFile);
        ConcurrentHashMap<String, Song> second = buildSongWithGenre(pathToData, secondGenreFile);
        ConcurrentHashMap<String, Song> third = buildSongWithGenre(pathToData, thirdGenreFile);

        Worker w1 = new Worker(songsMap, first);
        Worker w2 = new Worker(songsMap, second);
        Worker w3 = new Worker(songsMap, third);

        System.err.println("launching 3 threads");
        w1.start();
        w2.start();
        w3.start();

        try{
            w1.join();
            w2.join();
            w3.join();
        }catch(InterruptedException ie){ie.printStackTrace();}

        System.err.println("Threads done");

        ConcurrentHashMap<String, Song> songsFound_1 = w1.getSongsFound();
        ConcurrentHashMap<String, Song> songsFound_2 = w2.getSongsFound();
        ConcurrentHashMap<String, Song> songsFound_3 = w3.getSongsFound();

        ConcurrentHashMap<String, Song> completeSongs = new ConcurrentHashMap<String, Song>();
        for(String s : songsFound_1.keySet()) completeSongs.put(s, songsFound_1.get(s));
        for(String s : songsFound_2.keySet()) completeSongs.put(s, songsFound_2.get(s));
        for(String s : songsFound_3.keySet()) completeSongs.put(s, songsFound_3.get(s));


        

        System.err.println("Done");


    }
}
