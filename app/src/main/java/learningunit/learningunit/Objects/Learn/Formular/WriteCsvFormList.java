package learningunit.learningunit.Objects.Learn.Formular;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteCsvFormList {

    public void WriteFile(FormularList formList, Context ctx){

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "LearningUnit" + File.separator + "FormularLists" + File.separator + formList.getName() + ".csv");
        if(!file.exists()) {
            try {
                if (!file.getParentFile().getParentFile().exists())
                    file.getParentFile().getParentFile().mkdirs();
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                if (!file.exists())
                    file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(file.exists()){
            Toast.makeText(ctx, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "LearningUnit" + File.separator + "FormularLists" + File.separator + formList.getName() + ".csv", Toast.LENGTH_LONG).show();
            FileWriter fileWriter;
            BufferedWriter bfWriter;
            try {
                fileWriter  = new FileWriter(file);
                bfWriter = new BufferedWriter(fileWriter);
                bfWriter.write(formList.getFormularlist().get(0).getOriginal() + ", " + formList.getFormularlist().get(0).getTranslation());
                for(int i = 1; i < formList.getFormularlist().size(); i++){
                    Formular form = formList.getFormularlist().get(i);
                    bfWriter.newLine();
                    bfWriter.write(form.getOriginal() + ", " + form.getTranslation());
                }
                bfWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
