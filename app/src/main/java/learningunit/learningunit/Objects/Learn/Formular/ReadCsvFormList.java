package learningunit.learningunit.Objects.Learn.Formular;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import learningunit.learningunit.Objects.API.ManageData;

public class ReadCsvFormList {

    public void ReadList(Context ctx, InputStream is, String Sprache1, String Sprache2, String Name) {
        FormularList CsvList = new FormularList(Sprache1, Sprache2, Name, true, false);
        CsvList.setCreatorID(ManageData.getUserID());

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                Formular form = new Formular(tokens[0], tokens[1], Sprache1, Sprache2, false);
                CsvList.addFormular(form);
            }
            if (CsvList.getFormularlist().size() >= 2) {
                FormularMethods.saveFormularList(CsvList);
                ManageData.uploadFormularList(CsvList, ctx);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setTitle("CSV - Import");
                builder.setMessage("Fehler beim Importieren der CSV - Liste");
                builder.show();
            }
        } catch (IOException e) {
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
            } else {
                builder.setMessage("Fehler beim Importieren der CSV - Liste");
            }
            builder.show();
        }
    }

}
