package cn.ymex.little.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;

import cn.ymex.little.R;


public class TextLabel extends AppCompatTextView {

    private Label startLabel;
    private Label endLabel;
    private Label textLabel;

    public TextLabel(Context context) {
        this(context, null);
    }

    public TextLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TextLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            dealAttr(attrs);
        }
        //this.setLinkTextColor(getTextColors());

        this.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
        this.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明


        setText(buildSpannableString(startLabel, textLabel, endLabel));
    }

    private CharSequence buildSpannableString(Label... labels) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (Label label : labels) {
            if (label == null) {
                continue;
            }
            if (label.imageSpan != null || label.text != null) {
                builder.append(label.getSpannable());
            }
        }
        return builder.subSequence(0, builder.length());
    }

    private void dealAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TextLabel, 0, 0);

        int textColor = getTextColors().getDefaultColor();
        String text = getText().toString();
        float textSize = getTextSize();
        textLabel = new Label(textColor, textSize, text);

        String startText = typedArray.getString(R.styleable.TextLabel_start_text);
        int startTextColor = typedArray.getColor(R.styleable.TextLabel_start_text_color, textColor);
        int startTextSize = typedArray.getDimensionPixelSize(R.styleable.TextLabel_start_text_size, (int) textSize);
        startLabel = new Label(startTextColor, startTextSize, startText);

        String endText = typedArray.getString(R.styleable.TextLabel_end_text);
        int endTextColor = typedArray.getColor(R.styleable.TextLabel_end_text_color, textColor);
        int endTextSize = typedArray.getDimensionPixelSize(R.styleable.TextLabel_end_text_size, (int) textSize);
        endLabel = new Label(endTextColor, endTextSize, endText);

        typedArray.recycle();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }


    public void setFormatText(CharSequence format, Object... args) {
        if (args == null || args.length == 0) {
            this.setText(format);
            return;
        }
        this.setText(String.format(format.toString(), args));
    }

    public void setFormatText(@StringRes int str, Object... args) {
        String format = getResources().getString(str);
        this.setFormatText(format, args);
    }

    public Label getEndLabel() {
        return endLabel;
    }

    public Label getStartLabel() {
        return startLabel;
    }



    public void setOnTextLabelClickListener(LabelClickable clickListener) {
        textLabel.setClickableSpan(clickListener);
        setText(buildSpannableString(startLabel, textLabel, endLabel));
    }

    public static class ImageSpannable extends ImageSpan {

        private int ALIGN_FONTCENTER = 2;

        public ImageSpannable(Context context, int resourceId) {
            super(context, resourceId);
        }

        public ImageSpannable(Context context, int resourceId, int verticalAlignment) {
            super(context, resourceId, verticalAlignment);
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

    public static class Label {
        CharSequence text;
        ImageSpan imageSpan;
        ClickableSpan clickableText, clickableImage, clickableSpan;

        int textColor;
        float textSize;

        boolean isImageSpanInLast = false;

        public Label(CharSequence text, ImageSpan imageSpan) {
            this.text = TextUtils.isEmpty(text) ? "" : text;
            this.imageSpan = imageSpan;
        }

        public Label(ImageSpan imageSpan) {
            this(null, imageSpan);
        }

        public Label(int color, float size, CharSequence text) {
            this(text, null);
            this.textColor = color;
            this.textSize = size;
        }

        public Label(CharSequence text) {
            this(text, null);
        }

        public Label(Context context, CharSequence text, int resourceId) {
            this(text, new ImageSpannable(context, resourceId));
        }

        public void setImageSpanInLast(boolean imageSpanInLast) {
            isImageSpanInLast = imageSpanInLast;
        }

        public void setClickableText(ClickableSpan clickable) {
            this.clickableText = clickable;
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
                if (clickableText != null) {
                    start = isImageSpanInLast ? 0 : 1;
                    end = isImageSpanInLast ? at.length() - 1 : at.length();
                    spannableString.setSpan(clickableText, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else {
                spannableString = new SpannableString(text);
                if (clickableText != null) {
                    spannableString.setSpan(clickableText, 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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

    public static class LabelClickable extends ClickableSpan {

        OnClickListener onClickListener;

        private LabelClickable(OnClickListener clickListener) {
            this.onClickListener = clickListener;
        }

        public static LabelClickable create(OnClickListener listener) {
            return new LabelClickable(listener);
        }

        @Override
        public void onClick(View view) {
            if (this.onClickListener != null) {
                this.onClickListener.onClick(view);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
            ds.setUnderlineText(false);
        }
    }
}
