package learningunit.learningunit.Objects.Learn.VocabularyPackage;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import learningunit.learningunit.Objects.API.ManageData;

public class VocabularyMethods {

    public static List<VocabularyList> vocabularylists;
    public static ArrayList<String> vocabularylanglists = new ArrayList<String>();

    public static boolean openCreateList; //True CreateList ; False Import Csv List

    public static boolean nameavailable(String name){
        boolean a = true;
        CharSequence search = "AllVoc_";
        if(name.contains(search) == false) {
            if (vocabularylists != null) {
                for (int i = 0; i < vocabularylists.size(); i++) {
                    VocabularyList list = vocabularylists.get(i);
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


    public static void removeFollowedVocabularys(VocabularyList SharedList){
        if(VocabularyMethods.vocabularylists != null && SharedList != null) {
            VocabularyList list = null;
            if (VocabularyMethods.VocabularyLanguageUsed(SharedList.getLanguageName1(), SharedList.getLanguageName2()) == 1) {
                list = VocabularyMethods.getVocabularyList("AllVoc_" + SharedList.getLanguageName1() + " <--> " + SharedList.getLanguageName2(), true);
            } else if (VocabularyMethods.VocabularyLanguageUsed(SharedList.getLanguageName1(), SharedList.getLanguageName2()) == 2) {
                list = VocabularyMethods.getVocabularyList("AllVoc_" + SharedList.getLanguageName2() + " <--> " + SharedList.getLanguageName1(), true);
            }
            if (list != null) {
                vocabularylists.remove(list);
            }
        }
    }

    public static int VocabularyLanguageUsed(String language1, String language2){
        int returner = 0;
        if (vocabularylists != null) {
            for (int i = 0; i < vocabularylists.size(); i++) {
                VocabularyList list = vocabularylists.get(i);
                if (list.getName().equalsIgnoreCase("AllVoc_" + language1 + " <--> " + language2)) {
                    returner = 1;
                    break;
                }else if (list.getName().equalsIgnoreCase("AllVoc_" + language2 + " <--> " + language1)) {
                    returner =  2;
                    break;
                }
            }
        }
        return returner;
    }


    public static VocabularyList getVocabularyList(String name, boolean followed){
        for (int i = 0; i < vocabularylists.size(); i ++){
            VocabularyList list = vocabularylists.get(i);
            if(list.getName().equalsIgnoreCase(name) && list.getFollowed() == followed){
                return vocabularylists.get(i);
            }
        }
        return null;
    }

    public static VocabularyList getVocabularyList0(String name){
        for (int i = 0; i < vocabularylists.size(); i ++){
            VocabularyList list = vocabularylists.get(i);
            if(list.getName().equalsIgnoreCase(name)){
                return vocabularylists.get(i);
            }
        }
        return null;
    }



    public static void removeVocabularyList(VocabularyList list){
        vocabularylists.remove(list);
    }


    public static void saveVocabularyList(VocabularyList VocList){

        if(VocabularyMethods.vocabularylists != null) {
            if (!(VocabularyMethods.vocabularylists.contains(VocList))) {
                VocabularyMethods.vocabularylists.add(VocList);
            }else{
                int index = vocabularylists.indexOf(VocList);
                VocabularyMethods.vocabularylists.remove(index);
                VocabularyMethods.vocabularylists.add(VocList);
            }
        }else{
            VocabularyMethods.vocabularylists = new ArrayList<VocabularyList>();
            VocabularyMethods.vocabularylists.add(VocList);
        }

        ManageData.saveVocabularyLists();
    }

    public static boolean ListConstainsVoc(VocabularyList list, Vocabulary voc){
        boolean returner = false;

        for(Vocabulary v : list.getVocabularylist()){
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
