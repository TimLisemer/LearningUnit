package learningunit.learningunit.Objects.Learn.VocabularyPackage;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import learningunit.learningunit.Objects.API.ManageData;

public class VocabularyMethods {

    public static List<VocabularyList> vocabularylists;

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
        VocabularyList list = null;
        if(VocabularyMethods.VocabularyLanguageUsed(SharedList.getLanguageName1(), SharedList.getLanguageName2()) == 1){
            list = VocabularyMethods.getVocabularyList("AllVoc_" + SharedList.getLanguageName1() + "_" + SharedList.getLanguageName2(), true);
        }else if(VocabularyMethods.VocabularyLanguageUsed(SharedList.getLanguageName1(), SharedList.getLanguageName2()) == 2) {
            list = VocabularyMethods.getVocabularyList("AllVoc_" + SharedList.getLanguageName2() + "_" + SharedList.getLanguageName1(), true);
        }

        if(list != null) {
            vocabularylists.remove(list);
        }

    }

    public static int VocabularyLanguageUsed(String language1, String language2){

        if (vocabularylists != null) {
            for (int i = 0; i < vocabularylists.size(); i++) {
                VocabularyList list = vocabularylists.get(i);
                if (list.getName().equalsIgnoreCase("AllVoc_" + language1 + "_" + language2)) {
                    return 1;

                }else if (list.getName().equalsIgnoreCase("AllVoc_" + language2 + "_" + language1)) {
                    return  2;

                }
            }
        }
        return 0;
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


}
