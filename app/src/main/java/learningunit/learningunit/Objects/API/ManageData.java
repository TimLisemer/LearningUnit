package learningunit.learningunit.Objects.API;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import learningunit.learningunit.BeforeStart.FirstScreen;
import learningunit.learningunit.Menu.Learn.Vocabulary.Vokabeln;
import learningunit.learningunit.Menu.MainActivity;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.Vocabulary;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyList;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyMethods;

public class ManageData extends MainActivity{

    public static LinkedHashMap<String, String> Account;
    public static int OfflineAccount = 0; //0 ==> NoAccount; 1 ==> Offline; 2 ==> Online

    public static int getUserID(){
        if(OfflineAccount == 0 || OfflineAccount == 1) {
            return 0;
        }else{
            return Integer.parseInt(Account.get("id"));
        }
    }

    public static boolean OfflineDataLeft(){
        if(VocabularyMethods.vocabularylists != null) {
            int d = 0;
            for (int i = 0; i < VocabularyMethods.vocabularylists.size(); i++){
                if(VocabularyMethods.vocabularylists.get(i).getSource() == true){
                    d ++;
                }
            }
            if((VocabularyMethods.vocabularylists.size() - d) == 0){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public static void RemoveOfflineData(){
        ArrayList<VocabularyList> a = new ArrayList<VocabularyList>();

        if (VocabularyMethods.vocabularylists != null) {
            for (int i = 0; i < VocabularyMethods.vocabularylists.size(); i++) {
                if (VocabularyMethods.vocabularylists.get(i).getSource() == false) {
                    a.add(VocabularyMethods.vocabularylists.get(i));
                }
            }
        }else{
            VocabularyMethods.vocabularylists = new ArrayList<VocabularyList>();
        }
        VocabularyMethods.vocabularylists = a;
        FirstScreen.tinyDB.remove("VocLists");
        saveVocabularyLists();
        Account.clear();
        OfflineAccount = 0;
        ManageData.setOfflineAccount(0);
        ManageData.setOnlineAccount(false);
    }


    public static void saveVocabularyLists(){
        Gson gson = new Gson();
        String json = gson.toJson(VocabularyMethods.vocabularylists);
        FirstScreen.tinyDB.putString("VocLists", json);
    }


    public static void loadVocabularyLists(Context ctx){
        Gson gson = new Gson();
        String json = FirstScreen.tinyDB.getString("VocLists");
        if(json != "" || json == null){
            Type type = new TypeToken<ArrayList<VocabularyList>>() {}.getType();
            VocabularyMethods.vocabularylists = gson.fromJson(json, type);
        }else{
            VocabularyMethods.vocabularylists = new ArrayList<VocabularyList>();
        }
        if(OfflineAccount == 2){
            DownloadVocabularyLists(ctx);
        }
    }



    public static void DownloadFollowedVocabularyLists(Context ctx){
        if(MainActivity.InternetAvailable(ctx) == true){
            RequestHandler requestHandler = new RequestHandler();
            Log.d("Download", "----------------------------------------------------------------------------------------------");
            Log.d("Download", "Downloading Followed VocabularyLists");
            Log.d("Download", "------------------------------------------------------------------------------------------------------");
            Gson gson = new Gson();
            String json = requestHandler.sendGetRequest(MainActivity.URL_FollowedLists + getUserID());
            Type type = new TypeToken<ArrayList<ArrayList<String>>>() {}.getType();
            try {
                ArrayList<ArrayList<String>> list = gson.fromJson(json, type);
                VocabularyList vocabularyList;
                for(ArrayList<String> alist : list){
                    Log.d("Download", "Downloaded List with name: " + alist.get(1) + " ");
                    boolean go = true;
                    for (int i = 0; i < VocabularyMethods.vocabularylists.size(); i++) {
                        if (VocabularyMethods.vocabularylists.get(i).getName().equals(alist.get(1))) {
                            go = false;
                        }
                    }

                    if(go == true) {

                        String json1 = requestHandler.sendGetRequest(MainActivity.URL_GetShared + alist.get(0));
                        Type type1 = new TypeToken<String>() {}.getType();
                        String a = gson.fromJson(json1, type1);
                        final int sharedStatus = Integer.parseInt(a);
                        if(sharedStatus == 1) {
                            vocabularyList = new VocabularyList(alist.get(2), alist.get(3), alist.get(1), true, true, Integer.parseInt(alist.get(0)), Integer.parseInt(alist.get(4)));
                            vocabularyList.setShared(true);
                            VocabularyMethods.vocabularylists.add(vocabularyList);
                            DownloadVocabularys(vocabularyList.getName(), ctx);
                        }
                    }
                }

            }catch (Exception e) {
                Log.d("Download", "Nothing to download");
            }
        }else{
            Log.d("DownloadVocabularyLists", "Kein Internet Verfügbar");
        }

    }






    public static void DownloadVocabularyLists(Context ctx){
        if(MainActivity.InternetAvailable(ctx) == true){
            RequestHandler requestHandler = new RequestHandler();
            Log.d("Download", "----------------------------------------------------------------------------------------------");
            Log.d("Download", "Downloading VocabularyLists");
            Log.d("Download", "------------------------------------------------------------------------------------------------------");
            Gson gson = new Gson();
            String json = requestHandler.sendGetRequest(MainActivity.URL_GetVocabLists + getUserID());
            Type type = new TypeToken<ArrayList<ArrayList<String>>>() {}.getType();
            try {
                ArrayList<ArrayList<String>> list = gson.fromJson(json, type);
                VocabularyList vocabularyList;
                for(ArrayList<String> alist : list){
                    Log.d("Download", "Downloaded List with name: " + alist.get(1) + " ");
                    boolean go = true;
                    for (int i = 0; i < VocabularyMethods.vocabularylists.size(); i++) {
                        if (VocabularyMethods.vocabularylists.get(i).getName().equals(alist.get(1))) {
                            go = false;
                        }
                    }

                    if(go == true) {
                        vocabularyList = new VocabularyList(alist.get(2), alist.get(3), alist.get(1), true, false);
                        vocabularyList.setID(Integer.parseInt(alist.get(0)));
                        try {
                            String json1 = requestHandler.sendGetRequest(MainActivity.URL_GetShared + alist.get(0));
                            Type type1 = new TypeToken<String>() {
                            }.getType();
                            String a = gson.fromJson(json1, type1);
                            final int sharedStatus = Integer.parseInt(a);
                            if (sharedStatus == 0) {
                                vocabularyList.setShared(false);
                            } else {
                                vocabularyList.setShared(true);
                            }
                        }catch (Exception e){ }
                        VocabularyMethods.vocabularylists.add(vocabularyList);
                        DownloadVocabularys(vocabularyList.getName(), ctx);
                    }
                }

            }catch (Exception e) {
                Log.d("Download", "Nothing to download");
            }
        }else{
            Log.d("DownloadVocabularyLists", "Kein Internet Verfügbar");
        }

    }

    public static void DownloadVocabularys(String Name, Context ctx){
        if(MainActivity.InternetAvailable(ctx) == true) {
            try {
                RequestHandler requestHandler = new RequestHandler();
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<ArrayList<String>>>() {}.getType();
                Log.d("Download", "----------------------------------------------------------------------------------------------");
                Log.d("Download", "Downloading Vocabularys for VocabularyList: " + Name);
                Log.d("Download", "------------------------------------------------------------------------------------------------------");
                VocabularyList vocabularyList;
                if(Vokabeln.publiclist == false) {
                    vocabularyList = VocabularyMethods.getVocabularyList0(Name);
                    vocabularyList.getVocabularylist().clear();
                }else{
                    vocabularyList = Vokabeln.sharedlist;
                    Vokabeln.sharedlist.getVocabularylist().clear();
                }

                ArrayList<ArrayList<String>> voclist;
                String json1;
                if(Vokabeln.publiclist == false) {
                    if(vocabularyList.getCreatorID() == 0) {
                        json1 = requestHandler.sendGetRequest(MainActivity.URL_GetVocabs + getUserID() + "&Titel=" + vocabularyList.getName());
                        voclist = gson.fromJson(json1, type);
                    }else{
                        if(vocabularyList.getCreatorID() != ManageData.getUserID()){
                            Vokabeln.publiclist = true;
                            Vokabeln.sharedlist = vocabularyList;
                        }
                        json1 = requestHandler.sendGetRequest(MainActivity.URL_GetVocabs + vocabularyList.getCreatorID() + "&Titel=" + vocabularyList.getName());
                        voclist = gson.fromJson(json1, type);
                    }
                }else {
                    json1 = requestHandler.sendGetRequest(MainActivity.URL_GetVocabs + Vokabeln.sharedID + "&Titel=" + vocabularyList.getName());
                    voclist = gson.fromJson(json1, type);
                }

                for (ArrayList a : voclist) {
                    if(Vokabeln.publiclist == false) {
                        Vocabulary voc = new Vocabulary(a.get(0).toString(), a.get(1).toString(), vocabularyList.getLanguageName1(), vocabularyList.getLanguageName2(), false);
                        if(!(vocabularyList.ContainsVocabulary(voc))){
                            vocabularyList.addVocabulary(voc);
                        }
                    }else{
                        Vocabulary voc = new Vocabulary(a.get(0).toString(), a.get(1).toString(), Vokabeln.sharedlist.getLanguageName1(), Vokabeln.sharedlist.getLanguageName2(), true);
                        if(!(Vokabeln.sharedlist.ContainsVocabulary(voc))) {
                            Vokabeln.sharedlist.addVocabulary(voc);
                        }
                    }
                }

            } catch (Exception e) {
                Log.d("DownloadVocabularys", e.toString() + " ------------------------------------------------------------------");
           }
            Vokabeln.publiclist = false;
            Vokabeln.sharedlist = null;

        }else{
            Log.d("DownloadVocabularys", "Kein Internet Verfügbar");
        }
    }

    public static void uploadVocabularyList(VocabularyList vocabularyList, Context ctx) {
        if(vocabularyList.getFollowed() == false) {
            if (MainActivity.InternetAvailable(ctx)) {
                if (!(vocabularyList.getName().contains("AllVoc_"))) {
                    if (OfflineAccount == 2) {
                        Log.d("Upload", "----------------------------------------------------------------------------------------------");
                        Log.d("Upload", "Uploading Vocabularys");
                        Log.d("Upload", "------------------------------------------------------------------------------------------------------");
                        LinkedHashMap<String, String> params = new LinkedHashMap<>();
                        params.put("Titel", vocabularyList.getName());
                        params.put("Sprache1", vocabularyList.getLanguageName1());
                        params.put("Sprache2", vocabularyList.getLanguageName2());
                        params.put("id", getUserID() + "");

                        RequestHandler requestHandler = new RequestHandler();
                        requestHandler.sendPostRequest(MainActivity.URL_CreateVocList, params);


                        for (int i = 0; i < vocabularyList.getVocabularylist().size(); i++) {
                            LinkedHashMap<String, String> paramss = new LinkedHashMap<>();
                            paramss.put("Titel", vocabularyList.getName());
                            paramss.put("Sprache1", vocabularyList.getVocabularylist().get(i).getOriginal());
                            paramss.put("Sprache2", vocabularyList.getVocabularylist().get(i).getTranslation());
                            paramss.put("id", getUserID() + "");
                            paramss.put("extra", i + "");
                            requestHandler.sendPostRequest(MainActivity.URL_CreateVocabulary, paramss);
                        }
                    }
                }
            } else {
                Log.d("uploadVocabularyList", "Kein Internet Verfügbar");
            }
        }
    }



    public static void setOnlineAccount(Boolean state){
        if(state == false){
            FirstScreen.tinyDB.remove("Online");
            Account.clear();
        }else {
            OfflineAccount = 2;
            Gson gson = new Gson();
            String json = gson.toJson(Account);
            FirstScreen.tinyDB.putString("Online", json);
        }
    }

    public static void loadOnlineAccount(){
        Gson gson = new Gson();
        String json = FirstScreen.tinyDB.getString("Online");
        if(json != ""){
            OfflineAccount = 2;
            Type type = new TypeToken<LinkedHashMap<String, String>>() {}.getType();
            Account = gson.fromJson(json, type);
        }else{
            setOnlineAccount(false);
        }
    }

    public static void setOfflineAccount(int state){ //0 ==> LogOut; 1 ==> Offline; 2 ==> Online
        OfflineAccount = state;
        Gson gson = new Gson();
        String json = gson.toJson(OfflineAccount);
        FirstScreen.tinyDB.putString("Offline", json);
    }

    public static void loadOfflineAccount(){
        Gson gson = new Gson();
        String json = FirstScreen.tinyDB.getString("Offline");
        if(json != ""){
            Type type = new TypeToken<Integer>() {}.getType();
            OfflineAccount = gson.fromJson(json, type);
        }else{
            OfflineAccount = 0;
            Account.clear();
        }
    }



}
