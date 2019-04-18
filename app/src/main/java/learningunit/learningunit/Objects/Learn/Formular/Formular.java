package learningunit.learningunit.Objects.Learn.Formular;

import learningunit.learningunit.Objects.API.ManageData;

public class Formular {



    private String Original, Translation, LanguageName1, LanguageName2;

    public Formular(String original, String translation, String LanguageName1, String LanguageName2, boolean followed) {
        this.LanguageName1 = LanguageName1;
        this.LanguageName2 = LanguageName2;
        Original = original;
        Translation = translation;

        if(FormularMethods.FormularLanguageUsed(LanguageName1, getLanguageName2()) == 0){
            FormularList list;
            if(ManageData.OfflineAccount != 2){
                list = new FormularList(getLanguageName1(), LanguageName2, "AllForm_" + LanguageName1 + " <--> " + LanguageName2, false, followed);
            }else{
                list = new FormularList(getLanguageName1(), LanguageName2, "AllForm_" + LanguageName1 + " <--> " + LanguageName2, true, followed);
            }
            if(!(list.ContainsFormular(this))) {
                list.addFormular(this);
                FormularMethods.saveFormularList(list);
            }
        }else if(FormularMethods.FormularLanguageUsed(LanguageName1, getLanguageName2()) == 1){
            FormularList list = FormularMethods.getFormularList("AllForm_" + LanguageName1 + " <--> " + LanguageName2, false);
            if(list != null) {
                if (!(FormularMethods.ListConstainsForm(list, this))) {
                    list.addFormular(this);
                    FormularMethods.saveFormularList(list);
                }
            }else{
                list = new FormularList(LanguageName1, LanguageName2, "AllForm_" + LanguageName1 + " <--> " + LanguageName2, false, false);
                if(!(FormularMethods.ListConstainsForm(list, this))) {
                    list.addFormular(this);
                    FormularMethods.saveFormularList(list);
                }
                FormularMethods.saveFormularList(list);
            }
        }else if(FormularMethods.FormularLanguageUsed(LanguageName1, getLanguageName2()) == 2){
            FormularList list = FormularMethods.getFormularList("AllForm_" + LanguageName2 + " <--> " + LanguageName1, false);
            if(list != null) {
                if (!(FormularMethods.ListConstainsForm(list, this))) {
                    list.addFormular(this);
                    FormularMethods.saveFormularList(list);
                }
            }else{
                list = new FormularList(LanguageName1, LanguageName2, "AllForm_" + LanguageName2 + " <--> " + LanguageName1, false, false);
                if(!(FormularMethods.ListConstainsForm(list, this))) {
                    list.addFormular(this);
                    FormularMethods.saveFormularList(list);
                }
                FormularMethods.saveFormularList(list);
            }
        }
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



}
