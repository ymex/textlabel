package com.sample.labelview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import cn.ymex.label.TextLabelView;

public class MainActivity extends AppCompatActivity {

    TextLabelView labelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labelView = (TextLabelView) findViewById(R.id.tv_label);
        labelView.setText(insertImage());
    }


    private SpannableString insertImage() {

        ImageSpan imgSpan = new TextLabelView.ImageSpannable(this, R.mipmap.ic_launcher_round);

        SpannableString spannableString = new SpannableString("部分文字响应点击事件");
        spannableString.setSpan(imgSpan,0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
