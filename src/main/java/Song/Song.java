package Song;
import java.util.Date;

public class Song {
    private static long serialVersionUID = 1;

    private String code;
    private String name;
    private int pub_date;
    private String author;
    private String album;
    private String majority_genre;
    private String minority_genre;

    public Song(String code){
        this.code = code;
    }

    public Song(String code, String majority_genre, String minority_genre){
        this.code = code;
        this.majority_genre = majority_genre;
        this.minority_genre = minority_genre;
    }

    public Song(int pub_date, String code, String author, String name){
        this.code = code;
        this.name = name;
        this.pub_date = pub_date;
        this.author = author;
    }

    public String getCode(){return this.code;}
    public String getName(){return this.name;}
    public int getPub_date(){return this.pub_date;}
    public String getAuthor(){return this.author;}

    public String getAlbum(){return this.album;}
    public void setAlbum(String album){this.album = album;}

    public String getMajority_genre(){return this.majority_genre;}
    public String getMinority_genre(){return this.minority_genre;}

    public void setMajority_genre(String majority_genre){this.majority_genre = majority_genre;}
    public void setMinority_genre(String minority_genre){this.minority_genre = minority_genre;}


    public String[] getGenres(){
        if(this.minority_genre != null){
            String[] genres =  new String[2];
            genres[0] = this.majority_genre;
            genres[1] = this.minority_genre;
            return genres;
        }
        String[] genres = new String[1];
        genres[0] = this.majority_genre;
        return genres;
    }

    public String[] getValues(){
        String[] values = new String[4];
        values[0] = code;
        values[1] = name;
        values[2] = String.valueOf(pub_date);
        values[3] = author;
        return values;
    }

    public boolean equals(Song otherSong){
        //TODO
        return false;
    }

    public String toString(){
        if(this.getMinority_genre() != null)
            return this.getName() + " " + this.getAuthor() + " " + this.getCode() + " " + this.getPub_date() + " " + this.getMajority_genre() + " " + this.getMinority_genre();
        else
            return this.getName() + " " + this.getAuthor() + " " + this.getCode() + " " + this.getPub_date() + " " + this.getMajority_genre();
    }


}
