package cn.ymex.little.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.TypedValue;


public class SpanCell {
    CharSequence text;
    float textSize;
    int textColor;

    boolean isImageSpanInLast = false;
    ImageSpan imageSpan;

    ClickableSpan clickableSpan, clickableImage;


    public static SpanCell build() {
        return new SpanCell("");
    }

    public void setText(CharSequence text) {
        this.text = text;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }


    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setImageSpan(ImageSpan imageSpan) {
        this.imageSpan = imageSpan;
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

    public SpanCell text(CharSequence text) {
        this.text = text;
        return this;
    }

    public SpanCell textColor(int textColor) {
        this.textColor = textColor;
        return this;
    }


    public SpanCell textSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public SpanCell imageSpan(ImageSpan imageSpan) {
        this.imageSpan = imageSpan;
        return this;
    }

    public SpanCell imageSpanInLast(boolean imageSpanInLast) {
        isImageSpanInLast = imageSpanInLast;
        return this;
    }


    public SpanCell clickableImage(ClickableSpan clickable) {
        this.clickableImage = clickable;
        return this;
    }

    public SpanCell clickableSpan(ClickableSpan clickable) {
        this.clickableSpan = clickable;
        return this;
    }


    public SpanCell(CharSequence text) {
        this(text, null);
    }

    public SpanCell(ImageSpan imageSpan) {
        this(Color.BLACK, dp2px(14), null, imageSpan);
    }

    public SpanCell(CharSequence text, ImageSpan imageSpan) {
        this(Color.BLACK, dp2px(14), text, imageSpan);
    }

    public SpanCell(int textColor, float textSize, CharSequence text, ImageSpan imageSpan) {
        this.textColor = textColor;
        this.textSize = textSize;
        this.text = TextUtils.isEmpty(text) ? "" : text;
        this.imageSpan = imageSpan;
    }


    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    public SpanCell(Context context, CharSequence text, int resourceId) {
        this(text, new ImageSpannable(context, resourceId));
    }


    public CharSequence getSpannable() {

        SpannableString textSpanString = new SpannableString(TextUtils.isEmpty(this.text) ? "" : this.text);
        int start = 0, end = textSpanString.length();
        ForegroundColorSpan span = new ForegroundColorSpan(textColor);
        textSpanString.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan((int) textSize);
        textSpanString.setSpan(sizeSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        SpannableString imageSpanString = null;
        if (imageSpan != null) {
            imageSpanString = new SpannableString(" ");
            int s = 0, e = imageSpanString.length();
            imageSpanString.setSpan(imageSpan, s, e, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (clickableImage != null) {
                imageSpanString.setSpan(clickableImage,s,e,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (isImageSpanInLast) {
            builder.append(textSpanString);
            if (imageSpanString != null) {
                builder.append(imageSpanString);
            }
        }else {
            if (imageSpanString != null) {
                builder.append(imageSpanString);
            }
            builder.append(textSpanString);
        }
        if (clickableSpan != null) {
            builder.setSpan(clickableSpan,0,builder.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder.subSequence(0, builder.length());
    }
}