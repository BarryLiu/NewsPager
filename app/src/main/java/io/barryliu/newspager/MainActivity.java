package io.barryliu.newspager;

import android.app.Activity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import io.barryliu.newspager.fragment.NewsFragment;
import io.barryliu.newspager.view.Contacts;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabhost();
    }

    private void initTabhost() {
        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        //初始化tabhost
        tabHost.setup(this,getSupportFragmentManager(),R.id.realcontent);

        //创建选项卡
        for(int i=0;i<4;i++){
            //根据标签创建tabhost
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(i+"");

            //构建tabhost布局
           View itemView =getLayoutInflater().inflate(R.layout.tabhost_item_layout, null);

            ImageView iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            TextView tv_name = (TextView) itemView.findViewById(R.id.tv_name);

            tv_name.setText(Contacts.tabitems[i]);
            iv_icon.setImageResource(Contacts.icon_items[i]);

            tabSpec.setIndicator(itemView);
          //tabSpec.setIndicator("第"+i+"页");

            //绑定到tabhost中
             tabHost.addTab(tabSpec, NewsFragment.class,null);

        }
    }
}
