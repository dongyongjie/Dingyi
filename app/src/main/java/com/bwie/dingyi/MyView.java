package com.bwie.dingyi;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by lenovo on 2017/2/21.
 */

public class MyView extends View {
    private Region rectRegion,circleregion1,circleregion2;
    private Path rectpath,circlepath1,circlepath2;
    private Paint paint,paint1,paint2,textPaint;
    private int w2,h2;
    private int green=Color.GREEN,yellow=Color.YELLOW,white=Color.WHITE;
    private float bigRadius=250,smallRadius=350,rect=300;
    private float size=20;
    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化方法
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        //自定义属性
        if(attrs!=null){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyView);
            bigRadius=ta.getFloat(R.styleable.MyView_bigRadius,bigRadius);
            smallRadius=ta.getFloat(R.styleable.MyView_smallRadius,smallRadius);
            rect=ta.getFloat(R.styleable.MyView_rect,rect);
            yellow=ta.getColor(R.styleable.MyView_bigColor,yellow);
            size=ta.getFloat(R.styleable.MyView_textSize,size);
//            paint1.setColor(yellow);
            ta.recycle();
        }
        //实例化画笔,路径,region,颜色;
        rectRegion=new Region();
        rectpath=new Path();
        circleregion1=new Region();
        circleregion2=new Region();
        circlepath1=new Path();
        circlepath2=new Path();
        paint=new Paint();
        paint1=new Paint();
        paint2=new Paint();
        textPaint=new Paint();
        paint.setAntiAlias(true);
        paint1.setAntiAlias(true);
        paint2.setAntiAlias(true);
        textPaint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint1.setColor(yellow);
        paint2.setColor(Color.WHITE);
        textPaint.setColor(Color.BLACK);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //发送路径
        Path path=rectpath;
        Path path1=circlepath1;
        Path path2=circlepath2;
        canvas.drawPath(path,paint);
//        paint.setColor(Color.BLACK);
        canvas.drawPath(path1,paint1);
        canvas.drawPath(path2,paint2);
        //设置文本大小
        textPaint.setTextSize(size);

        //将文字放到正中间
        Rect re=new Rect();
        textPaint.getTextBounds("圆环",0,"圆环".length(),re);
        canvas.drawText("圆环",getWidth()/2-re.width()/2,getHeight()/2+re.height()/2,textPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //在屏幕中间添加一个矩形
        rectpath.addRect(w/2-rect,h/2-rect,w/2+rect,h/2+rect, Path.Direction.CW);
        //在屏幕中间添加一个圆形
//        paint.setColor(Color.YELLOW);
        circlepath1.addCircle(w/2,h/2,w/2-bigRadius, Path.Direction.CW);
        //在屏幕中间添加一个圆形
        circlepath2.addCircle(w/2,h/2,w/2-smallRadius, Path.Direction.CW);
        // ▼将剪裁边界设置为视图大小
        Region region=new Region(-w,-h,w,h);
        //将路径分别添加到region中
        rectRegion.setPath(rectpath,region);
        circleregion1.setPath(circlepath1,region);
        circleregion2.setPath(circlepath2,region);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //判断点击事件
        switch (event.getAction()){
            //当按下时
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                //按照从小到大的顺序判断点击的位置在哪里,分别吐司
                if(circleregion2.contains(x,y)){
                    Toast.makeText(getContext(), "在小圆内", Toast.LENGTH_SHORT).show();
                }else if(circleregion1.contains(x,y)){
                    Toast.makeText(getContext(), "点击圆环", Toast.LENGTH_SHORT).show();
                }else if(rectRegion.contains(x,y)){
                    Toast.makeText(getContext(), "点击矩形", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "点击空白", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }
}
