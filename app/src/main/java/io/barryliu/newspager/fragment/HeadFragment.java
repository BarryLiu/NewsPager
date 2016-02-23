package io.barryliu.newspager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.viewpagerindicator.CirclePageIndicator;

import io.barryliu.newspager.R;
import io.barryliu.newspager.adapter.NewsListAdapter;

/**
 * Created by Barry on 2016/2/19.
 */
public class HeadFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_headnews,null);

        //添加头部分
        View headView = inflater.inflate(R.layout.frag_head_viewpager_layout, null);

        ViewPager vpHead= (ViewPager) headView.findViewById(R.id.vp_head);
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
        PullToRefreshListView ptrlv= (PullToRefreshListView) view.findViewById(R.id.lv);;
        ListView lv = ptrlv.getRefreshableView();
        lv.addHeaderView(headView);

        //正文部分
     /*   String [] data =new String[20];   //为正文准备的测试数据
        for(int i=0;i<20;i++){
            data[i] ="第"+i+"条";
        }
         ArrayAdapter adapter =new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,data);
       */
        lv.setAdapter(new NewsListAdapter(getContext()));

        return view;
    }

    public static int[] picIds = {
            R.drawable.head1,
            R.drawable.head2,
            R.drawable.head3
    };


    public static class ImageFragment extends Fragment{
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ImageView iv  = new ImageView(inflater.getContext());
            int pos = getArguments().getInt("position");
            //适应控件的边框
            iv.setAdjustViewBounds(true);
            iv.setImageResource(picIds[pos]);
            return iv;
        }
    }
}
