package Song;

public class Genre {

    private String id;
    private String name;

    public Genre(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId(){return this.id;}
    public String getName(){return this.name;}

    public String toString(){
        return this.id + " " + this.name;
    }

}
