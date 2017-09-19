package com.sample.labelview;

import android.graphics.Color;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    private List<Item> getThemeData() {
        return new ArrayList<Item>() {{
            add(new Item("setText()","设置内容头尾片段是不会改变的"));
            add(new Item("setStartSpanCell(spancell)","动态设置头片段"));
            add(new Item("setEndSpanCell(spancell)","动态设置尾片段"));
            add(new Item("单独的图文片段（SpanCell）"));
            add(new Item("设置SpanCell点击事件"));
            add(new Item("多SpanCell片段组合,与片段独立的点击事件"));
        }};

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                textLabel.setText("随机数"+(new Random().nextInt()));
                break;

            case 1:
                textLabel.getStartSpanCell().text("Start SpanCell").textColor(getResources().getColor(R.color.blue_light));
                textLabel.setText(textLabel.getText());
                break;

            case 2:
                ImageSpannable spannable = new ImageSpannable(this,R.mipmap.amalia);
                spannable.setSize(80, 40);
                textLabel.getEndSpanCell().text(" SpanCell").imageSpan(spannable).imageSpanInLast(false);

                textLabel.setText(textLabel.getText());
                break;
        }
    }

    public static class TextAdapter extends ListViewAdapter<Item, ListViewAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false));
        }

        @Override
        public void onBindViewHolder(int position, final View convertView, ViewGroup parent, ViewHolder hold) {
            TextLabel textLabel = hold.find(R.id.tl_demo);
            TextView tvDetail = hold.find(R.id.tv_detail);
            Item item = getItem(position);
            textLabel.setText(item.getTitle());
            tvDetail.setText(item.getDetail());
            textLabel.getStartSpanCell().imageSpan(null).text("");
            textLabel.getEndSpanCell().imageSpan(null).text("");
            switch (position) {


                case 3:
                    ImageSpannable imageSpannable = new ImageSpannable(convertView.getContext(), R.mipmap.ic_launcher);
                    imageSpannable.setSize(48, 48);
                    textLabel.setText(SpanCell.build().text("设置右边图片").imageSpan(imageSpannable).imageSpanInLast(false));
                    break;

                case 4:
                    textLabel.getStartSpanCell().text("查看协议：");
                    textLabel.getEndSpanCell().text("点击查看.").setTextSize(36);

                    SpanCell sp = SpanCell.build()
                            .textColor(Color.parseColor("#887acc"))
                            .text("《用户协议》");

                    sp.setClickableSpan(new SpanCell.OnClickListener() {
                        @Override
                        public void onClick(View view, SpanCell spanCell) {
                            PopupDialog.create(view.getContext())
                                    .controller(AlertController
                                            .build()
                                            .title("提示")
                                            .message(spanCell.getText().toString())
                                            .positiveButton("Ok", null))
                                    .show();
                        }
                    });

                    textLabel.setText(sp);

                    break;

                case 5:
                    SpanCell sc2 = SpanCell.build()
                            .text("文字片段-1 ")
                            .textColor(convertView.getResources().getColor(R.color.color_1))
                            .linkColor(convertView.getResources().getColor(R.color.color_1));
                    sc2.setClickableSpan(new SpanCell.OnClickListener() {
                        @Override
                        public void onClick(View view, SpanCell spanCell) {
                            Toast.makeText(convertView.getContext(),spanCell.getText(),Toast.LENGTH_SHORT).show();
                        }
                    });

                    SpanCell sc3 = SpanCell.build()
                            .text(" 文字片段-2 ")
                            .textColor(convertView.getResources().getColor(R.color.color_2))
                            .linkColor(convertView.getResources().getColor(R.color.color_2));
                    sc3.setClickableSpan(new SpanCell.OnClickListener() {
                        @Override
                        public void onClick(View view, SpanCell spanCell) {
                            Toast.makeText(convertView.getContext(),spanCell.getText(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    ImageSpannable sc4image = new ImageSpannable(convertView.getContext(), R.mipmap.ic_launcher);
                    sc4image.setSize(48, 48);
                    SpanCell sc4 = SpanCell.build()
                            .text(" 图文片段-3 ")
                            .imageSpan(sc4image)
                            .imageSpanInLast(true)
                            .textColor(convertView.getResources().getColor(R.color.color_3))
                            .linkColor(convertView.getResources().getColor(R.color.color_3));
                    sc4.setClickableSpan(new SpanCell.OnClickListener() {
                        @Override
                        public void onClick(View view, SpanCell spanCell) {
                            Toast.makeText(convertView.getContext(),spanCell.getText(),Toast.LENGTH_SHORT).show();
                        }
                    });

                    textLabel.setText(sc2,sc3,sc4);
                    break;
            }

        }
    }

    private class Item {
        public Item(String detail) {
            this.title = "";
            this.detail = detail;
        }
        public Item(String title, String detail) {
            this.title = title;
            this.detail = detail;
        }

        String title;
        String detail;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}
