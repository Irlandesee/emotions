package emotion;

public class Emotion {



    private String id;
    private String type;
    private String subtype;
    private String name;

    private float emotion_intensity;
    private float emotion_subtype_intensity;

    public Emotion(String emotion_id,String emotionName, String emotionSubtype, String emotionType){
        this.id = emotion_id;
        this.name = emotionName;
        this.subtype = emotionSubtype;
        this.type = emotionType;
    }

    public String[] getValues(){
        String[] values = new String[4];
        values[0] = id;
        values[1] = name;
        values[2] = subtype;
        values[3] = type;
        return values;
    }

    public String toString(){
        return id + " " + name + " " + subtype + " " + type;
    }

}
