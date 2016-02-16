package io.barryliu.newspager;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GuideActivity extends AppCompatActivity {
    ViewPager vp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        vp = (ViewPager) findViewById(R.id.vp);

        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return  SectionFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        getIntent();
    }
    public static int []  imageIds = {
            R.drawable.page1,
            R.drawable.page2,
            R.drawable.page3
    };
    //创建一个Fragment的类
    public static class SectionFragment extends Fragment{

        public static SectionFragment newInstance(int position){
            SectionFragment fragment = new SectionFragment();
            Bundle b = new Bundle();
            b.putInt("pos", position);
            //创建一个bundle用于传递
            fragment.setArguments(b);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //创建一个imageView
            View view = inflater.inflate(R.layout.frag_guider_vp_layout,null);
            //获取到传递过来的参数
            int position = getArguments().getInt("pos");

            //找到imageView
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_guider);
            imageView.setImageResource(imageIds[position]);

            return view;
        }
    }
}
