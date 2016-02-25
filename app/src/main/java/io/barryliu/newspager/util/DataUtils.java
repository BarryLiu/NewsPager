package io.barryliu.newspager.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import io.barryliu.newspager.Contacts;
import io.barryliu.newspager.bean.NewsBean;
import io.barryliu.newspager.utils.LogUtils;

/**
 * Created by Barry on 2016/2/19.
 */
public class DataUtils {

    public List<NewsBean> newsList ;
    private static void getJsonStr( ) {
        try {
            URL url = new URL(Contacts.httpPath);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line ;
            StringBuffer sb = new StringBuffer();
            while((line = br.readLine())!=null){
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<NewsBean> getNewsList(Context context) {
        List<NewsBean> list = new ArrayList<>();
        try {
        //准备数据
        //通过字符流得到  json 中 的数据
          InputStream is = context.getAssets().open("json/news.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            JSONArray array = new JSONArray(sb.toString());
            for(int i=0;i<array.length();i++){
                JSONObject obj = array.getJSONObject(i);

                NewsBean nb = new NewsBean() ;
                nb.setName((String) obj.get("detail"));
                nb.setImagePath((String) obj.get("imagePath"));
                nb.setTime(obj.getString("time"));
                nb.setZan(obj.getInt("commit"));

                list.add(nb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
