package io.barryliu.newspager.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Barry on 2016/2/20.
 */
public class MeasuredViewPager extends ViewPager{

    public MeasuredViewPager(Context context){
        super(context);
    }
    public MeasuredViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    TextView textView;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;

        // 下面遍历所有child的高度
        for (int i = 0; i < getChildCount(); i++) {
            //获取子元素
            View child = getChildAt(i);
            //测量子元素的高度
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) // 采用最大的view的高度。
                height = h;
        }
        //计算高度
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }
}
