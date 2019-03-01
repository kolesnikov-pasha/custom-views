package clbrain.org.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class RoundProgressLine extends View {

    private float iconHeight = 20f, iconWidth = 20f,  progress = 65;
    private Drawable icon;
    private String text;
    private int color = Color.parseColor("#CD5C5C"), strokeWidth = 10;

    public RoundProgressLine(Context context) {
        super(context);
        p.setStyle(Paint.Style.STROKE);
    }

    public RoundProgressLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        p.setStyle(Paint.Style.STROKE);
        handleAttributes(context, attrs);
    }

    public RoundProgressLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        p.setStyle(Paint.Style.STROKE);
        handleAttributes(context, attrs);
    }

    private void handleAttributes(Context context, AttributeSet attributes){
        TypedArray array = context.obtainStyledAttributes(attributes, R.styleable.RoundProgressLine);
        for (int i = 0; i < array.length(); i++) {
            int id = array.getIndex(i);
            switch (id){
                case R.styleable.RoundProgressLine_icon:
                    icon = array.getDrawable(i);
                    break;
                case R.styleable.RoundProgressLine_line_color:
                    color = array.getColor(i, Color.parseColor("#CD5C5C"));
                    p.setColor(color);
                    break;
                case R.styleable.RoundProgressLine_text:
                    text = array.getString(i);
                    textPaint.setTextAlign(Paint.Align.CENTER);
                    textPaint.setTextSize(25);
                    break;
                case R.styleable.RoundProgressLine_progress_percent:
                    progress = array.getInt(i, 0);
                    break;
                case R.styleable.RoundProgressLine_line_width:
                    strokeWidth = array.getInt(i, 10);
                    p.setStrokeWidth(strokeWidth);
                    break;
                case R.styleable.RoundProgressLine_icon_height:
                    iconHeight = array.getDimension(i, 20f);
                    break;
                case R.styleable.RoundProgressLine_icon_width:
                    iconWidth = array.getDimension(i, 20f);
                    break;
                case R.styleable.RoundProgressLine_text_size:
                    textPaint.setTextSize(array.getDimension(i, 16));
                    break;
                case R.styleable.RoundProgressLine_text_color:
                    textPaint.setColor(array.getColor(i, Color.parseColor("#000000")));
                    break;
            }
        }
        array.recycle();
    }

    Paint p = new Paint(), textPaint = new Paint();
    RectF rectF = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        int d = strokeWidth / 2;
        float start = -90f, angle = 360.0f * progress / 100.0f;
        rectF.set(d, d, getWidth() - d,getHeight() - d);
        canvas.drawArc(rectF, start, angle, false, p);
        int cx = getWidth() / 2, cy = getHeight() / 2;
        int sx = (int) (iconWidth / 2), sy = (int) (iconHeight / 2);
        icon.setBounds(cx - sx, cy - sy, cx + sx, cy + sy);
        icon.draw(canvas);
        canvas.drawText(text, cx, cy + sy + 10, textPaint);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(final float p) {
        ValueAnimator animator = ValueAnimator.ofFloat(this.progress, p);
        float delta = Math.abs(p - progress);
        animator.setDuration((long) (delta / 100 * 1000));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }
}
