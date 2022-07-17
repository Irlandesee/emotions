package Song;
import java.util.Date;

public class Song {
    private static long serialVersionUID = 1;

    private String code;
    private String name;
    private int pub_date;
    private String author;

    public Song(){}

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
        return this.getName() + " " + this.getAuthor() + " " + this.getCode() + " " + this.getPub_date();
    }


}
