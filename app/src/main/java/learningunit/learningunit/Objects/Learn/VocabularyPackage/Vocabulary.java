package learningunit.learningunit.Objects.Learn.VocabularyPackage;

import android.util.Log;

import learningunit.learningunit.Objects.API.ManageData;

public class Vocabulary {


    private String Original, Translation, LanguageName1, LanguageName2;
    private int id;

    public Vocabulary(String original, String translation, String LanguageName1, String LanguageName2) {
        this.LanguageName1 = LanguageName1;
        this.LanguageName2 = LanguageName2;
        Original = original;
        Translation = translation;
        if(VocabularyMethods.VocabularyLanguageUsed(LanguageName1, getLanguageName2()) == 0){
            VocabularyList list;
            if(ManageData.OfflineAccount != 2){
                list = new VocabularyList(getLanguageName1(), LanguageName2, "AllVoc_" + LanguageName1 + "_" + LanguageName2, false);
            }else{
                list = new VocabularyList(getLanguageName1(), LanguageName2, "AllVoc_" + LanguageName1 + "_" + LanguageName2, true);
            }
            list.addVocabulary(this);
            VocabularyMethods.saveVocabularyList(list);
        }else if(VocabularyMethods.VocabularyLanguageUsed(LanguageName1, getLanguageName2()) == 1){
            VocabularyList list = VocabularyMethods.getVocabularyList("AllVoc_" + LanguageName1 + "_" + LanguageName2);
            list.addVocabulary(this);VocabularyMethods.saveVocabularyList(list);
        }else if(VocabularyMethods.VocabularyLanguageUsed(LanguageName1, getLanguageName2()) == 2){
            VocabularyList list = VocabularyMethods.getVocabularyList("AllVoc_" + LanguageName2 + "_" + LanguageName1);
            list.addVocabulary(this);
            VocabularyMethods.saveVocabularyList(list);
        }
        Log.d("Vocabulary", "Created new Vocabulary - Originallanguage: " + LanguageName1 + " Vocabulary: " +  original + " Translationlanguage: " + LanguageName2 + " Vocabulary: " +  translation);
    }

    public String getOriginal() {
        return Original;
    }

    public void setOriginal(String original) {
        Original = original;
    }

    public String getTranslation() {
        return Translation;
    }

    public void setTranslation(String translation) {
        Translation = translation;
    }

    public String getLanguageName1() {
        return LanguageName1;
    }

    public void setLanguageName1(String languageName1) {
        LanguageName1 = languageName1;
    }

    public String getLanguageName2() {
        return LanguageName2;
    }

    public void setLanguageName2(String languageName2) {
        LanguageName2 = languageName2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
