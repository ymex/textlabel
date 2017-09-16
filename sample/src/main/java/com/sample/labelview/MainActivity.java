package com.sample.labelview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        findViewById(R.id.btn_settext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textLabel.setTextFormat("ymex");
            }
        });

        textLabel.setOnTextLabelClickListener(TextLabel.SpanClickListener.onClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toaster.show("Hi mm");
            }
        }));
    }
}
