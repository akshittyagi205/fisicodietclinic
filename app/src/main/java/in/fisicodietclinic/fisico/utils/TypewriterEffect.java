package in.fisicodietclinic.fisico.utils;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;


/**
 * Created by manyamadan on 11/12/17.
 */

public class TypewriterEffect extends android.support.v7.widget.AppCompatTextView {

    private CharSequence mText;
    private int mIndex;
    private long mDelay = 150; // in ms

    public TypewriterEffect(Context context) {
        super(context);
    }

    public TypewriterEffect(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Handler mHandler = new Handler();

    private Runnable characterAdder = new Runnable() {

        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex++));

            if (mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };

    public void animateText(CharSequence txt) {
        mText = txt;
        mIndex = 0;

        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    public void setCharacterDelay(long m) {
        mDelay = m;
    }
}