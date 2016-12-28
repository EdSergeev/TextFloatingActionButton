package com.github.edsergeev;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.text.TextPaint;
import android.util.AttributeSet;


public class TextFloatingActionButton extends FloatingActionButton {
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int MONOSPACE = 3;

    private final TextPaint mTextPaint;
    private final int yCenterOffset;
    private ColorStateList mTextColor;
    private CharSequence mText = "";
    private int textXoffset;
    private int textYoffset;

    public TextFloatingActionButton(Context context) {
        this(context, null);
    }

    public TextFloatingActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final Resources res = getResources();

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.density = res.getDisplayMetrics().density;
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        yCenterOffset = -(int) ((mTextPaint.descent() + mTextPaint.ascent()) / 2);

        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        CharSequence text = "";
        ColorStateList textColor = null;
        int textSize = 15;
        int typefaceIndex = -1;
        int styleIndex = -1;
        String fontFamily = null;
        boolean fontFamilyExplicit = false;

        final Resources.Theme theme = context.getTheme();

        TypedArray a = theme.obtainStyledAttributes(
                attrs, R.styleable.TextFloatingActionButton, defStyleAttr, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            if (attr == R.styleable.TextFloatingActionButton_android_text) {
                text = a.getText(attr);

            } else if (attr == R.styleable.TextFloatingActionButton_android_textColor) {
                textColor = a.getColorStateList(attr);

            } else if (attr == R.styleable.TextFloatingActionButton_android_textSize) {
                textSize = a.getDimensionPixelSize(attr, textSize);

            } else if (attr == R.styleable.TextFloatingActionButton_android_typeface) {
                typefaceIndex = a.getInt(attr, typefaceIndex);

            } else if (attr == R.styleable.TextFloatingActionButton_android_fontFamily) {
                fontFamily = a.getString(attr);
                fontFamilyExplicit = true;

            } else if (attr == R.styleable.TextFloatingActionButton_android_textStyle) {
                styleIndex = a.getInt(attr, styleIndex);

            } else if (attr == R.styleable.TextFloatingActionButton_text_x_offset) {
                textXoffset = a.getLayoutDimension(attr, 0);

            } else if (attr == R.styleable.TextFloatingActionButton_text_y_offset) {
                textYoffset = a.getLayoutDimension(attr, 0);
            }
        }
        a.recycle();

        if (typefaceIndex != -1 && !fontFamilyExplicit) {
            fontFamily = null;
        }
        setTypefaceFromAttrs(fontFamily, typefaceIndex, styleIndex);

        setTextColor(textColor != null ? textColor : ColorStateList.valueOf(Color.BLACK));

        mTextPaint.setTextSize(textSize);

        setText(text);
    }

    private void setTypefaceFromAttrs(String familyName, int typefaceIndex, int styleIndex) {
        Typeface tf = null;
        if (familyName != null) {
            tf = Typeface.create(familyName, styleIndex);
            if (tf != null) {
                setTypeface(tf);
                return;
            }
        }
        switch (typefaceIndex) {
            case SANS:
                tf = Typeface.SANS_SERIF;
                break;

            case SERIF:
                tf = Typeface.SERIF;
                break;

            case MONOSPACE:
                tf = Typeface.MONOSPACE;
                break;
        }

        setTypeface(tf, styleIndex);
    }

    public void setTypeface(Typeface tf) {
        if (mTextPaint.getTypeface() != tf) {
            mTextPaint.setTypeface(tf);
            invalidate();
        }
    }

    public void setTypeface(Typeface tf, int style) {
        if (style > 0) {
            if (tf == null) {
                tf = Typeface.defaultFromStyle(style);
            } else {
                tf = Typeface.create(tf, style);
            }

            setTypeface(tf);
            // now compute what (if any) algorithmic styling is needed
            int typefaceStyle = tf != null ? tf.getStyle() : 0;
            int need = style & ~typefaceStyle;
            mTextPaint.setFakeBoldText((need & Typeface.BOLD) != 0);
            mTextPaint.setTextSkewX((need & Typeface.ITALIC) != 0 ? -0.25f : 0);
        } else {
            mTextPaint.setFakeBoldText(false);
            mTextPaint.setTextSkewX(0);
            setTypeface(tf);
        }
    }

    public void setTextColor(ColorStateList colors) {
        if (colors == null) {
            throw new NullPointerException();
        }

        mTextColor = colors;
    }

    public void setText(CharSequence text) {
        mText = text;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int color = mTextColor.getColorForState(getDrawableState(), Color.BLACK);
        mTextPaint.setColor(color);

        canvas.save();

        canvas.translate(getWidth() / 2, getHeight() / 2);

        canvas.drawText(mText, 0, mText.length(), textXoffset, textYoffset + yCenterOffset, mTextPaint);

        canvas.restore();
    }
}
