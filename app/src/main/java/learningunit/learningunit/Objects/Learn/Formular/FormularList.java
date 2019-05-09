package learningunit.learningunit.Objects.Learn.Formular;

import java.util.ArrayList;

public class FormularList {

    private ArrayList<Formular> formularList =  new ArrayList<Formular>();
    private String name, LanguageName1, LanguageName2;
    private boolean source, followed, Shared;
    private int id, Followers;
    private int CreatorID;

    public FormularList(String LanguageName1, String LanguageName2, String name, boolean source, boolean followed) {
        this.source = source;
        this.LanguageName1 = LanguageName1;
        this.LanguageName2 = LanguageName2;
        this.name = name;
        this.followed = followed;
    }

    public FormularList(String LanguageName1, String LanguageName2, String name, boolean source, boolean followed, int ID, int CreatorID, boolean Shared) {
        this.source = source;
        this.LanguageName1 = LanguageName1;
        this.LanguageName2 = LanguageName2;
        this.name = name;
        this.followed = followed;
        this.id = ID;
        this.CreatorID = CreatorID;
        this.Shared = Shared;
    }

    public void addFormular(Formular form){
        formularList.add(form);

    }

    public boolean ContainsFormular(Formular form){
        boolean falser = false;
        for (Formular main : this.formularList){
            if(main.getOriginal().equalsIgnoreCase(form.getOriginal()) && main.getTranslation().equalsIgnoreCase(form.getTranslation())){
                falser = true;
                break;
            }
        }
        return falser;
    }

    public int size(){
        return formularList.size();
    }

    public ArrayList<Formular> getFormularlist() {
        return this.formularList;
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
        return "FormularList{" +
                "formularList=" + formularList +
                ", name='" + name + '\'' +
                ", LanguageName1='" + LanguageName1 + '\'' +
                ", LanguageName2='" + LanguageName2 + '\'' +
                ", source=" + source +
                ", followed=" + followed +
                ", Shared=" + Shared +
                ", id=" + id +
                ", Followers=" + Followers +
                ", CreatorID=" + CreatorID +
                '}';
    }

    public int getFollowers() {
        return Followers;
    }

    public void setFollowers(int followers) {
        Followers = followers;
    }
}
