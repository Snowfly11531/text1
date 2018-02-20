package com.example.text1;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by hasee on 2017/5/14.
 */

public class ListViewforScrollView extends ListView {
    public ListViewforScrollView(Context context){
        super(context);
    }
    public ListViewforScrollView(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    public ListViewforScrollView(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec,expandSpec);
    }
}
