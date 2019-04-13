package learningunit.learningunit.Objects.Organizer;

public class Certificate {

    private String title, description;
    public Certificate(String t, String d){
        this.title = t;
        this.description = d;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

}
