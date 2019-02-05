package learningunit.learningunit.Objects.Learn.VocabularyPackage;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.R;

public class ReadCsvVocList {

    private VocabularyList CsvList;
    String Sprache1 = "Englisch", Sprache2 = "Deutsch", Name = "Unit-9";

    public void ReadList(Context ctx){
        CsvList = new VocabularyList(Sprache1, Sprache2, Name,true, false);
        CsvList.setCreatorID(ManageData.getUserID());

        InputStream is = ctx.getResources().openRawResource(R.raw.unit9);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                Vocabulary voc = new Vocabulary(tokens[0], tokens[1], Sprache1, Sprache2, false);
                CsvList.addVocabulary(voc);
            }
            ManageData.uploadVocabularyList(CsvList, ctx);



        }catch (IOException e){
            Log.wtf("ReadCsvlist", "Error: " + e + " Line: " + line);
            e.printStackTrace();
        }
    }

}
