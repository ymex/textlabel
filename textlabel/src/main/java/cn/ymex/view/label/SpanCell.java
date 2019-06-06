package cn.ymex.view.label;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.TypedValue;
import android.view.View;


public class SpanCell {
    CharSequence text;
    private float textSize;
    private int textColor;
    private int linkColor;

    private boolean isImageSpanInLast = false;
    ImageSpan imageSpan;
    private BackgroundSpan backgroundSpan;
    private ClickableSpan clickableSpan, clickableImage;


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

    public void setLinkColor(int linkColor) {
        this.linkColor = linkColor;
    }


    public CharSequence getText() {
        return text;
    }

    public float getTextSize() {
        return textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getLinkColor() {
        return linkColor;
    }

    public boolean isImageSpanInLast() {
        return isImageSpanInLast;
    }

    public ImageSpan getImageSpan() {
        return imageSpan;
    }

    /**
     * 图文点击事件
     *
     * @param listener
     */
    public SpanCell setClickableSpan(OnClickListener listener) {
        this.clickableSpan = SpanCell.SpanClickListener.onClick(listener, this);
        getSpannable();
        return this;
    }

    /**
     * 图片点击事件
     *
     * @param listener
     */
    public SpanCell setClickableImage(OnClickListener listener) {
        this.clickableImage = SpanCell.SpanClickListener.onClick(listener, this);
        getSpannable();
        return this;
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


    public SpanCell linkColor(int linkColor) {
        this.linkColor = linkColor;
        return this;
    }


    /**
     * text background
     * @param color background color
     * @param textColor text color
     * @param radius background radius
     * @return
     */
    public SpanCell setBackgroundSpan(int color,int textColor,int radius) {
        this.backgroundSpan = new BackgroundSpan(color,textColor,radius);
        return this;
    }


    public SpanCell(CharSequence text) {
        this(text, null);
    }

    public SpanCell(ImageSpan imageSpan) {
        this(Color.BLACK, dp2px(14), null, Color.BLACK, imageSpan);
    }

    public SpanCell(CharSequence text, ImageSpan imageSpan) {
        this(Color.BLACK, dp2px(14), text, Color.BLACK, imageSpan);
    }

    public SpanCell(int textColor, float textSize, CharSequence text, int linkColor, ImageSpan imageSpan) {
        this.textColor = textColor;
        this.textSize = textSize;
        this.linkColor = linkColor;
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
                imageSpanString.setSpan(clickableImage, s, e, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (isImageSpanInLast) {
            builder.append(textSpanString);
            if (imageSpanString != null) {
                builder.append(imageSpanString);
            }
        } else {
            if (imageSpanString != null) {
                builder.append(imageSpanString);
            }
            builder.append(textSpanString);
        }
        if (clickableSpan != null) {
            builder.setSpan(clickableSpan, 0, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (backgroundSpan != null) {
            builder.setSpan(backgroundSpan, 0, builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder.subSequence(0, builder.length());
    }

    public interface OnClickListener {
        void onClick(View view, SpanCell spanCell);
    }


    /**
     * click listener
     */
    public static class SpanClickListener extends ClickableSpan {

        SpanCell.OnClickListener onClickListener;
        SpanCell spanCell;

        private SpanClickListener(SpanCell.OnClickListener clickListener, SpanCell spanCell) {
            this.spanCell = spanCell;
            this.onClickListener = clickListener;
        }

        public static SpanClickListener onClick(SpanCell.OnClickListener listener, SpanCell spanCell) {
            return new SpanClickListener(listener, spanCell);
        }

        public static SpanClickListener onClick(SpanCell.OnClickListener listener) {
            return new SpanClickListener(listener, null);
        }

        @Override
        public void onClick(View view) {
            if (this.onClickListener != null) {
                this.onClickListener.onClick(view, spanCell);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            if (spanCell != null) {
                ds.setColor(spanCell.getLinkColor());
            }
            ds.setUnderlineText(false);//下划线
            ds.clearShadowLayer();
        }
    }

}