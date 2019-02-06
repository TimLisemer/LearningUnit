package learningunit.learningunit.Objects.Learn.VocabularyPackage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import learningunit.learningunit.BeforeStart.FirstScreen;
import learningunit.learningunit.Menu.Learn.Vocabulary.CreateVocList;
import learningunit.learningunit.Menu.Learn.Vocabulary.Vokabeln;
import learningunit.learningunit.Menu.MainActivity;
import learningunit.learningunit.Objects.API.ManageData;
import learningunit.learningunit.Objects.API.RequestHandler;
import learningunit.learningunit.R;

public class ReadCsvVocList {


    public void ReadList(Context ctx, InputStream is, String Sprache1, String Sprache2, String Name){
        VocabularyList CsvList = new VocabularyList(Sprache1, Sprache2, Name,true, false);
        CsvList.setCreatorID(ManageData.getUserID());

        is = ctx.getResources().openRawResource(R.raw.unit9);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                Vocabulary voc = new Vocabulary(tokens[0], tokens[1], Sprache1, Sprache2, false);
                CsvList.addVocabulary(voc);
            }
            VocabularyMethods.saveVocabularyList(CsvList);
            ManageData.uploadVocabularyList(CsvList, ctx);
        }catch (IOException e){
            Log.wtf("ReadCsvlist", "Error: " + e + " Line: " + line);
            e.printStackTrace();

            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setCancelable(true);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setTitle("CSV - Import");
            if (line != "") {
                builder.setMessage("Fehler beim Importieren der CSV - Liste --> Zeile: " + line);
            }else{
                builder.setMessage("Fehler beim Importieren der CSV - Liste");
            }
            builder.show();
        }
    }

}
