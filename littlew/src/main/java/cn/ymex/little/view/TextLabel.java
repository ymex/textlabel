package cn.ymex.little.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import cn.ymex.little.R;


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

        String startText = typedArray.getString(R.styleable.TextLabel_startText);
        int startTextColor = typedArray.getColor(R.styleable.TextLabel_startTextColor, textColor);
        int startTextSize = typedArray.getDimensionPixelSize(R.styleable.TextLabel_startTextSize, (int) textSize);
        startSpanCell = SpanCell.build().text(startText).textColor(startTextColor).textSize(startTextSize);

        String endText = typedArray.getString(R.styleable.TextLabel_endText);
        int endTextColor = typedArray.getColor(R.styleable.TextLabel_endTextColor, textColor);
        int endTextSize = typedArray.getDimensionPixelSize(R.styleable.TextLabel_endTextSize, (int) textSize);
        endSpanCell = SpanCell.build().text(endText).textColor(endTextColor).textSize(endTextSize);

        format = typedArray.getString(R.styleable.TextLabel_format);

        typedArray.recycle();
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            dealAttr(attrs);
        }

        //this.setLinkTextColor(getTextColors());
        this.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
        this.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明
    }

    @Override
    public CharSequence getText() {
        return textSpanCell == null ? super.getText() : textSpanCell.text;
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

    public void setStartSpanCellClickListener(OnClickListener onClickListener) {
        if (this.startSpanCell != null) {
            startSpanCell.setClickableSpan(SpanClickListener.onClick(onClickListener));
        }
    }

    /**
     * click listener
     */
    public static class SpanClickListener extends ClickableSpan {

        OnClickListener onClickListener;

        private SpanClickListener(OnClickListener clickListener) {
            this.onClickListener = clickListener;
        }

        public static SpanClickListener onClick(OnClickListener listener) {
            return new SpanClickListener(listener);
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
