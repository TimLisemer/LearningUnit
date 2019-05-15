package learningunit.learningunit.Objects.API;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import learningunit.learningunit.Objects.Learn.Formular.Formular;
import learningunit.learningunit.Objects.Learn.Formular.FormularList;
import learningunit.learningunit.Objects.Learn.Formular.FormularMethods;
import learningunit.learningunit.Objects.Organizer.EventMethods;
import learningunit.learningunit.Objects.Organizer.Exam;
import learningunit.learningunit.Objects.Organizer.Homework;
import learningunit.learningunit.Objects.Organizer.Presentation;
import learningunit.learningunit.Objects.PublicAPIs.RequestHandler;
import learningunit.learningunit.Objects.Timetable.Day;
import learningunit.learningunit.Objects.Timetable.Hour;
import learningunit.learningunit.Objects.Timetable.HourList;
import learningunit.learningunit.Objects.Timetable.Week;
import learningunit.learningunit.beforeStart.FirstScreen;
import learningunit.learningunit.menu.learn.formular.Formeln;
import learningunit.learningunit.menu.learn.vocabulary.Vokabeln;
import learningunit.learningunit.menu.MainActivity;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.Vocabulary;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyList;
import learningunit.learningunit.Objects.Learn.VocabularyPackage.VocabularyMethods;

public class ManageData extends MainActivity{

    public static LinkedHashMap<String, String> Account;
    public static int OfflineAccount = 0; //0 ==> NoAccount; 1 ==> Offline; 2 ==> Online
    protected static boolean Premium = false;

    public static int getUserID(){
        if(OfflineAccount == 0 || OfflineAccount == 1) {
            return 0;
        }else{
            return Integer.parseInt(Account.get("id"));
        }
    }

    public static int getUserAdID(){
        if(OfflineAccount == 0 || OfflineAccount == 1) {
            return 0;
        }else{
            return Integer.parseInt(Account.get("SharedID"));
        }
    }

    public static boolean hasPremium(){
        boolean returner;
        if(OfflineAccount == 2) {
            returner = Premium;
        }else{
            returner = false;
        }
        return returner;
    }

    public static void setPremium(Boolean P){
        if(OfflineAccount == 2){
            Premium = P;
        }else {
            Premium = false;
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
                String jsona = FirstScreen.tinyDB.getString("WeekA");
                String jsonb = FirstScreen.tinyDB.getString("WeekA");
                if(jsona.equalsIgnoreCase("") && jsonb.equalsIgnoreCase("")) {
                    if(FormularMethods.formularLists != null) {
                        int e = 0;
                        for (int ii = 0; ii < FormularMethods.formularLists.size(); ii++){
                            if(FormularMethods.formularLists.get(ii).getSource() == true){
                                e ++;
                            }
                        }
                        if((FormularMethods.formularLists.size() - e) == 0){
                            return false;
                        }else{
                            if(!(FirstScreen.tinyDB.getString("Homework").equalsIgnoreCase(""))){
                                return false;
                            }else{
                                if(!(FirstScreen.tinyDB.getString("Exam").equalsIgnoreCase(""))){
                                    return false;
                                }else{
                                    if(!(FirstScreen.tinyDB.getString("Presentation").equalsIgnoreCase(""))){
                                        return false;
                                    }else{
                                        return true;
                                    }
                                }
                            }
                        }
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
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
        ArrayList<FormularList> b = new ArrayList<FormularList>();
        if (FormularMethods.formularLists != null) {
            for (int i = 0; i < FormularMethods.formularLists.size(); i++) {
                if (FormularMethods.formularLists.get(i).getSource() == false) {
                    b.add(FormularMethods.formularLists.get(i));
                }
            }
        }else{
            FormularMethods.formularLists = new ArrayList<FormularList>();
        }
        FormularMethods.formularLists = b;
        FirstScreen.tinyDB.remove("VocLists");
        FirstScreen.tinyDB.remove("WeekA");
        FirstScreen.tinyDB.remove("WeekB");
        FirstScreen.tinyDB.remove("Homework");
        FirstScreen.tinyDB.remove("Exam");
        FirstScreen.tinyDB.remove("Presentation");
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

    public static void saveFormularLists(){
        Gson gson = new Gson();
        String json = gson.toJson(FormularMethods.formularLists);
        FirstScreen.tinyDB.putString("FormLists", json);
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
    }


    public static void loadFormularLists(Context ctx){
        Gson gson = new Gson();
        String json = FirstScreen.tinyDB.getString("FormLists");
        if(json != "" || json == null){
            Type type = new TypeToken<ArrayList<FormularList>>() {}.getType();
            FormularMethods.formularLists = gson.fromJson(json, type);
        }else{
            FormularMethods.formularLists = new ArrayList<FormularList>();
        }
    }



    public static void DownloadFollowedVocabularyLists(Context ctx, boolean downloadVocs){
        if(MainActivity.InternetAvailable(ctx) == true){
            RequestHandler requestHandler = new RequestHandler();
            //Log.d("Download", "----------------------------------------------------------------------------------------------");
            //Log.d("Download", "Downloading Followed VocabularyLists");
            //Log.d("Download", "------------------------------------------------------------------------------------------------------");
            Gson gson = new Gson();
            String json = requestHandler.sendGetRequest(MainActivity.URL_FollowedLists + getUserID());
            Type type = new TypeToken<ArrayList<ArrayList<String>>>() {}.getType();
            try {
                ArrayList<ArrayList<String>> list = gson.fromJson(json, type);
                VocabularyList vocabularyList;
                for(ArrayList<String> alist : list){
                    //Log.d("Download", "Downloaded List with name: " + alist.get(1) + " ");
                    boolean go = true;
                    for (int i = 0; i < VocabularyMethods.vocabularylists.size(); i++) {
                        if (VocabularyMethods.vocabularylists.get(i).getName().equals(alist.get(1))) {
                            go = false;
                        }
                    }

                    if(go == true) {

                        String a = alist.get(5);
                        final int sharedStatus = Integer.parseInt(a);
                        if(sharedStatus == 1) {
                            vocabularyList = new VocabularyList(alist.get(2), alist.get(3), alist.get(1), true, true, Integer.parseInt(alist.get(0)), Integer.parseInt(alist.get(4)), true);
                            vocabularyList.setShared(true);
                            VocabularyMethods.vocabularylists.add(vocabularyList);
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




    public static void DownloadFollowedFormularLists(Context ctx, boolean downloadForms){
        if(MainActivity.InternetAvailable(ctx) == true){
            RequestHandler requestHandler = new RequestHandler();
            Gson gson = new Gson();
            String json = requestHandler.sendGetRequest(MainActivity.URL_FollowedFormLists + getUserID());
            Type type = new TypeToken<ArrayList<ArrayList<String>>>() {}.getType();
            try {
                ArrayList<ArrayList<String>> list = gson.fromJson(json, type);
                FormularList formularList;
                for(ArrayList<String> alist : list){
                    boolean go = true;
                    for (int i = 0; i < FormularMethods.formularLists.size(); i++) {
                        if (FormularMethods.formularLists.get(i).getName().equals(alist.get(1))) {
                            go = false;
                        }
                    }

                    if(go == true) {

                        String a = alist.get(5);
                        final int sharedStatus = Integer.parseInt(a);
                        if(sharedStatus == 1) {
                            formularList = new FormularList(alist.get(2), alist.get(3), alist.get(1), true, true, Integer.parseInt(alist.get(0)), Integer.parseInt(alist.get(4)), true);
                            formularList.setShared(true);
                            FormularMethods.formularLists.add(formularList);
                        }
                    }
                }

            }catch (Exception e) {
                Log.d("Download", "Nothing to download");
            }
        }else{
            Log.d("DownloadFormularLists", "Kein Internet Verfügbar");
        }

    }


    public static void DownloadVocabularys(String Name, Context ctx){
        if(MainActivity.InternetAvailable(ctx) == true) {
            try {
                RequestHandler requestHandler = new RequestHandler();
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<ArrayList<String>>>() {}.getType();
                Log.d("Download", "----------------------------------------------------------------------------------------------");
                Log.d("Download", "Downloading Vocabularys for VocabularyList: " + Name + "---------------------------------------");
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

            } catch (Exception e) { }
        }else{
            Log.d("DownloadVocabularys", "Kein Internet Verfügbar");
        }
    }




    public static void DownloadFormulars(String Name, Context ctx){
        if(MainActivity.InternetAvailable(ctx) == true) {
            try {
                RequestHandler requestHandler = new RequestHandler();
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<ArrayList<String>>>() {}.getType();
                FormularList formularList;
                if(Formeln.publiclist == false) {
                    formularList = FormularMethods.getFormularList0(Name);
                    formularList.getFormularlist().clear();
                }else{
                    formularList = Formeln.sharedlist;
                    Formeln.sharedlist.getFormularlist().clear();
                }

                ArrayList<ArrayList<String>> voclist;
                String json1;
                if(Formeln.publiclist == false) {
                    if(formularList.getCreatorID() == 0) {
                        json1 = requestHandler.sendGetRequest(MainActivity.URL_GetVocabs + getUserID() + "&Titel=" + formularList.getName());
                        voclist = gson.fromJson(json1, type);
                    }else{
                        if(formularList.getCreatorID() != ManageData.getUserID()){
                            Formeln.publiclist = true;
                            Formeln.sharedlist = formularList;
                        }
                        json1 = requestHandler.sendGetRequest(MainActivity.URL_GetVocabs + formularList.getCreatorID() + "&Titel=" + formularList.getName());
                        voclist = gson.fromJson(json1, type);
                    }
                }else {
                    json1 = requestHandler.sendGetRequest(MainActivity.URL_GetVocabs + Formeln.sharedID + "&Titel=" + formularList.getName());
                    voclist = gson.fromJson(json1, type);
                }

                for (ArrayList a : voclist) {
                    if(Formeln.publiclist == false) {
                        Formular form = new Formular(a.get(0).toString(), a.get(1).toString(), formularList.getLanguageName1(), formularList.getLanguageName2(), false);
                        if(!(formularList.ContainsFormular(form))){
                            formularList.addFormular(form);
                        }
                    }else{
                        Formular form = new Formular(a.get(0).toString(), a.get(1).toString(), Formeln.sharedlist.getLanguageName1(), Formeln.sharedlist.getLanguageName2(), true);
                        if(!(Formeln.sharedlist.ContainsFormular(form))) {
                            Formeln.sharedlist.addFormular(form);
                        }
                    }
                }

            } catch (Exception e) { }
        }else{
            Log.d("DownloadFormulars", "Kein Internet Verfügbar");
        }
    }




    public static void uploadVocabularyList(VocabularyList vocabularyList, Context ctx) {
        if(vocabularyList.getFollowed() == false) {
            if (MainActivity.InternetAvailable(ctx)) {
                if (!(vocabularyList.getName().contains("AllVoc_"))) {
                    if (OfflineAccount == 2) {
                        vocabularyList.setSource(true);
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
                if (OfflineAccount == 2) {
                    Log.d("uploadVocabularyList", "Kein Internet Verfügbar, hochladen Verschoben");
                }
            }
        }
    }



    public static void uploadFormularList(FormularList formularList, Context ctx) {
        if(formularList.getFollowed() == false) {
            if (MainActivity.InternetAvailable(ctx)) {
                if (!(formularList.getName().contains("AllForm_"))) {
                    if (OfflineAccount == 2) {
                        formularList.setSource(true);
                        Log.d("Upload", "----------------------------------------------------------------------------------------------");
                        Log.d("Upload", "Uploading Formulars");
                        Log.d("Upload", "------------------------------------------------------------------------------------------------------");
                        LinkedHashMap<String, String> params = new LinkedHashMap<>();
                        params.put("Titel", formularList.getName());
                        params.put("Sprache1", formularList.getLanguageName1());
                        params.put("Sprache2", formularList.getLanguageName2());
                        params.put("id", getUserID() + "");

                        RequestHandler requestHandler = new RequestHandler();
                        requestHandler.sendPostRequest(MainActivity.URL_CreateFormList, params);


                        for (int i = 0; i < formularList.getFormularlist().size(); i++) {
                            LinkedHashMap<String, String> paramss = new LinkedHashMap<>();
                            paramss.put("Titel", formularList.getName());
                            paramss.put("Sprache1", formularList.getFormularlist().get(i).getOriginal());
                            paramss.put("Sprache2", formularList.getFormularlist().get(i).getTranslation());
                            paramss.put("id", getUserID() + "");
                            paramss.put("extra", i + "");
                            requestHandler.sendPostRequest(MainActivity.URL_CreateFormular, paramss);
                        }
                    }
                }
            } else {
                if (OfflineAccount == 2) {
                    Log.d("uploadFormularList", "Kein Internet Verfügbar, hochladen Verschoben");
                }
            }
        }
    }



    public static void uploadDelayedVocabularyLists(Context ctx){
        if(OfflineAccount == 2) {
            if(MainActivity.InternetAvailable(ctx)) {
                for (VocabularyList list : VocabularyMethods.vocabularylists) {
                    if (list.getSource() == false) {
                        list.setSource(true);
                        uploadVocabularyList(list, ctx);
                    }
                }
            }
        }
    }


    public static void uploadDelayedFormularLists(Context ctx){
        if(OfflineAccount == 2) {
            if(MainActivity.InternetAvailable(ctx)) {
                for (FormularList list : FormularMethods.formularLists) {
                    if (list.getSource() == false) {
                        list.setSource(true);
                        uploadFormularList(list, ctx);
                    }
                }
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


    public static void NewDownloadVocabularyLists(Context ctx, boolean downloadVocs){
        if(MainActivity.InternetAvailable(ctx) == true){
            RequestHandler requestHandler = new RequestHandler();
            Gson gson = new Gson();
            String json = requestHandler.sendGetRequest(MainActivity.URL_NewCreateVocList + getUserID());
            Type type = new TypeToken<Object[][]>() {}.getType();
            try {
                Object[][] afterjson = gson.fromJson(json, type);
                ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
                for (int gh = 0; gh < afterjson.length; gh ++){
                    ArrayList<String> jk = new ArrayList<String>();
                    jk.add(afterjson[gh][0].toString());
                    jk.add(afterjson[gh][1].toString());
                    jk.add(afterjson[gh][2].toString());
                    jk.add(afterjson[gh][3].toString());
                    jk.add(afterjson[gh][5].toString());
                    list.add(jk);
                }
                VocabularyList vocabularyList;
                int o = 0;
                for(ArrayList<String> alist : list){

                    boolean go = true;
                    for (int i = 0; i < VocabularyMethods.vocabularylists.size(); i++) {
                        if (VocabularyMethods.vocabularylists.get(i).getName().equals(alist.get(1))) {
                            go = false;
                            break;
                        }
                    }

                    vocabularyList = new VocabularyList(alist.get(2), alist.get(3), alist.get(1), true, false);
                    vocabularyList.setID(Integer.parseInt(alist.get(0)));
                    if(go == true) {

                        try {

                            vocabularyList.setFollowers(Integer.parseInt(alist.get(5)));

                            final int sharedStatus = Integer.parseInt(alist.get(4));
                            if (sharedStatus == 0) {
                                vocabularyList.setShared(false);
                            } else {
                                vocabularyList.setShared(true);
                            }
                        }catch (Exception e){ }

                        if(downloadVocs == true) {
                            ArrayList<ArrayList<String>> voclist = (ArrayList<ArrayList<String>>) afterjson[o][4];
                            for (ArrayList a : voclist) {
                                if (Vokabeln.publiclist == false) {
                                    Vocabulary voc = new Vocabulary(a.get(0).toString(), a.get(1).toString(), vocabularyList.getLanguageName1(), vocabularyList.getLanguageName2(), false);
                                    vocabularyList.addVocabulary(voc);
                                } else {
                                    Vocabulary voc = new Vocabulary(a.get(0).toString(), a.get(1).toString(), vocabularyList.getLanguageName1(), vocabularyList.getLanguageName2(), true);
                                    vocabularyList.addVocabulary(voc);
                                }
                            }
                            VocabularyMethods.vocabularylists.add(vocabularyList);
                        }

                    }else{
                        for (int i = 0; i < VocabularyMethods.vocabularylists.size(); i++) {
                            if (VocabularyMethods.vocabularylists.get(i).getName().equals(alist.get(1))) {
                                VocabularyMethods.vocabularylists.get(i).setLanguageName1(alist.get(2));
                                VocabularyMethods.vocabularylists.get(i).setLanguageName2(alist.get(3));
                                VocabularyMethods.vocabularylists.get(i).setName(alist.get(1));
                                vocabularyList.setID(Integer.parseInt(alist.get(0)));
                                VocabularyMethods.vocabularylists.get(i).setSource(true);
                                break;
                            }
                        }
                    }
                    o++;
                }

            }catch (Exception e) { }
        }else{
            Log.d("NewDownloadVocLists", "Kein Internet Verfügbar");
        }
    }



    public static void NewDownloadFormularLists(Context ctx, boolean downloadForms){
        if(MainActivity.InternetAvailable(ctx) == true){
            RequestHandler requestHandler = new RequestHandler();
            Gson gson = new Gson();
            String json = requestHandler.sendGetRequest(MainActivity.URL_NewCreateFormList + getUserID());
            Type type = new TypeToken<Object[][]>() {}.getType();
            try {
                Object[][] afterjson = gson.fromJson(json, type);
                ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
                for (int gh = 0; gh < afterjson.length; gh ++){
                    ArrayList<String> jk = new ArrayList<String>();
                    jk.add(afterjson[gh][0].toString());
                    jk.add(afterjson[gh][1].toString());
                    jk.add(afterjson[gh][2].toString());
                    jk.add(afterjson[gh][3].toString());
                    jk.add(afterjson[gh][5].toString());
                    list.add(jk);
                }
                FormularList formularList;
                int o = 0;
                for(ArrayList<String> alist : list){

                    boolean go = true;
                    for (int i = 0; i < FormularMethods.formularLists.size(); i++) {
                        if (FormularMethods.formularLists.get(i).getName().equals(alist.get(1))) {
                            go = false;
                            break;
                        }
                    }

                    formularList = new FormularList(alist.get(2), alist.get(3), alist.get(1), true, false);
                    formularList.setID(Integer.parseInt(alist.get(0)));
                    if(go == true) {

                        try {
                            formularList.setFollowers(Integer.parseInt(alist.get(5)));
                            final int sharedStatus = Integer.parseInt(alist.get(4));
                            if (sharedStatus == 0) {
                                formularList.setShared(false);
                            } else {
                                formularList.setShared(true);
                            }
                        }catch (Exception e){ }

                        if(downloadForms == true) {
                            ArrayList<ArrayList<String>> formlist = (ArrayList<ArrayList<String>>) afterjson[o][4];
                            for (ArrayList a : formlist) {
                                if (Formeln.publiclist == false) {
                                    Formular form = new Formular(a.get(0).toString(), a.get(1).toString(), formularList.getLanguageName1(), formularList.getLanguageName2(), false);
                                    formularList.addFormular(form);
                                } else {
                                    Formular form = new Formular(a.get(0).toString(), a.get(1).toString(), formularList.getLanguageName1(), formularList.getLanguageName2(), true);
                                    formularList.addFormular(form);
                                }
                            }
                            FormularMethods.formularLists.add(formularList);
                        }

                    }else{
                        for (int i = 0; i < FormularMethods.formularLists.size(); i++) {
                            if (FormularMethods.formularLists.get(i).getName().equals(alist.get(1))) {
                                FormularMethods.formularLists.get(i).setLanguageName1(alist.get(2));
                                FormularMethods.formularLists.get(i).setLanguageName2(alist.get(3));
                                FormularMethods.formularLists.get(i).setName(alist.get(1));
                                formularList.setID(Integer.parseInt(alist.get(0)));
                                FormularMethods.formularLists.get(i).setSource(true);
                                break;
                            }
                        }
                    }
                    o++;
                }

            }catch (Exception e) { }
        }else{
            Log.d("NewDownloadFormLists", "Kein Internet Verfügbar");
        }
    }








    public static boolean LoadTimetable(boolean weekId, int id, Context ctx, boolean upload){

        RequestHandler requestHandler = new RequestHandler();
        Gson gson = new Gson();
        String json;
        if(weekId){
            json = requestHandler.sendGetRequest(MainActivity.URL_getWeekbyId + id);
        }else {
            json = requestHandler.sendGetRequest(MainActivity.URL_getWeekbyUser + id);
        }
        if(json.isEmpty()){
            Log.d("LoadTimetable false", json);
            return false;
        }else {
            Log.d("LoadTimetable true", json);
            Week weekA, weekB;
            Type type = new TypeToken<Object[][]>() {
            }.getType();
            try {
                Object[][] afterjson = gson.fromJson(json, type);

                ArrayList<Integer> DetailArrayList = new ArrayList<Integer>();
                DetailArrayList.add(Integer.parseInt(afterjson[0][0].toString()));
                DetailArrayList.add(Integer.parseInt(afterjson[0][1].toString()));
                DetailArrayList.add(Integer.parseInt(afterjson[0][2].toString()));
                DetailArrayList.add(Integer.parseInt(afterjson[0][3].toString()));

                Day[] day = new Day[afterjson[3].length];
                Hour[] hour = new Hour[DetailArrayList.get(1) * afterjson[3].length];


                if(afterjson[3].length != DetailArrayList.get(0)){
                    weekA = new Week(DetailArrayList.get(0));
                    weekA.setWeekID(DetailArrayList.get(2));
                    weekB = new Week(DetailArrayList.get(0));
                    for (int i = 0; i < afterjson[3].length; i++) {
                        day[i] = new Day(afterjson[3][i].toString());
                        for (int d = 1 ; d <= DetailArrayList.get(1); d++) {
                            int e = ((i * DetailArrayList.get(1)) + d) - 1;
                            hour[e] = new Hour(afterjson[1][e].toString(), "#" + afterjson[2][e].toString());
                            day[i].getHourList().add(hour[e]);
                        }
                        if(i < DetailArrayList.get(0)) {
                            weekA.addDay(day[i]);
                        }else{
                            weekB.addDay(day[i]);
                        }
                    }
                    String json1 = gson.toJson(weekA);
                    FirstScreen.tinyDB.putString("WeekA", json1);
                    Log.d("LoadTimetable WeekA", json1);
                    String json2 = gson.toJson(weekB);
                    FirstScreen.tinyDB.putString("WeekB", json2);
                    Log.d("LoadTimetable WeekB", json2);

                }else{
                    weekA = new Week(DetailArrayList.get(0));
                    weekA.setWeekID(DetailArrayList.get(2));
                    for (int i = 0; i < afterjson[3].length; i++) {
                        day[i] = new Day(afterjson[3][i].toString());
                        for (int d = 1 ; d <= DetailArrayList.get(1); d++) {
                            int e = ((i * DetailArrayList.get(1)) + d) - 1;
                            hour[e] = new Hour(afterjson[1][e].toString(), "#" + afterjson[2][e].toString());
                            day[i].getHourList().add(hour[e]);
                        }
                        weekA.addDay(day[i]);
                    }
                    String json1 = gson.toJson(weekA);
                    FirstScreen.tinyDB.putString("WeekA", json1);
                    Log.d("LoadTimetable WeekA", json1);
                }

                if(DetailArrayList.get(3) != ManageData.getUserID() && upload){
                    HourList.noConnection(true, ctx, weekA, null, false);
                }

                return true;
            } catch (Exception e){
                Log.d("LoadTimetable", e + "");
                return false;
            }
        }
    }









    public static boolean LoadOrganizer(){
        RequestHandler requestHandler = new RequestHandler();
        Gson gson = new Gson();
        String json  = requestHandler.sendGetRequest(MainActivity.URL_getEvents + getUserID());
        if(json.isEmpty()){
            Log.d("LoadOrganizer false", json);
            return false;
        }else {
            Log.d("LoadOrganizer true", json);
            Object[][][] object;
            Type type = new TypeToken<Object[][][]>() {}.getType();
            object = gson.fromJson(json, type);

            Log.d("LoadOrganizer", object.toString());

            try {
                for (int d = 0; d < object[0].length; d++) {
                    Homework homework = new Homework(Integer.parseInt(object[0][d][0].toString()), Integer.parseInt(object[0][d][1].toString()), Integer.parseInt(object[0][d][2].toString()));
                    homework.setTitle(object[0][d][3].toString());
                    homework.setDescription(object[0][d][4].toString());
                    homework.setHour(new Hour(object[0][d][6].toString(), "#" + object[0][d][5].toString()));
                    if (Integer.parseInt(object[0][d][7].toString()) == 0) {
                        homework.setDone(false);
                    } else {
                        homework.setDone(true);
                    }
                    String json1 = FirstScreen.tinyDB.getString("Homework");
                    ArrayList<Homework> halist;
                    if (json1.equals("")) {
                        halist = new ArrayList<Homework>();
                    } else {
                        Type type1 = new TypeToken<ArrayList<Homework>>() {
                        }.getType();
                        halist = gson.fromJson(json1, type1);
                    }
                    Boolean go = true;
                    for(Homework ha : halist){
                        if(ha.getDay() == homework.getDay() && ha.getMonth() == homework.getMonth() && ha.getYear() == homework.getYear() && ha.getTitle().equalsIgnoreCase(homework.getTitle()) && ha.getDescription().equalsIgnoreCase(homework.getDescription()) && ha.getHour().getName().equalsIgnoreCase(homework.getHour().getName()) && ha.getHour().getColorCode().equalsIgnoreCase(homework.getHour().getColorCode())){
                            go = false;
                        }
                    }
                    if(go == true) {
                        halist.add(homework);
                        Log.d("Homework", "Downloaded Homework " + homework.getTitle());
                        FirstScreen.tinyDB.putString("Homework", gson.toJson(halist));
                    }
                }
            }catch (Exception e){
                Log.d("Homework", e.toString());
            }
            try{
                for(int d = 0; d < object[1][d].length; d ++) {
                    Exam exam = new Exam(Integer.parseInt(object[1][d][0].toString()), Integer.parseInt(object[1][d][1].toString()), Integer.parseInt(object[1][d][2].toString()));
                    exam.setTitle(object[1][d][3].toString());
                    exam.setDescription(object[1][d][4].toString());
                    exam.setHour(new Hour(object[1][d][6].toString(), "#" + object[1][d][5].toString()));
                    exam.setGrade(Integer.parseInt(object[1][d][7].toString()));
                    String json1 = FirstScreen.tinyDB.getString("Exam");
                    ArrayList<Exam> exlist;
                    if(json1.equals("")){
                        exlist = new ArrayList<Exam>();
                    }else {
                        Type type1 = new TypeToken<ArrayList<Exam>>() {
                        }.getType();
                        exlist = gson.fromJson(json1, type1);
                    }
                    Boolean go = true;
                    for(Exam ex : exlist){
                        if(ex.getDay() == exam.getDay() && ex.getMonth() == exam.getMonth() && ex.getYear() == exam.getYear() && ex.getTitle().equalsIgnoreCase(exam.getTitle()) && ex.getDescription().equalsIgnoreCase(exam.getDescription()) && ex.getHour().getName().equalsIgnoreCase(exam.getHour().getName()) && ex.getHour().getColorCode().equalsIgnoreCase(exam.getHour().getColorCode())){
                            go = false;
                        }
                    }
                    if(go == true) {
                        exlist.add(exam);
                        Log.d("Exam", "Downloaded Exam " + exam.getTitle());
                        FirstScreen.tinyDB.putString("Exam", gson.toJson(exlist));
                    }
                }
            }catch (Exception e){
                Log.d("Exam", e.toString());
            }
            try{
                for(int d = 0; d < object[2][d].length; d ++) {
                    Presentation pre = new Presentation(Integer.parseInt(object[2][d][0].toString()), Integer.parseInt(object[2][d][1].toString()), Integer.parseInt(object[2][d][2].toString()));
                    pre.setTitle(object[2][d][3].toString());
                    pre.setDescription(object[2][d][4].toString());
                    pre.setHour(new Hour(object[2][d][6].toString(), "#" + object[2][d][5].toString()));
                    pre.setGrade(Integer.parseInt(object[2][d][7].toString()));
                    String json1 = FirstScreen.tinyDB.getString("Presentation");
                    ArrayList<Presentation> prelist;
                    if(json1.equals("")){
                        prelist = new ArrayList<Presentation>();
                    }else {
                        Type type1 = new TypeToken<ArrayList<Presentation>>() {
                        }.getType();
                        prelist = gson.fromJson(json1, type1);
                    }
                    Boolean go = true;
                    for(Presentation presentation : prelist){
                        if(presentation.getDay() == pre.getDay() && presentation.getMonth() == pre.getMonth() && presentation.getYear() == pre.getYear() && presentation.getTitle().equalsIgnoreCase(pre.getTitle()) && presentation.getDescription().equalsIgnoreCase(pre.getDescription()) && presentation.getHour().getName().equalsIgnoreCase(pre.getHour().getName()) && presentation.getHour().getColorCode().equalsIgnoreCase(pre.getHour().getColorCode())){
                            go = false;
                        }
                    }
                    if(go == true) {
                        prelist.add(pre);
                        Log.d("Presentation", "Downloaded Presentation " + pre.getTitle());
                        FirstScreen.tinyDB.putString("Presentation", gson.toJson(prelist));
                    }
                }
            }catch (Exception e){
                Log.d("Presentation", e.toString());
            }


            return true;
        }
    }


















































































}