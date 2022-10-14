package Song;

public class Genre {

    private String id;
    private String name;
    private String subGenre;

    public Genre(String id, String genre, String subGenre){
        this.id = id;
        this.name = genre;
        this.subGenre = subGenre;
    }

    public String getId(){return this.id;}
    public String getName(){return this.name;}

    public String toString(){
        if(!this.subGenre.equals("")) return this.id + " " + this.name + " " + this.subGenre;
        else return this.id + " " + this.name;
    }

}
