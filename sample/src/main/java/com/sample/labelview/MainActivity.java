package com.sample.labelview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;

import cn.ymex.little.view.TextLabelView;
import cn.ymex.little.widget.Toaster;

public class MainActivity extends AppCompatActivity {

    TextLabelView labelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toaster.init(this);

        labelView = (TextLabelView) findViewById(R.id.tv_label);
        labelView.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString spannableString = insertImage();

        SpannableStringBuilder builder = new SpannableStringBuilder(spannableString);
        builder.append(insertImage());
        //labelView.setFormatText("my name %1$s","ymex");
        labelView.setOnTextLabelClickListener(TextLabelView.LabelClickable.create(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toaster.show("----------hi");
            }
        }));
        //labelView.setText(insertImage());
    }


    private SpannableString insertImage() {

        String text = "部分文字响应点击事件";
        TextLabelView.Label label = new TextLabelView.Label(this, text, R.mipmap.ic_launcher);

       // TextLabelView.Label label = new TextLabelView.Label(text);
        label.setClickableSpan(TextLabelView.LabelClickable.create(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toaster.show("----------hello");
            }
        }));
        return label.getSpannable();
    }
}
