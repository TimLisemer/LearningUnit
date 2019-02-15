package learningunit.learningunit.Objects.Learn.VocabularyPackage;

import android.util.Log;

import java.util.ArrayList;

import learningunit.learningunit.Objects.API.ManageData;

public class VocabularyList {

    private ArrayList<Vocabulary> vocabularylist =  new ArrayList<Vocabulary>();
    private String name, LanguageName1, LanguageName2;
    private boolean source, followed, Shared;
    private int id;
    private int CreatorID;

    public VocabularyList(String LanguageName1, String LanguageName2, String name, boolean source, boolean followed) {
        this.source = source;
        this.LanguageName1 = LanguageName1;
        this.LanguageName2 = LanguageName2;
        this.name = name;
        this.followed = followed;

        /*
        if(!(VocabularyMethods.vocabularylanglists.contains(LanguageName1 + "-" + LanguageName2) || VocabularyMethods.vocabularylanglists.contains(LanguageName2 + "-" + LanguageName1))){
            VocabularyMethods.vocabularylanglists.add(LanguageName1 + "-" + LanguageName2);
            if(ManageData.OfflineAccount != 2){
                VocabularyMethods.vocabularylists.add(new VocabularyList(getLanguageName1(), LanguageName2, "AllVoc_" + LanguageName1 + " <--> " + LanguageName2, false, followed));
            }else{
                VocabularyMethods.vocabularylists.add(new VocabularyList(getLanguageName1(), LanguageName2, "AllVoc_" + LanguageName1 + " <--> " + LanguageName2, true, followed));
            }
        }
        */

        Log.d("VocabularyList", "Created Vocabulary List " + name + "Source: " + source + " Followed: " + followed);
    }

    public VocabularyList(String LanguageName1, String LanguageName2, String name, boolean source, boolean followed, int ID, int CreatorID, boolean Shared) {
        this.source = source;
        this.LanguageName1 = LanguageName1;
        this.LanguageName2 = LanguageName2;
        this.name = name;
        this.followed = followed;
        this.id = ID;
        this.CreatorID = CreatorID;
        this.Shared = Shared;

        /*
        if(!(VocabularyMethods.vocabularylanglists.contains(LanguageName1 + "-" + LanguageName2) || VocabularyMethods.vocabularylanglists.contains(LanguageName2 + "-" + LanguageName1))){
            VocabularyMethods.vocabularylanglists.add(LanguageName1 + "-" + LanguageName2);
            if(ManageData.OfflineAccount != 2){
                VocabularyMethods.vocabularylists.add(new VocabularyList(getLanguageName1(), LanguageName2, "AllVoc_" + LanguageName1 + " <--> " + LanguageName2, false, followed));
            }else{
                VocabularyMethods.vocabularylists.add(new VocabularyList(getLanguageName1(), LanguageName2, "AllVoc_" + LanguageName1 + " <--> " + LanguageName2, true, followed));
            }
        }
        */

        Log.d("VocabularyList", "Created Vocabulary List " + name + "Source: " + source + " Followed: " + followed + " ID: " + id + " CreatorID: " + CreatorID);
    }

    public void addVocabulary(Vocabulary voc){
        vocabularylist.add(voc);

    }

    public boolean ContainsVocabulary(Vocabulary voc){
        boolean falser = false;
        for (Vocabulary main : this.vocabularylist){
            if(main.getOriginal().equalsIgnoreCase(voc.getOriginal()) && main.getTranslation().equalsIgnoreCase(voc.getTranslation())){
                falser = true;
                break;
            }
        }
        return falser;
    }

    public int size(){
        return vocabularylist.size();
    }

    public ArrayList<Vocabulary> getVocabularylist() {
        return this.vocabularylist;
    }

    public void setID(int ID){
        this.id = ID;
    }

    public int getID(){
        return this.id;
    }

    public void setCreatorID(int CreatorID){
        this.CreatorID = CreatorID;
    }

    public int getCreatorID(){
        return this.CreatorID;
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

    public boolean getFollowed(){
        return this.followed;
    }

    public boolean getSource() { //true = online; false = offline
        return source;
    }



    public boolean getShared() {
        return Shared;
    }
    public void setShared(boolean Shared) {
        this.Shared = Shared;
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

    @Override
    public String toString() {
        return "VocabularyList{" +
                "vocabularylist=" + vocabularylist +
                ", name='" + name + '\'' +
                ", LanguageName1='" + LanguageName1 + '\'' +
                ", LanguageName2='" + LanguageName2 + '\'' +
                ", source=" + source +
                ", followed=" + followed +
                ", Shared=" + Shared +
                ", id=" + id +
                ", CreatorID=" + CreatorID +
                '}';
    }
}
