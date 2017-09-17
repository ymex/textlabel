package cn.ymex.little.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * ImageSpan
 */

public class ImageSpannable extends ImageSpan {

    private int ALIGN_FONTCENTER = 2;

    int imgWidth, imgHeight;

    public ImageSpannable(Context context, int resourceId) {
        super(context, resourceId);
    }

    public ImageSpannable(Context context, int resourceId, int verticalAlignment) {
        super(context, resourceId, verticalAlignment);
    }

    /**
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        this.imgWidth = width;
        this.imgHeight = height;
    }

    @Override
    public Drawable getDrawable() {
        Drawable drawable = super.getDrawable();
        if (drawable != null && imgWidth > 0 && imgHeight > 0) {
            drawable.setBounds(0, 0, imgWidth, imgHeight);
        }
        return drawable;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                     Paint paint) {


        Drawable drawable = getDrawable();
        canvas.save();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();


        int transY = bottom - drawable.getBounds().bottom;
        if (mVerticalAlignment == ALIGN_BASELINE) {
            transY -= fm.descent;
        } else if (mVerticalAlignment == ALIGN_FONTCENTER) {
            transY = ((y + fm.descent) + (y + fm.ascent)) / 2 - drawable.getBounds().bottom / 2;
        }

        canvas.translate(x, transY);
        drawable.draw(canvas);
        canvas.restore();
    }


    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
        if (fm != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;

            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        return rect.right;
    }
}

