package io.barryliu.newspager.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
/**
 * Created by Barry on 2016/2/17.
 */
public class IndicateView extends View{
	Paint whitePaint;
	Paint redPaint;
	
	public IndicateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public IndicateView(Context context) {
		super(context);
		init();
	}

	public void init(){
		whitePaint=new Paint();
		redPaint=new Paint();
		whitePaint.setColor(Color.WHITE);
		redPaint.setColor(Color.RED);
	}
	
	int x;
	int y;
	int r=6; //圆的半径
	public static int number=3;//圆的数量
	int offX=5;
	int curPosition;
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		for (int i = 0; i < number; i++) {
			canvas.drawCircle(x+r+i*(2*r+offX), y+r, r, whitePaint);
			if(i==curPosition){
				canvas.drawCircle(x+r+i*(2*r+offX), y+r, r, redPaint);
			}
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//  TextView 中的。。
		//获取控件测量模式
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		//获取尺寸
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        
        int width;
        int height;
        
        if (widthMode == MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            width = widthSize;
        } else{	//wrap_contentʱ��
        	width=2*r*number+offX*(number-1);
        }
        
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else{
        	height=r*2;
        }
        
        setMeasuredDimension(width, height);
	}
	/**
	 * 设置为第几个页面
	 * @param position
	 */
	public void setCurrentPosition(int position){
		curPosition=position;
		//刷新页面
		invalidate();
	}

}
