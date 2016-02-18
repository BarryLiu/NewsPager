package io.barryliu.newspager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import io.barryliu.newspager.lisenter.OnJumpLisenter;
import io.barryliu.newspager.view.Contacts;
import io.barryliu.newspager.view.IndicateView;

/**
 * 向导页面： 只有软件第一次用的时候会有向导页面，进入后会生成一个 Contacts.first对应的值的xml文件
 */
public class GuideActivity extends AppCompatActivity implements OnJumpLisenter {
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

        final IndicateView ivView = (IndicateView) findViewById(R.id.iv_guider);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ivView.setCurrentPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        getIntent();
    }
    public static int []  imageIds = {
            R.drawable.page1,
            R.drawable.page2,
            R.drawable.page3
    };

    static OnJumpLisenter jumpLisenter ;
    @Override
    public void jump() {
        Intent intent =new Intent(this,MainActivity.class);


        SharedPreferences preferences = getSharedPreferences(Contacts.first, MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(Contacts.TagIsFirst, true);
        edit.apply();


        startActivity(intent);
        finish();
    }


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

            //显示button按钮
            if(position == IndicateView.number-1){
                Button btnGuider = (Button) view.findViewById(R.id.btn_guider);
                btnGuider.setVisibility(View.VISIBLE);

                btnGuider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpLisenter = (OnJumpLisenter) getActivity();

                        jumpLisenter.jump();
                    }
                });
            }

            return view;
        }
    }
}
