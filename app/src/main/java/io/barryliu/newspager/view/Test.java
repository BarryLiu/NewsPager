package io.barryliu.newspager.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Barry on 2016/2/24.
 */
public class Test {

    public void  test(){
        URL url = null;
        try {
            ImageView iv =null;//new ImageView();

            url = new URL("hht");
            InputStream is = url.openStream();

            Bitmap bitmap = BitmapFactory.decodeStream(is);
            iv.setImageBitmap(bitmap);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
