package io.barryliu.newspager.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by Barry on 2016/2/25.
 */
public class StarageUtil {
    public static File getCacheDir() {
        File dir = new File(Environment.getExternalStorageDirectory(), "newsPager");
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }
}
