package com.sample.labelview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.ymex.adapter.pure.ListViewAdapter;
import cn.ymex.popup.controller.AlertController;
import cn.ymex.popup.dialog.PopupDialog;

import cn.ymex.view.label.ImageSpannable;
import cn.ymex.view.label.SpanCell;
import cn.ymex.view.label.TextLabel;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextLabel textLabel;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textLabel = (TextLabel) findViewById(R.id.tv_label);

        listView = (ListView) findViewById(R.id.lv_listview);

        TextAdapter adapter = new TextAdapter(this, textLabel);
        adapter.setData(getThemeData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

    }


    private List<Item> getThemeData() {
        return new ArrayList<Item>() {{
            add(new Item("setText()", "设置内容头尾片段是不会改变的"));
            add(new Item("设置SpanCell点击事件"));
            add(new Item("多SpanCell片段组合,与片段独立的点击事件"));
            add(new Item("图文混排"));
        }};

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public static class TextAdapter extends ListViewAdapter<Item, ListViewAdapter.ViewHolder> {
        TextLabel tb;
        Context context;

        public TextAdapter(Context context, TextLabel tb) {
            this.context = context;
            this.tb = tb;
        }

        @Override
        public ListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
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
                case 0:
                    final int num = new Random().nextInt(100);
                    SpanCell sc21 = SpanCell.build()
                            .text(" 设置内容 ")
                            .textColor(convertView.getResources().getColor(R.color.color_4))
                            .linkColor(convertView.getResources().getColor(R.color.color_4));
                    sc21.setClickableSpan(new SpanCell.OnClickListener() {
                        @Override
                        public void onClick(View view, SpanCell spanCell) {
                            tb.setText("内容文字" + num);
                        }
                    });

                    SpanCell sc31 = SpanCell.build()
                            .text("设置头片段 ")
                            .textColor(convertView.getResources().getColor(R.color.color_5))
                            .linkColor(convertView.getResources().getColor(R.color.color_5));
                    sc31.setClickableSpan(new SpanCell.OnClickListener() {
                        @Override
                        public void onClick(View view, SpanCell spanCell) {
                            tb.getStartSpanCell().text("START" + num).textColor(convertView.getContext().getResources().getColor(R.color.blue_light));
                            tb.setText(tb.getText());
                        }
                    });
                    ImageSpannable sc41image = new ImageSpannable(convertView.getContext(), R.mipmap.ic_launcher);
                    sc41image.setSize(48, 48);
                    SpanCell sc41 = SpanCell.build()
                            .text(" 设置尾片段 ")
                            .imageSpan(sc41image)
                            .imageSpanInLast(true)
                            .textColor(convertView.getResources().getColor(R.color.color_6))
                            .linkColor(convertView.getResources().getColor(R.color.color_6));
                    sc41.setClickableSpan(new SpanCell.OnClickListener() {
                        @Override
                        public void onClick(View view, SpanCell spanCell) {
                            ImageSpannable spannable = new ImageSpannable(convertView.getContext(), R.mipmap.amalia);
                            spannable.setSize(80, 40);
                            tb.getEndSpanCell().text("END" + num).imageSpan(spannable).imageSpanInLast(false);

                            tb.setText(tb.getText());
                        }
                    });

                    textLabel.setText(sc21, sc31, sc41);
                    break;


                case 1:
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

                case 2:
                    SpanCell sc2 = SpanCell.build()
                            .text("文字片段-1 ")
                            .textColor(convertView.getResources().getColor(R.color.color_1))
                            .linkColor(convertView.getResources().getColor(R.color.color_1));
                    sc2.setClickableSpan(new SpanCell.OnClickListener() {
                        @Override
                        public void onClick(View view, SpanCell spanCell) {
                            Toast.makeText(convertView.getContext(), spanCell.getText(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(convertView.getContext(), spanCell.getText(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    textLabel.setText(sc2, sc4);
                    break;
                case 3:
                    Context context = convertView.getContext();
                    ImageSpannable forgimg = new ImageSpannable(context, R.mipmap.frog);
                    SpanCell span1 = SpanCell.build().text("一只小青蛙").imageSpanInLast(true).imageSpan(forgimg);
                    ImageSpannable deerimg = new ImageSpannable(context, R.mipmap.deer);
                    SpanCell span2 = SpanCell.build().text(",发现了一只受伤的小鹿").imageSpan(deerimg).imageSpanInLast(true);
                    ImageSpannable hippoimg = new ImageSpannable(context, R.mipmap.hippo, ImageSpannable.ALIGN_FONTCENTER);
                    hippoimg.setSize(64, 64);
                    SpanCell span3 = SpanCell.build().text("于是它去寻求小牛").imageSpanInLast(true).imageSpan(hippoimg);

                    ImageSpannable owlimg = new ImageSpannable(context, R.mipmap.owl, ImageSpannable.ALIGN_FONTCENTER);
                    owlimg.setSize(160, 160);
                    SpanCell span4 = SpanCell.build().imageSpanInLast(true).
                            text("的帮助。小牛说，不帮不帮就不帮。。于是小青蛙又去向其他 动物寻求帮助。于是它找到了猫头鹰").imageSpan(owlimg);
                    SpanCell span5 = SpanCell.build().text(",于是他们一起愉快的喝可乐 ！呵呵");
                    textLabel.setText(span1, span2, span3, span4, span5);
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
