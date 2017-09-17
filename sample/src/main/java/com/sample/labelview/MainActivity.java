package com.sample.labelview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;

import cn.ymex.little.view.ImageSpannable;
import cn.ymex.little.view.SpanCell;
import cn.ymex.little.view.TextLabel;
import cn.ymex.little.widget.Toaster;
import cn.ymex.popup.dialog.PopupDialog;
import cn.ymex.popup.dialog.controller.AlertController;

public class MainActivity extends AppCompatActivity {

    TextLabel textLabel;
    TextLabel textSpanCellLable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toaster.init(this);

        textLabel = (TextLabel) findViewById(R.id.tv_label);
        textSpanCellLable = (TextLabel) findViewById(R.id.tv_label_2);
        textLabel.setMovementMethod(LinkMovementMethod.getInstance());

        findViewById(R.id.btn_settext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textLabel.setTextFormat("ymex");
            }
        });

        textLabel.setStartSpanCellClickListener(new SpanCell.OnClickListener() {
            @Override
            public void onClick(View view,SpanCell cell) {
                Toaster.show("Hi mm");
                PopupDialog.create(MainActivity.this)
                        .controller(AlertController
                                .build()
                                .title("提示")
                                .message(cell.getText().toString())
                                .positiveButton("Ok", null))
                        .show();
            }
        });

        findViewById(R.id.btn_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                ImageSpannable imageSpannable = new ImageSpannable(MainActivity.this, R.mipmap.ic_launcher);
                imageSpannable.setSize(480, 360);
                SpanCell spanCell = new SpanCell("bbap", imageSpannable);
                textSpanCellLable.setText(spanCell.getSpannable());
            }
        });
    }
}
