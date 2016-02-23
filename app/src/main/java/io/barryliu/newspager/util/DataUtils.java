package io.barryliu.newspager.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.barryliu.newspager.bean.NewsBean;

/**
 * Created by Barry on 2016/2/19.
 */
public class DataUtils {
    public static List<NewsBean> getNewsList(Context context){
        List<NewsBean> list =new ArrayList<>();


        //通过字符流得到  json 中 的数据
        try {
            InputStream is = context.getAssets().open("json/news.json");
            BufferedReader br = new BufferedReader( new InputStreamReader(is));

            String line;
            StringBuffer sb=new StringBuffer();
            while((line=br.readLine())!=null){
                sb.append(line);
            }

            //解析json
            JSONArray array = new JSONArray(sb.toString());

            for(int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);

                NewsBean newsBean = new NewsBean();
                newsBean.setName(object.getString("detail"));
                newsBean.setImagePath(object.getString("imagePath"));
                newsBean.setZan(object.getInt("commit"));
                newsBean.setTime(object.getString("time"));

                list.add(newsBean);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

}
