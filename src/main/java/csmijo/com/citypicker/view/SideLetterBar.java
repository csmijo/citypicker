package csmijo.com.citypicker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import csmijo.com.citypicker.R;

/**
 * Created by chengqianqian-xy on 2016/12/29.
 */

public class SideLetterBar extends View {

    private static final String[] letters = {"定位", "热门", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private int choose = -1;
    private Paint mPaint = new Paint();
    private boolean showBg = false;
    private OnLetterChangedListener mOnLetterChangedListener;
    private TextView overlay;


    public SideLetterBar(Context context) {
        super(context);
    }

    public SideLetterBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置悬浮的 TextView
     *
     * @param overlay
     */
    public void setOverlay(TextView overlay) {
        this.overlay = overlay;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBg) {
            canvas.drawColor(Color.TRANSPARENT);
        }

        int height = getHeight();
        int width = getWidth();
        int sigleHeight = height / letters.length;
        for (int i = 0; i < letters.length; i++) {
            this.mPaint.setTextSize(getResources().getDimension(R.dimen.side_letter_bar_letter_size));
            this.mPaint.setColor(getResources().getColor(R.color.cp_gray));
            mPaint.setAntiAlias(true);
            if (i == choose) {
                mPaint.setColor(getResources().getColor(R.color.cp_gray_deep));
                mPaint.setFakeBoldText(true);  // 加粗
            }

            float xPos = width / 2 - mPaint.measureText(letters[i]) / 2;
            float yPos = sigleHeight * (i + 1);
            canvas.drawText(letters[i], xPos, yPos, mPaint);
            mPaint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();
        int oldChoose = choose;
        OnLetterChangedListener listener = mOnLetterChangedListener;
        int index = (int) (y / getHeight() * letters.length);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBg = true;
                if (oldChoose != index && listener != null) {
                    if (index >= 0 && index < letters.length) {
                        listener.onLetterChanged(letters[index]);
                        choose = index;
                        invalidate();
                        if (overlay != null) {
                            overlay.setVisibility(VISIBLE);
                            overlay.setText(letters[index]);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != index && listener != null) {
                    if (index >= 0 && index < letters.length) {
                        listener.onLetterChanged(letters[index]);
                        choose = index;
                        invalidate();
                        if (overlay != null) {
                            overlay.setVisibility(VISIBLE);
                            overlay.setText(letters[index]);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBg = false;
                choose = -1;
                invalidate();
                if (overlay != null) {
                    overlay.setVisibility(GONE);
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnLetterChangedListener(OnLetterChangedListener onLetterChangedListener) {
        mOnLetterChangedListener = onLetterChangedListener;
    }

    public interface OnLetterChangedListener {
        void onLetterChanged(String letter);
    }

}
