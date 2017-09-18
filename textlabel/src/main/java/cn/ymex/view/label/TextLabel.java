package cn.ymex.view.label;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.StringRes;
import android.support.annotation.StyleableRes;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import cn.ymex.textlabel.R;


public class TextLabel extends AppCompatTextView {

    private SpanCell startSpanCell;
    private SpanCell endSpanCell;
    private SpanCell textSpanCell;
    private CharSequence format;


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


    private void dealAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TextLabel, 0, 0);

        int textColor = getTextColors().getDefaultColor();
        String text = getText().toString();
        float textSize = getTextSize();
        textSpanCell = SpanCell.build().text(text).textColor(textColor).textSize(textSize);

        startSpanCell = resolveAttr(typedArray, R.styleable.TextLabel_startText,
                R.styleable.TextLabel_startTextColor,
                R.styleable.TextLabel_startTextSize,
                R.styleable.TextLabel_startLinkColor,
                R.styleable.TextLabel_startDrawable,
                R.styleable.TextLabel_startDrawableSize,
                R.styleable.TextLabel_startDrawableInLast,
                R.styleable.TextLabel_startDrawableVerticalAlignment,
                textColor, (int) textSize);

        endSpanCell = resolveAttr(typedArray, R.styleable.TextLabel_endText,
                R.styleable.TextLabel_endTextColor,
                R.styleable.TextLabel_endTextSize,
                R.styleable.TextLabel_startLinkColor,
                R.styleable.TextLabel_endDrawable,
                R.styleable.TextLabel_endDrawableSize,
                R.styleable.TextLabel_endDrawableInLast,
                R.styleable.TextLabel_endDrawableVerticalAlignment,
                textColor, (int) textSize);
        format = typedArray.getString(R.styleable.TextLabel_format);
        typedArray.recycle();
    }


    private SpanCell resolveAttr(TypedArray typedArray, @StyleableRes int text, @StyleableRes int textColor,
                                 @StyleableRes int textSize, @StyleableRes int linkColor,
                                 @StyleableRes int drawable, @StyleableRes int drawableSize,
                                 @StyleableRes int drawableInlast, @StyleableRes int verticalAlignment,
                                 int defTextColor, int defTextSize) {

        String t = typedArray.getString(text);
        int tColor = typedArray.getColor(textColor, defTextColor);
        int tSize = typedArray.getDimensionPixelSize(textSize, defTextSize);
        int tlinkColor = typedArray.getColor(linkColor, defTextColor);
        int tDrawable = typedArray.getResourceId(drawable, -1);
        int tDsize = typedArray.getDimensionPixelSize(drawableSize, -1);
        boolean isLast = typedArray.getBoolean(drawableInlast, false);
        int v = typedArray.getInt(verticalAlignment, ImageSpannable.ALIGN_FONTCENTER);
        ImageSpannable imageSpan = null;
        if (tDrawable > 0) {
            imageSpan = new ImageSpannable(getContext(), tDrawable, v);
            if (tDsize > 0) {
                imageSpan.setSize(tDsize, tDsize);
            }
        }
        return SpanCell.build().text(t).textColor(tColor).textSize(tSize).linkColor(tlinkColor)
                .imageSpan(imageSpan).imageSpanInLast(isLast);
    }


    private void init(AttributeSet attrs) {
        if (attrs != null) {
            dealAttr(attrs);
        }
        this.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
        this.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明
    }

    @Override
    public CharSequence getText() {
        return textSpanCell == null ? super.getText() : textSpanCell.text;
    }

    public void setText(SpanCell... spanCells) {
        setText(buildSpannableString(spanCells));
    }

    /**
     * 获取显示的文字
     *
     * @return
     */
    public CharSequence getDisplayText() {
        return buildSpannableString(startSpanCell, textSpanCell, endSpanCell).toString();
    }

    private CharSequence buildSpannableString(SpanCell... spanCells) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (SpanCell spanCell : spanCells) {
            if (spanCell == null) {
                continue;
            }
            if (spanCell.imageSpan != null || spanCell.text != null) {
                builder.append(spanCell.getSpannable());
            }
        }
        return builder.subSequence(0, builder.length());
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        if (textSpanCell != null) {
            textSpanCell.text = text;
            super.setText(buildSpannableString(startSpanCell, textSpanCell, endSpanCell), type);
            return;
        }
        super.setText(text, type);
    }


    public void setTextFormat(Object... args) {
        setTextf(this.format, args);
    }

    /**
     * 格式化设置文字
     *
     * @param format format
     * @param args   parameter
     */
    public void setTextf(CharSequence format, Object... args) {
        if (TextUtils.isEmpty(format)) {
            this.setText("");
            return;
        }
        if (args == null || args.length == 0) {
            this.setText(format);
            return;
        }
        this.setText(String.format(format.toString(), args));
    }

    /**
     * 格式化设置文字
     *
     * @param str  format
     * @param args parameter
     */
    public void setTextf(@StringRes int str, Object... args) {
        String format = getResources().getString(str);
        this.setTextf(format, args);
    }

    public void setFormat(CharSequence formatText) {
        this.format = formatText;
    }

    public CharSequence getFormat() {
        return format;
    }


    public void setStartSpanCell(SpanCell startSpanCell) {
        this.startSpanCell = startSpanCell;
    }

    public void setEndSpanCell(SpanCell endSpanCell) {
        this.endSpanCell = endSpanCell;
    }

    public void setTextSpanCell(SpanCell textSpanCell) {
        this.textSpanCell = textSpanCell;
    }

    public void setStartSpanCellClickListener(SpanCell.OnClickListener onClickListener) {
        if (this.startSpanCell != null) {
            startSpanCell.setClickableSpan(SpanClickListener.onClick(onClickListener, startSpanCell));
        }
    }

    public void setTextSpanCellClickListener(SpanCell.OnClickListener onClickListener) {
        if (this.textSpanCell != null) {
            textSpanCell.setClickableSpan(SpanClickListener.onClick(onClickListener, textSpanCell));
        }
    }

    public void setEndSpanCellClickListener(SpanCell.OnClickListener onClickListener) {
        if (this.endSpanCell != null) {
            endSpanCell.setClickableSpan(SpanClickListener.onClick(onClickListener, endSpanCell));
        }
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
