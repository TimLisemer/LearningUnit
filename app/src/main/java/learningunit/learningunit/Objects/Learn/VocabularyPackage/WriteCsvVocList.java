package learningunit.learningunit.Objects.Learn.VocabularyPackage;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteCsvVocList {



    public void WriteFile(VocabularyList vocList, Context ctx){




        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "LearningUnit" + File.separator + "VocabularyLists" + File.separator + vocList.getName() + ".csv");
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
            Toast.makeText(ctx, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "LearningUnit" + File.separator + "VocabularyLists" + File.separator + vocList.getName() + ".csv", Toast.LENGTH_LONG).show();
            FileWriter fileWriter;
            BufferedWriter bfWriter;
            try {
                fileWriter  = new FileWriter(file);
                bfWriter = new BufferedWriter(fileWriter);
                bfWriter.write(vocList.getVocabularylist().get(0).getOriginal() + ", " + vocList.getVocabularylist().get(0).getTranslation());
                for(int i = 1; i < vocList.getVocabularylist().size(); i++){
                    Vocabulary voc = vocList.getVocabularylist().get(i);
                    bfWriter.newLine();
                    bfWriter.write(voc.getOriginal() + ", " + voc.getTranslation());
                }
                bfWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
