package com.sample.labelview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;

import cn.ymex.little.view.TextLabel;
import cn.ymex.little.widget.Toaster;

public class MainActivity extends AppCompatActivity {

    TextLabel textLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toaster.init(this);

        textLabel = (TextLabel) findViewById(R.id.tv_label);
        textLabel.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString spannableString = insertImage();

        SpannableStringBuilder builder = new SpannableStringBuilder(spannableString);
        builder.append(insertImage());
        //textLabel.setFormatText("my name %1$s","ymex");
        textLabel.setOnTextLabelClickListener(TextLabel.LabelClickable.create(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toaster.show("----------hi");
            }
        }));
        //textLabel.setText(insertImage());
    }


    private SpannableString insertImage() {

        String text = "部分文字响应点击事件";
        TextLabel.Label label = new TextLabel.Label(this, text, R.mipmap.ic_launcher);

       // TextLabelView.Label label = new TextLabelView.Label(text);
        label.setClickableSpan(TextLabel.LabelClickable.create(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toaster.show("----------hello");
            }
        }));
        return label.getSpannable();
    }
}
