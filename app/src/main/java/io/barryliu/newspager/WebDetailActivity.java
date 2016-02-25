package io.barryliu.newspager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEventSource;
import android.webkit.WebView;

/**
 * Created by Barry on 2016/2/24.
 */
public class WebDetailActivity extends Activity {

    private WebView wvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_details_layout);

        initView();

        //wvDetail.loadUrl("http://www.baidu.com");
        //wvDetail.loadUrl("file:///json/news.json");
        wvDetail.loadUrl("file:///android_asset/a1.htm");
//        wvDetail.load
    }

    private void initView() {
        wvDetail = (WebView) findViewById(R.id.wb_detail);
    }
}
