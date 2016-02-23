package io.barryliu.newspager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import io.barryliu.newspager.R;
import io.barryliu.newspager.bean.NewsBean;
import io.barryliu.newspager.util.DataUtils;

/**
 * Created by Barry on 2016/2/19.
 */
public class NewsListAdapter extends BaseAdapter {
    List<NewsBean> mList;
    Context content ;

    public NewsListAdapter(
            Context content ){
        this.mList = DataUtils.getNewsList(content);
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


        if(convertView == null){
            convertView = LayoutInflater.from(content).inflate(R.layout.lv_news_item,null);
        }
        ImageView ivIcon = (ImageView) convertView.findViewById(R.id.iv_news_icon);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tv_news_time);
        TextView tvDetail = (TextView) convertView.findViewById(R.id.tv_news_detail);
        TextView tvZan = (TextView) convertView.findViewById(R.id.tv_news_zan);

        NewsBean nb =   mList.get(position);
        tvTime.setText(nb.getTime());
        tvDetail.setText(nb.getName());
        tvZan.setText(nb.getZan()+"");
//        holder.iv_news_icon.set
        try {
            Bitmap bm = BitmapFactory.decodeStream(content.getAssets().open("images/"+nb.getImagePath()));
            ivIcon.setImageBitmap(bm);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertView;
    }


}
