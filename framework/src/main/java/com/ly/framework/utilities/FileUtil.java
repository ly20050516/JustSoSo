package com.ly.framework.utilities;

import android.os.Environment;

import java.io.File;

/**
 * Created by ly on 2017/8/6.
 */

public class FileUtil {
    public static File getDebugDumpDirectory() {

        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "JustSoSoDebug");
        if(!dir.exists()) {
            dir.mkdir();
        }
        return dir;

    }
}
