package cn.ymex.view.label;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;


/**
 * Created by ymexc on 2018/1/15.
 * About:字体背景
 */

public class BackgroundSpan extends ReplacementSpan {

    private int cornerRadius = 8;
    private int backgroundColor = 0;
    private int textColor = 0;

    public BackgroundSpan(int backgroundColor, int textColor, int radius) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.cornerRadius = radius;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        RectF rect = new RectF(x, top, x + measureText(paint, text, start, end), bottom);
        paint.setColor(backgroundColor);
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);
        paint.setColor(textColor);
        canvas.drawText(text, start, end, x, y, paint);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return Math.round(paint.measureText(text, start, end));
    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }
}
