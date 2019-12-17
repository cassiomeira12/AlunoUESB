package com.navan.app.alunouesb.utils;

import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

/**
 * Created by pedro on 29/08/17.
 */

public class PastasUtil {

    public static void criarPastas(AppCompatActivity activity) {
        if (activity == null)
            return;

        File file = new File(getPath());
        if (!file.exists())
            file.mkdir();

        file = new File(getPathBancoDeDados());
        if (!file.exists())
            file.mkdir();

        file = new File(getPathNoMedia());
        if (!file.exists())
            file.mkdir();
    }

    public static String getPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/CadernoAcademico/";
    }

    public static String getPathBancoDeDados() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/CadernoAcademico/bancoDeDados/";
    }

    public static String getPathNoMedia() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/CadernoAcademico/.nomedia/";
    }

}
