package learningunit.learningunit.Objects.Learn.Formular;

import java.util.ArrayList;
import java.util.List;

public class FormularMethods {

    public static List<FormularList> formularLists;
    public static ArrayList<String> formularanglists = new ArrayList<String>();

    public static boolean openCreateList; //True CreateList ; False Import Csv List

    public static boolean nameavailable(String name){
        boolean a = true;
        CharSequence search = "AllForm_";
        if(name.contains(search) == false) {
            if (formularLists != null) {
                for (int i = 0; i < formularLists.size(); i++) {
                    FormularList list = formularLists.get(i);
                    if (list.getName().equalsIgnoreCase(name)) {
                        a = false;
                    }
                }
            }
        }else{
            a = false;
        }
        return a;
    }


    public static void removeFollowedFormular(FormularList SharedList){
        if(FormularMethods.formularLists != null && SharedList != null) {
            FormularList list = null;
            if (FormularMethods.FormularLanguageUsed(SharedList.getLanguageName1(), SharedList.getLanguageName2()) == 1) {
                list = FormularMethods.getFormularList("AllForm_" + SharedList.getLanguageName1() + " <--> " + SharedList.getLanguageName2(), true);
            } else if (FormularMethods.FormularLanguageUsed(SharedList.getLanguageName1(), SharedList.getLanguageName2()) == 2) {
                list = FormularMethods.getFormularList("AllForm_" + SharedList.getLanguageName2() + " <--> " + SharedList.getLanguageName1(), true);
            }
            if (list != null) {
                formularLists.remove(list);
            }
        }
    }

    public static int FormularLanguageUsed(String language1, String language2){
        int returner = 0;
        if (formularLists != null) {
            for (int i = 0; i < formularLists.size(); i++) {
                FormularList list = formularLists.get(i);
                if (list.getName().equalsIgnoreCase("AllForm_" + language1 + " <--> " + language2)) {
                    returner = 1;
                    break;
                }else if (list.getName().equalsIgnoreCase("AllForm_" + language2 + " <--> " + language1)) {
                    returner =  2;
                    break;
                }
            }
        }
        return returner;
    }


    public static FormularList getFormularList(String name, boolean followed){
        for (int i = 0; i < formularLists.size(); i ++){
            FormularList list = formularLists.get(i);
            if(list.getName().equalsIgnoreCase(name) && list.getFollowed() == followed){
                return formularLists.get(i);
            }
        }
        return null;
    }

    public static FormularList getFormularList0(String name){
        for (int i = 0; i < formularLists.size(); i ++){
            FormularList list = formularLists.get(i);
            if(list.getName().equalsIgnoreCase(name)){
                return formularLists.get(i);
            }
        }
        return null;
    }



    public static void removeFormularList(FormularList list){
        formularLists.remove(list);
    }


    public static void saveFormularList(FormularList forList){

        if(FormularMethods.formularLists != null) {
            if (!(FormularMethods.formularLists.contains(forList))) {
                FormularMethods.formularLists.add(forList);
            }else{
                int index = formularLists.indexOf(forList);
                FormularMethods.formularLists.remove(index);
                FormularMethods.formularLists.add(forList);
            }
        }else{
            FormularMethods.formularLists = new ArrayList<FormularList>();
            FormularMethods.formularLists.add(forList);
        }

        //ManageData.saveVocabularyLists();
    }

    public static boolean ListConstainsForm(FormularList list, Formular voc){
        boolean returner = false;

        for(Formular v : list.getFormularlist()){
            if(v.getTranslation().equals(voc.getTranslation())){
                if(v.getOriginal().equals(voc.getOriginal())){
                    if(v.getLanguageName2().equals(voc.getLanguageName2())){
                        if(v.getLanguageName1().equals(voc.getLanguageName1())){
                            returner = true;
                        }
                    }
                }
            }
        }

        return returner;
    }































}
