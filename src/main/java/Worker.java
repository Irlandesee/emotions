import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import Song.Song;

public class Worker extends Thread{

    private ConcurrentHashMap<String, Song> songsWithGenre;
    private ConcurrentHashMap<String, Song> songs;

    private ConcurrentHashMap<String, Song> songsFound;

    public Worker(ConcurrentHashMap<String, Song> songs, ConcurrentHashMap<String, Song> songsWithGenre){
        this.songsWithGenre = songsWithGenre;
        this.songs = songs;
        songsFound = new ConcurrentHashMap<String, Song>();
    }

    public void run(){
        for(String s : songs.keySet()){
            if(songsWithGenre.containsKey(s)){
                Song songWithGenre = songsWithGenre.get(s);
                Song res = songs.get(s);
                res.setMajority_genre(songWithGenre.getMajority_genre());
                res.setMinority_genre(songWithGenre.getMinority_genre());
                songsFound.put(s, res);
            }
        }

    }

    public ConcurrentHashMap<String, Song> getSongsFound(){
        return this.songsFound;
    }

}
