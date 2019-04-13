package learningunit.learningunit.Objects.Learn.VocabularyPackage;

import android.util.Log;

import learningunit.learningunit.Objects.API.ManageData;

public class Vocabulary {


    private String Original, Translation, LanguageName1, LanguageName2;

    public Vocabulary(String original, String translation, String LanguageName1, String LanguageName2, boolean followed) {
        this.LanguageName1 = LanguageName1;
        this.LanguageName2 = LanguageName2;
        Original = original;
        Translation = translation;

        if(VocabularyMethods.VocabularyLanguageUsed(LanguageName1, getLanguageName2()) == 0){
            VocabularyList list;
            if(ManageData.OfflineAccount != 2){
                list = new VocabularyList(getLanguageName1(), LanguageName2, "AllVoc_" + LanguageName1 + " <--> " + LanguageName2, false, followed);
            }else{
                list = new VocabularyList(getLanguageName1(), LanguageName2, "AllVoc_" + LanguageName1 + " <--> " + LanguageName2, true, followed);
            }
            if(!(list.ContainsVocabulary(this))) {
                list.addVocabulary(this);
                //Log.d("ADDED", this.getOriginal() + " <--> " + this.getTranslation() + " to ALLLIST");
                VocabularyMethods.saveVocabularyList(list);
            }
        }else if(VocabularyMethods.VocabularyLanguageUsed(LanguageName1, getLanguageName2()) == 1){
            VocabularyList list = VocabularyMethods.getVocabularyList("AllVoc_" + LanguageName1 + " <--> " + LanguageName2, false);
            if(list != null) {
                if (!(VocabularyMethods.ListConstainsVoc(list, this))) {
                    list.addVocabulary(this);
                    VocabularyMethods.saveVocabularyList(list);
                    //Log.d("ADDED", this.getOriginal() + " <--> " + this.getTranslation() + " to ALLLIST");
                }
            }else{
                list = new VocabularyList(LanguageName1, LanguageName2, "AllVoc_" + LanguageName1 + " <--> " + LanguageName2, false, false);
                if(!(VocabularyMethods.ListConstainsVoc(list, this))) {
                    list.addVocabulary(this);
                    VocabularyMethods.saveVocabularyList(list);
                    //Log.d("ADDED", this.getOriginal() + " <--> " + this.getTranslation() + " to ALLLIST");
                }
                VocabularyMethods.saveVocabularyList(list);
                Log.d("AAAAA", "KKKKKKKKKKKK");
            }
        }else if(VocabularyMethods.VocabularyLanguageUsed(LanguageName1, getLanguageName2()) == 2){
            VocabularyList list = VocabularyMethods.getVocabularyList("AllVoc_" + LanguageName2 + " <--> " + LanguageName1, false);
            if(list != null) {
                if (!(VocabularyMethods.ListConstainsVoc(list, this))) {
                    list.addVocabulary(this);
                    VocabularyMethods.saveVocabularyList(list);
                    //Log.d("ADDED", this.getOriginal() + " <--> " + this.getTranslation() + " to ALLLIST");
                }
            }else{
                list = new VocabularyList(LanguageName1, LanguageName2, "AllVoc_" + LanguageName2 + " <--> " + LanguageName1, false, false);
                if(!(VocabularyMethods.ListConstainsVoc(list, this))) {
                    list.addVocabulary(this);
                    VocabularyMethods.saveVocabularyList(list);
                    //Log.d("ADDED", this.getOriginal() + " <--> " + this.getTranslation() + " to ALLLIST");
                }
                Log.d("AAAAA", "NNNNNNNNN");
                VocabularyMethods.saveVocabularyList(list);
            }
        }

        //Log.d("vocabulary", "Created new vocabulary - Originallanguage: " + LanguageName1 + " vocabulary: " +  original + " Translationlanguage: " + LanguageName2 + " vocabulary: " +  translation);
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

    @Override
    public String toString() {
        return "vocabulary{" +
                "Original='" + Original + '\'' +
                ", Translation='" + Translation + '\'' +
                ", LanguageName1='" + LanguageName1 + '\'' +
                ", LanguageName2='" + LanguageName2 + '\'' +
                '}';
    }
}