package com.novaorbis.anirudh.convos;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;


public class TypeWriter extends TextView {
    private CharSequence mText;
    private int mIndex;
    private long mDelay = 50; // in ms

    public TypeWriter(Context context) {
        super(context);
    }
    public TypeWriter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public Handler obj = new Handler();
    private Runnable obj2 =  new Runnable()
    {

        @Override
        public void run() {
            setText(mText.subSequence(0,mIndex++));
            if(mIndex <= mText.length())
                obj.postDelayed(obj2,mDelay);
        }
    };
    public void animateText(CharSequence txt) {
        mText = txt;
        mIndex = 0;
        setText("");
        obj.removeCallbacks(obj2);
        obj.postDelayed(obj2, mDelay);
    }
    public void setCharacterDelay(long m) {
        mDelay = m;
    }
}
