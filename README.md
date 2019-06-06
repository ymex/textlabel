[ ![Download](https://api.bintray.com/packages/ymex/maven/textlabel/images/download.svg) ](https://bintray.com/ymex/maven/textlabel/_latestVersion)

# TextLabel
用于处理要需要格式化TextView 及简单的图文混排的情况。可在TextLabel中设置文字的颜色，尺寸，及可点击事件。

## TextLabel

TextLabel可独立设置一个固定头片段和一个尾片段。每个图文片段（SpanCell）都可以独门设置颜色，图片，大小，点击事件。
TextLabel依赖SpanCell实现图文混排 。一个SpanCell由文字与一个图片组成，基于SpanCell可实现混排效果。 

## 说明
项目在分支androidx 和 support ，master 分支没有代码。

## gradle依赖

```
//support版本
compile 'cn.ymex:textlabel:1.1.2'
//androidx版本
compile 'cn.ymex:textlabel:1.1.3'
```

## 使用场景
![使用场景](https://github.com/ymex/textlabel/blob/master/art/default.png)
### 标签化
![标签化](https://github.com/ymex/textlabel/blob/master/art/textlabel.png)
<br>开发中常遇到上面这种场景，通常我们用使用以下访求去格式化。
```
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="4dip"
    android:paddingLeft="8dip"
    android:text="100"/>

textView.setText(String.format("金额：%1$s元"),money);

```
使用`TextLabel`后我们就可以这样使用
```
<cn.ymex.view.label.TextLabel
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="4dip"
    android:paddingLeft="8dip"
    android:text="100"
    app:endText="元"
    app:startDrawable="@mipmap/money1"
    app:startDrawableSize="24dp"
    app:startText=" 金额：" />
    
textLabel.setText(money);
```
或者

```
<cn.ymex.view.label.TextLabel
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="4dip"
    android:paddingLeft="8dip"
    android:text="100"
    app:format="金额：%1$s元" />
    
textLabel.setTextFormat(money);
```

### 部分文字可点击及换色
开发中经常遇到使TextView的部分文字设置不同的颜色，并可点击 。如下图<br>
![可点击](https://github.com/ymex/textlabel/blob/master/art/click.png)
```
textLabel.getStartSpanCell().text("查看协议：");
textLabel.getEndSpanCell().text("点击查看.");

SpanCell sp = SpanCell.build()
        .textColor(Color.parseColor("#887acc"))
        .text("《用户协议》");

sp.setClickableSpan(new SpanCell.OnClickListener() {
    @Override
    public void onClick(View view, SpanCell spanCell) {
        //....
    }
});

textLabel.setText(sp);
```


### 文字混排 
![文字混排](https://github.com/ymex/textlabel/blob/master/art/text_pic.png)


```
 Context context = convertView.getContext();
ImageSpannable forgimg = new ImageSpannable(context, R.mipmap.frog);
SpanCell span1 = SpanCell.build().text("一只小青蛙").imageSpanInLast(true).imageSpan(forgimg);
ImageSpannable deerimg = new ImageSpannable(context, R.mipmap.deer);
SpanCell span2 = SpanCell.build().text(",发现了一只受伤的小鹿").imageSpan(deerimg).imageSpanInLast(true);
ImageSpannable hippoimg = new ImageSpannable(context, R.mipmap.hippo, ImageSpannable.ALIGN_FONTCENTER);
hippoimg.setSize(64,64);
SpanCell span3 = SpanCell.build().text("于是它去寻求小牛").imageSpanInLast(true).imageSpan(hippoimg);

ImageSpannable owlimg = new ImageSpannable(context, R.mipmap.owl, ImageSpannable.ALIGN_FONTCENTER);
owlimg.setSize(160, 160);
SpanCell span4 = SpanCell.build().imageSpanInLast(true).
        text("的帮助。小牛说，不帮不帮就不帮。。于是小青蛙又去向其他 动物寻求帮助。于是它找到了猫头鹰").imageSpan(owlimg);
SpanCell span5 = SpanCell.build().text(",于是他们一起愉快的喝可乐 ！呵呵");
textLabel.setText(span1,span2,span3,span4,span5);
```

License
-------

    Copyright 2017 ymex.cn

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.