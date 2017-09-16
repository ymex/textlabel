package cn.ymex.little.view;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;



public class SpanCell {
    CharSequence text;
    float textSize;
    int textColor;

    boolean isImageSpanInLast = false;

    int imgWidth,imgHeight;

    ImageSpan imageSpan;
    ClickableSpan clickableSpan, clickableImage;

    public SpanCell(CharSequence text, ImageSpan imageSpan) {
        this.text = TextUtils.isEmpty(text) ? "" : text;
        this.imageSpan = imageSpan;
    }

    public SpanCell(ImageSpan imageSpan) {
        this(null, imageSpan);
    }

    public SpanCell(int color, float size, CharSequence text) {
        this(text, null);
        this.textColor = color;
        this.textSize = size;
    }

    public SpanCell(CharSequence text) {
        this(text, null);
    }

    public SpanCell(Context context, CharSequence text, int resourceId) {
        this(text, new ImageSpannable(context, resourceId));
    }

    public void setImageSpanInLast(boolean imageSpanInLast) {
        isImageSpanInLast = imageSpanInLast;
    }


    public void setClickableImage(ClickableSpan clickable) {
        this.clickableImage = clickable;
    }

    public void setClickableSpan(ClickableSpan clickable) {
        this.clickableSpan = clickable;
        getSpannable();
    }

    public SpannableString getSpannable() {
        SpannableString spannableString;
        if (imageSpan != null) {
            CharSequence at = isImageSpanInLast ? text + "I" : "I" + text;
            int start = isImageSpanInLast ? at.length() - 1 : 0;
            int end = isImageSpanInLast ? at.length() : 1;

            spannableString = new SpannableString(at);
            spannableString.setSpan(this.imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (clickableImage != null) {
                spannableString.setSpan(clickableImage, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (clickableSpan != null) {
                start = isImageSpanInLast ? 0 : 1;
                end = isImageSpanInLast ? at.length() - 1 : at.length();
                spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else {
            spannableString = new SpannableString(text);
            if (clickableSpan != null) {
                spannableString.setSpan(clickableSpan, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        if (!TextUtils.isEmpty(text)) {


            int start = 0;
            int end = text.length();
            if (imageSpan != null) {
                start = isImageSpanInLast ? 0 : 1;
                end = isImageSpanInLast ? text.length() - 1 : text.length();
            }
            if (textColor != 0) {
                ForegroundColorSpan span = new ForegroundColorSpan(textColor);
                spannableString.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (textSize != 0) {
                AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan((int) textSize);
                spannableString.setSpan(sizeSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        if (clickableSpan != null) {
            spannableString.setSpan(clickableSpan, 0, imageSpan != null ? text.length() + 1 : text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }
}