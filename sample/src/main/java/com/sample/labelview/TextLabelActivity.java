package com.sample.labelview;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.ymex.view.label.SpanCell;
import cn.ymex.view.label.TextLabel;

public class TextLabelActivity extends AppCompatActivity {

    private TextLabel textLabelFirst;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_label);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        textLabelFirst = (TextLabel) findViewById(R.id.tl_first);
        SpanCell cell = SpanCell.build().text("美元");
        textLabelFirst.setEndText(cell);


        SpanCell.OnClickListener onClickListener = new SpanCell.OnClickListener() {
            @Override
            public void onClick(View view, SpanCell spanCell) {
                Toast.makeText(getBaseContext(), "----------", Toast.LENGTH_SHORT).show();
            }
        };
//        cell.setClickableSpan();
        textLabelFirst.setEndText(textLabelFirst.getEndSpanCell().setClickableSpan(onClickListener));

        textView = (TextView) findViewById(R.id.tv_text_view);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "----------", Toast.LENGTH_SHORT).show();
                System.out.println("--------------LLLLLLLLLLLLLLLLLLLLL");
            }
        });

    }
}
