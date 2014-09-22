package ru.katso.livebutton;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Button;

public class LiveButton extends Button {

    private int backgroundColor = 0xffcccccc;
    private int shadowColor = 0xffaaaaaa;
    private float corners = 2.5f;
    private float pressedHeight = 1.5f;
    private float normalHeight = 4f;

    public LiveButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiveButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setIncludeFontPadding(false);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.livebutton, defStyle, 0);
        if (typedArray != null) {
            backgroundColor = typedArray.getColor(R.styleable.livebutton_backgroundColor, backgroundColor);
            shadowColor = typedArray.getColor(R.styleable.livebutton_shadowColor, shadowColor);
            corners = typedArray.getDimension(R.styleable.livebutton_corners, dipToPixels(getContext(), corners));
            pressedHeight = typedArray.getDimension(R.styleable.livebutton_pressedHeight, dipToPixels(getContext(), pressedHeight));
            normalHeight = typedArray.getDimension(R.styleable.livebutton_normalHeight, dipToPixels(getContext(), normalHeight));
            typedArray.recycle();
        }

        setGravity(Gravity.CENTER);
        setOnClickListener(null);

        initBackground();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled()) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                setPressed(true);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float additionalPadding;

        if (isPressed()) {
            additionalPadding = (getHeight() + normalHeight) / 2;
            additionalPadding -= pressedHeight;
        } else {
            additionalPadding = (getHeight() - normalHeight) / 2;
        }
        additionalPadding -= getLineHeight() / 2;

        TextPaint textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();
        canvas.save();
        canvas.translate(0, additionalPadding);
        if (getLayout() != null) {
            getLayout().draw(canvas);
        }
        canvas.restore();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    private void initBackground() {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, getLayerList(true));
        states.addState(new int[]{}, getLayerList(false));

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(states);
        } else {
            setBackground(states);
        }
    }

    private LayerDrawable getLayerList(boolean isPressed) {

        float[] radii = new float[8];
        for (int i = 0; i < radii.length; i++) {
            radii[i] = corners;
        }

        ShapeDrawable shapeShadow = new ShapeDrawable(new RectShape());
        shapeShadow.getPaint().setColor(shadowColor);
        shapeShadow.setShape(new RoundRectShape(radii, null, null));

        ShapeDrawable shapeBackground = new ShapeDrawable(new RectShape());
        shapeBackground.getPaint().setColor(backgroundColor);
        shapeBackground.setShape(new RoundRectShape(radii, null, null));

        LayerDrawable composite = new LayerDrawable(new Drawable[]{shapeShadow, shapeBackground});

        if (isPressed) {
            composite.setLayerInset(0, 0, (int) (normalHeight - pressedHeight), 0, 0);
            composite.setLayerInset(1, 0, (int) (normalHeight - pressedHeight), 0, (int) pressedHeight);
        } else {
            composite.setLayerInset(0, 0, 0, 0, 0);
            composite.setLayerInset(1, 0, 0, 0, (int) normalHeight);
        }

        return composite;
    }

    private float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        initBackground();
    }

    public int getShadowColor() {
        return shadowColor;
    }

    public void setShadowColor(int shadowColor) {
        this.shadowColor = shadowColor;
        initBackground();
    }

    public float getCorners() {
        return corners;
    }

    public void setCorners(float corners) {
        this.corners = corners;
        initBackground();
    }

    public float getPressedHeight() {
        return pressedHeight;
    }

    public void setPressedHeight(float pressedHeight) {
        this.pressedHeight = pressedHeight;
        initBackground();
    }

    public float getNormalHeight() {
        return normalHeight;
    }

    public void setNormalHeight(float normalHeight) {
        this.normalHeight = normalHeight;
        initBackground();
    }
}
