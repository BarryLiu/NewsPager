package io.barryliu.newspager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import io.barryliu.newspager.Contacts;
import io.barryliu.newspager.R;
import io.barryliu.newspager.WebDetailActivity;
import io.barryliu.newspager.adapter.NewsListAdapter;
import io.barryliu.newspager.bean.NewsBean;
import io.barryliu.newspager.util.DataUtils;
import io.barryliu.newspager.utils.UIUtils;

/**
 * Created by Barry on 2016/2/19.
 */
public class HeadFragment extends Fragment {

    private NewsListAdapter listAdapter;
    PullToRefreshListView ptrlv ;

    class ReflashData {
        String str ;
        ListView lv;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            try {
                ReflashData rf = (ReflashData) msg.obj;

                String string = rf.str;
                String arrayStr = Html.fromHtml(string).toString();


                List<NewsBean> list = new ArrayList<NewsBean>();
                JSONArray array = null;
                array = new JSONArray(arrayStr);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    NewsBean nb =new NewsBean() ;
                    nb.setTime(obj.getString("time"));
                    nb.setZan(obj.getInt("commit"));
                    nb.setImagePath(obj.getString("imagePath"));
                    nb.setName(obj.getString("detail"));

                    list.add(nb);
                }
                rf.lv.setAdapter(new NewsListAdapter(list, getContext()));

                //跳到详情页面
                rf.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(getContext(), WebDetailActivity.class);
                        startActivity(intent);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    };


    Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            ptrlv.onRefreshComplete();;


            NewsBean nb = new NewsBean();
            nb.setImagePath("a1.png");
            nb.setName("新添加的一行");
            nb.setTime("12月15日");
            listAdapter.getmList().add(nb);
            //刷新界面
            // listAdapter.notifyDataSetChanged();
            ptrlv.getRefreshableView().setAdapter(listAdapter);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_headnews, null);

        //添加头部分
        View headView = inflater.inflate(R.layout.frag_head_viewpager_layout, null);
        ViewPager vpHead = (ViewPager) headView.findViewById(R.id.vp_head);
        CirclePageIndicator cpiHead = (CirclePageIndicator) headView.findViewById(R.id.cpi_head);

        vpHead.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                ImageFragment fragment = new ImageFragment();
                Bundle b = new Bundle();
                b.putInt("position", position);
                fragment.setArguments(b);
                return fragment;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        //将指引点与ViewPager关联
        cpiHead.setViewPager(vpHead);

        ImageView iv = new ImageView(getContext());
        iv.setImageResource(R.drawable.head1);
        //图片要和边框对齐
        iv.setAdjustViewBounds(true);

//        ListView lv = (ListView) view.findViewById(R.id.lv);
//        lv.addHeaderView(headView);
        //改成有下拉控件的
          ptrlv = (PullToRefreshListView) view.findViewById(R.id.lv);
       final ListView lv = ptrlv.getRefreshableView();
        lv.addHeaderView(headView);

        //实现下拉的时候刷新数据
        ptrlv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler2.sendEmptyMessage(0);
                    }
                }.start();
            }
        });


        //正文部分  为正文准备的测试数据
        new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    URL url = new URL(Contacts.httpPath);
                    URLConnection conn = url.openConnection();
                    InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line = "";
                    StringBuffer sb = new StringBuffer();
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    Message msg = handler.obtainMessage();
                    ReflashData rf= new ReflashData();
                    rf.str = sb.toString();
                    rf.lv = lv;
                    msg.obj = rf;

                    handler.sendMessage(msg);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();


//        listAdapter = new NewsListAdapter(DataUtils.getNewsList(getContext()), getContext());
//        lv.setAdapter(listAdapter);




        return view;
    }

    public static int[] picIds = {
            R.drawable.head1,
            R.drawable.head2,
            R.drawable.head3
    };


    public static class ImageFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ImageView iv = new ImageView(inflater.getContext());
            int pos = getArguments().getInt("position");
            //适应控件的边框
            iv.setAdjustViewBounds(true);
            iv.setImageResource(picIds[pos]);
            return iv;
        }
    }
}
