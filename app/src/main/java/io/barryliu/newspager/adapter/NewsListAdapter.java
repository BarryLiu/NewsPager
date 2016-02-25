package io.barryliu.newspager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import io.barryliu.newspager.Contacts;
import io.barryliu.newspager.R;
import io.barryliu.newspager.bean.NewsBean;
import io.barryliu.newspager.manager.ThreadPoolManager;
import io.barryliu.newspager.util.DataUtils;
import io.barryliu.newspager.util.StarageUtil;

/**
 * Created by Barry on 2016/2/19.
 */
public class NewsListAdapter extends BaseAdapter {
    List<NewsBean> mList;
    Context content;

    static LruCache<String, Bitmap> imageCache = new LruCache<String, Bitmap>(1024 * 1024 * 4) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight();
        }
    };

    public List<NewsBean> getmList() {
        return mList;
    }

    public NewsListAdapter(List<NewsBean> list,
                           Context content) {
        this.mList = list;//DataUtils.getNewsList(content);
        this.content = content;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(content).inflate(R.layout.lv_news_item, null);
        }
        final ImageView ivIcon = (ImageView) convertView.findViewById(R.id.iv_news_icon);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tv_news_time);
        TextView tvDetail = (TextView) convertView.findViewById(R.id.tv_news_detail);
        TextView tvZan = (TextView) convertView.findViewById(R.id.tv_news_zan);

        final NewsBean nb = mList.get(position);
        tvTime.setText(nb.getTime());
        tvDetail.setText(nb.getName());
        tvZan.setText(nb.getZan() + "");
//        holder.iv_news_icon.set


        Bitmap bm = imageCache.get(nb.getImagePath());
        if(bm!=null){//缓存中有数据，
            ivIcon.setImageBitmap(bm);
        }else{ //没有就先判断文件缓存中有没有缓存数据， 没有就去网络下载、
            File file = new File(StarageUtil.getCacheDir(),nb.getImagePath());
            if(file.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                ivIcon.setImageBitmap(bitmap);
            }else{//没有数据 ，网络中下载

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bm = getConn(nb.getImagePath());
                        Object[] obj = new Object[3];
                        obj[0] = ivIcon;
                        obj[1] = nb.getImagePath();
                        obj[2] = bm;



                        Message msg = handler.obtainMessage();
                        msg.obj = obj;
                        handler.sendMessage(msg);
                    }
                };
                ThreadPoolManager.getInstance().execute(r);

            }
        }


/*       //本地取得模拟图片
        try {
            Bitmap bm = BitmapFactory.decodeStream(content.getAssets().open("images/" + nb.getImagePath()));
            ivIcon.setImageBitmap(bm);

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return convertView;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Object[] obj = (Object[]) msg.obj;
            ImageView ivIcon = (ImageView) obj[0];
            String imagePath = (String) obj[1];
            Bitmap bm = (Bitmap) obj[2];

            ivIcon.setImageBitmap(bm);

            //保存到缓存中
            imageCache.put(imagePath,bm);
            //保存到本地文件中缓存
            try {
                File image = new File(StarageUtil.getCacheDir(),imagePath);
                if(!image.exists()){
                    image.createNewFile();
                }
                OutputStream os = new FileOutputStream(image);
                bm.compress(Bitmap.CompressFormat.PNG,100,os);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private Bitmap getConn(String imagePath) {
        try {
            String path = Contacts.httpHost + "images/" + imagePath;
            Log.d("main",path);
            URL url = new URL(path);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
