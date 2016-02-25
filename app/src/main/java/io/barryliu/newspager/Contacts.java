package io.barryliu.newspager;

import io.barryliu.newspager.R;

/**
 *  一些基本的参数
 * Created by Barry on 2016/2/17.
 *
 */
public class Contacts {
    //网络访问地址
    public final static String httpHost="http://192.168.8.14:8080/Http1/";
    public static  final String httpPath="http://192.168.8.14:8080/Http1/json/news.json";

    //第一次登陆向导的配置
    public final static String first="first";
    public final static String TagIsFirst="is First";

    //选项的名称
    public static final String [] tabitems={
      "新闻","湖南日报","观察","政情"
    };

    public static final int [] icon_items={
            R.drawable.news_item_icon_bg,
            R.drawable.pager_item_icon_bg,
            R.drawable.watch_item_icon_bg,
            R.drawable.police_item_icon_bg

    };



    public static final  int [] icon_slide={
        R.drawable.icon_a19,
        R.drawable.icon_a20,
        R.drawable.icon_a21,
        R.drawable.icon_a22,
        R.drawable.icon_a23
    };
    public static  final String [] icon_slide_name= {
            "清除缓存", "中小板指", "aaaa", "关于我们", "商务合作"
    };

}
