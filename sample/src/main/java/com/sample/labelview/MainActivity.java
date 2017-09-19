package com.sample.labelview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ymex.pure.adapter.ListViewAdapter;
import cn.ymex.view.label.ImageSpannable;
import cn.ymex.view.label.SpanCell;
import cn.ymex.view.label.TextLabel;
import cn.ymex.popup.dialog.PopupDialog;
import cn.ymex.popup.dialog.controller.AlertController;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextLabel textLabel;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textLabel = (TextLabel) findViewById(R.id.tv_label);
        listView = (ListView) findViewById(R.id.lv_listview);

        TextAdapter adapter = new TextAdapter();
        adapter.setData(getThemeData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }


    private List<String> getThemeData() {
        return new ArrayList<String>() {{
            add("图文单元");
            add("设置点击事件");
        }};

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println("-------------============" + i);
        switch (i) {
            case 0:
                ImageSpannable imageSpannable = new ImageSpannable(MainActivity.this, R.mipmap.ic_launcher);
                imageSpannable.setSize(48, 48);
                SpanCell spanCell = new SpanCell("设置右边图片", imageSpannable);
                spanCell.imageSpanInLast(true);

                textLabel.setText(spanCell);
                break;
            case 1:
                final SpanCell spanCell1 = new SpanCell("点击事件");

                spanCell1.setClickableSpan(new SpanCell.OnClickListener() {
                    @Override
                    public void onClick(View view, SpanCell spanCell) {
                        PopupDialog.create(MainActivity.this)
                                .controller(AlertController
                                        .build()
                                        .title("提示")
                                        .message(spanCell.getText().toString())
                                        .positiveButton("Ok", null))
                                .show();
                    }
                });
                textLabel.setText(spanCell1);

                break;
        }
    }

    public static class TextAdapter extends ListViewAdapter<String, ListViewAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false));
        }

        @Override
        public void onBindViewHolder(int position, View convertView, ViewGroup parent, ViewHolder hold) {
            TextView tvTitle = hold.find(R.id.tl_title);
            tvTitle.setText(getItem(position));
        }
    }
}
