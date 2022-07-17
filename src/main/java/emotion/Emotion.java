package emotion;

public class Emotion {

    public enum Emotion_type{
        SUBLIMITY,
        VITALITY,
        UNEASE,
    }

    public enum Emotion_subtype{
        WONDER,
        TRANSCENDENCE,
        TENDERNESS,
        NOSTALGIA,
        PEACEFULNESS,
        ENERGY,
        JOYFUL_ACTIVATION,
        TENSION,
        SADNESS
    }

    public enum Emotion_name{
        happy,
        amazed,
        dazzled,
        allured,
        moved,
        inspired,
        feeling_of_transcendence,
        feeling_of_spirituality,
        thrills,
        in_love,
        affectionate,
        sensual,
        tender,
        softened_up,
        sentimental,
        dreamy,
        nostalgic,
        melancholic,
        calm,
        relaxed,
        serene,
        soothed,
        meditative,
        energetic,
        triumphant,
        fiery,
        strong,
        heroic,
        stimulated,
        joyful,
        animated,
        dancing,
        amused,
        agitated,
        nervous,
        impatient,
        irritated,
        sad,
        sorrowful

    }

    private String emotion_id;
    private Emotion_type generalType;
    private Emotion_subtype subtype;
    private Emotion_name emotionName;
    private float emotion_intensity;
    private float emotion_subtype_intensity;

    public Emotion(){}



}
