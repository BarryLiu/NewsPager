package io.barryliu.newspager;

import android.graphics.Canvas;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.barryliu.newspager.fragment.NewsFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flMain = (FrameLayout) findViewById(R.id.fl_main);

        initTabhost();

        initSlide();

    }
    SlidingMenu menu =null;
    FrameLayout flMain = null;
    //加载侧滑菜单
    private void initSlide() {
          menu = new SlidingMenu(this);
        //设置从左边出来
        menu.setMode(SlidingMenu.LEFT);
        //

        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
//		// 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//		// 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);

        menu.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                //将画布默认的黑背景替换掉
//                canvas.drawColor(Color.GRAY);
//                canvas.scale(percentOpen, 1, 1, 0);
//                menu.setAlpha((percentOpen)*2);
                 flMain.setAlpha(percentOpen);
            }
        });




        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < Contacts.icon_slide.length; i++) {
            HashMap map = new HashMap<String, Objects>();
            map.put("icon", Contacts.icon_slide[i]);
            map.put("name", Contacts.icon_slide_name[i]);
            data.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, data,
                R.layout.item_slide_lv_layout, new String[]{"icon","name"},
                new int[]{R.id.iv_slide,R.id.tv_slide});
        View slideView = getLayoutInflater().inflate(R.layout.sildemenu_layout, null);

         ListView lvSlide = (ListView) slideView.findViewById(R.id.lv_slide);
       /* PullToRefreshListView ptrlv= (PullToRefreshListView) slideView.findViewById(R.id.lv_slide);;
        ListView lv = ptrlv.getRefreshableView();
        lv.setAdapter(adapter);*/
        lvSlide.setAdapter(adapter);
        //把滑动菜单添加进所有的Activity中，可选值SLIDING_CONTENT ， SLIDING_WINDOW
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(slideView);
    }
    public void showSlideMenu(View view) {
        menu.showMenu();
    }

    private void initTabhost() {
        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        //初始化tabhost
        tabHost.setup(this, getSupportFragmentManager(), R.id.realcontent);

        //创建选项卡
        for (int i = 0; i < 4; i++) {
            //根据标签创建tabhost
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(i + "");

            //构建tabhost布局
            View itemView = getLayoutInflater().inflate(R.layout.tabhost_item_layout, null);

            ImageView iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            TextView tv_name = (TextView) itemView.findViewById(R.id.tv_name);

            tv_name.setText(Contacts.tabitems[i]);
            iv_icon.setImageResource(Contacts.icon_items[i]);

            tabSpec.setIndicator(itemView);
            //tabSpec.setIndicator("第"+i+"页");

            //绑定到tabhost中
            tabHost.addTab(tabSpec, NewsFragment.class, null);

        }
    }


}
