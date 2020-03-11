package clbrain.org.myapplication;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class DrawableOnTouchView extends View {
    final Drawable firework;
    float x;
    float y;
    List<FireworkData> myFireworks = new ArrayList<>();

    public DrawableOnTouchView(Context context) {
        this(context, null);
    }

    public DrawableOnTouchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawableOnTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DrawableOnTouchView);
        firework = getResources().getDrawable(a.getResourceId(R.styleable.DrawableOnTouchView_drawable_scr, R.drawable.firework));
        firework.setBounds(0, 0, firework.getIntrinsicWidth(), firework.getIntrinsicHeight());
        a.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            FireworkData currentFirework = new FireworkData();
            currentFirework.x = x;
            currentFirework.y = y;
            myFireworks.add(currentFirework);
            startAnimation(currentFirework);
        }
        return true;
    }

    private void startAnimation(@NonNull final FireworkData fireworkData) {
        final ValueAnimator sizeAnimation = new ValueAnimator();
        sizeAnimation.setFloatValues(0, 400);
        sizeAnimation.setDuration(1000);
        sizeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fireworkData.width = (float) animation.getAnimatedValue();
                fireworkData.height = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        sizeAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                myFireworks.remove(fireworkData);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                myFireworks.remove(fireworkData);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        sizeAnimation.start();
    }

    private void drawFireworkAtCoordinates(@NonNull final Canvas canvas, final float x, final float y, final float width, final float height) {
        final float halfX = width / 2f;
        final float halfY = height / 2f;
        final int realWidth = firework.getIntrinsicWidth();
        final int realHeight = firework.getIntrinsicHeight();
        final float scaleX = (width + 0f) / realWidth;
        final float scaleY = (height + 0f) / realHeight;
        canvas.translate(x - halfX, y - halfY);
        canvas.scale(scaleX, scaleY);
        firework.draw(canvas);
        canvas.scale(1 / scaleX, 1 / scaleY);
        canvas.translate(-x + halfX, -y + halfY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (final FireworkData currentFirework : myFireworks) {
            drawFireworkAtCoordinates(
                canvas,
                currentFirework.x,
                currentFirework.y,
                currentFirework.width,
                currentFirework.height
            );
        }
    }

    static class FireworkData {
        float x, y, width, height;
    }
}
