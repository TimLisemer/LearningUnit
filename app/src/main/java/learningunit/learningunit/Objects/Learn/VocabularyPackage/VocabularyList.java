package learningunit.learningunit.Objects.Learn.VocabularyPackage;

import android.util.Log;

import java.util.ArrayList;

public class VocabularyList {

    private ArrayList<Vocabulary> vocabularylist =  new ArrayList<Vocabulary>();
    private String name, LanguageName1, LanguageName2;
    private boolean source;

    public VocabularyList(String LanguageName1, String LanguageName2, String name, boolean source) {
        this.source = source;
        this.LanguageName1 = LanguageName1;
        this.LanguageName2 = LanguageName2;
        this.name = name;

        Log.d("VocabularyList", "Created Vocabulary List " + name + "Source: " + source);
    }

    public void addVocabulary(Vocabulary voc){
        vocabularylist.add(voc);

    }

    public int size(){
        return vocabularylist.size();
    }

    public ArrayList<Vocabulary> getVocabularylist() {
        return this.vocabularylist;
    }



    public void setLanguageName1(String languageName1) {
        LanguageName1 = languageName1;
    }

    public void setLanguageName2(String languageName2) {
        LanguageName2 = languageName2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Log.d("VocabularyList", "Changed name of List " + this.name + " to " + name);
        this.name = name;
    }

    public boolean getSource() { //true = online; false = offline
        return source;
    }

    public void setSource(boolean source) { //true = online; false = offline
        this.source = source;
    }

    public String getLanguageName1() {
        return LanguageName1;
    }

    public String getLanguageName2() {
        return LanguageName2;
    }

}
