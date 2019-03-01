package clbrain.org.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class CustomToggle extends android.support.v7.widget.AppCompatButton implements View.OnClickListener {
    private static final int			STATE_COUNT = 4;
    private int					state;
    private String[]				txt	= new String[4];
    private Drawable[]			img	= new Drawable[4];


    private OnViewChangeListener	listener = new OnViewChangeListener() {
        @Override
        public void onChangeState(int i) {

        }
    };

    public interface OnViewChangeListener {
        public void onChangeState(int i);
    }

    public void setOnViewChangeListener(OnViewChangeListener l) {
        this.listener = l;
    }

    public CustomToggle(Context context) {
        this(context, null);
    }

    public CustomToggle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        handleAttributes(context, attrs);
    }

    public CustomToggle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnClickListener(this);
        handleAttributes(context, attrs);
    }

    private void handleAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomToggle);
        final int count = a.getIndexCount();
        // Перебираем все имеющиеся атрибуты
        // И присваиваем соответствующим переменным
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            switch (attr) {

                case R.styleable.CustomToggle_customText_0:
                    txt[0] = a.getString(attr);
                    break;

                case R.styleable.CustomToggle_customText_1:
                    txt[1] = a.getString(attr);
                    break;

                case R.styleable.CustomToggle_customText_2:
                    txt[2] = a.getString(attr);
                    break;

                case R.styleable.CustomToggle_customText_3:
                    txt[3] = a.getString(attr);
                    break;

                case R.styleable.CustomToggle_customImage_0:
                    img[0] = a.getDrawable(attr);
                    break;

                case R.styleable.CustomToggle_customImage_1:
                    img[1] = a.getDrawable(attr);
                    break;

                case R.styleable.CustomToggle_customImage_2:
                    img[2] = a.getDrawable(attr);
                    break;

                case R.styleable.CustomToggle_customImage_3:
                    img[3] = a.getDrawable(attr);
                    break;
            }
        }
        a.recycle();
        setValues();
    }

    // Применяем полученные свойства к контролу
    private void setValues() {
        setText(txt[state]);
        setCompoundDrawablesWithIntrinsicBounds(img[state], null, null, null);
    }

    public void setState(int i) {
        this.state = i;
        setValues();
    }

    private void changeState() {
        if (state < STATE_COUNT - 1) state++;
        else state = 0;
        setValues();
        listener.onChangeState(state);
    }

    public int getState() {
        return state;
    }

    public String getCurrentText() {
        return txt[state];
    }

    @Override
    public void onClick(View v) {
        changeState();
    }
}