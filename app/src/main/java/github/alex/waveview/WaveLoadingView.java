package github.alex.waveview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hasee on 2016/5/23.
 */
public class WaveLoadingView extends View {
    private Paint wavePaint;
    private int waveColor = 0xff0099CC;
    private Path path;
    /**左右偏移 φ*/
    private int fai = 0;
    /**上下偏移*/
    private volatile float k = -50;
    /**角速度*/
    private float w = 0.5f;
    /**振幅*/
    private int a = 20;
    /**控件高*/
    private int height;
    /**控件宽*/
    private int width;
    //目标高度
    private float targetHeight;
    /** 0% 时，空白的高度*/
    private float blankHeight;
    /**绘制间隔时间*/
    private int interval = 4;
    /**是否继续绘制*/
    private volatile boolean isRun = true;
    /**是否初始化*/
    private boolean isFirst = true;
    public WaveLoadingView(Context context) {
        super(context);
        initView();
    }
    public WaveLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    Runnable mDrawAuto = new Runnable()
    {
        @Override
        public void run()
        {
            while (isRun)
            {
                fai++;
                if (fai == 360)
                {
                    fai = 0;
                }
                if (k > targetHeight)
                {
                    k--;
                }
                fai = 0;
                mDrawHandler.sendEmptyMessage(1);
                try
                {
                    Thread.sleep(interval);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    };
    Handler mDrawHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            invalidate();
            super.handleMessage(msg);
        }
    };


    private void initView()
    {
        //初始化波浪画笔
        wavePaint = new Paint();
        wavePaint.setAntiAlias(true);
        wavePaint.setColor(waveColor);
        path = new Path();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initData();
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        super.onLayout(changed, left, top, right, bottom);
    }
    private void initData()
    {
        if (isFirst)
        {
            width = getMeasuredWidth();
            height = getMeasuredHeight();
            blankHeight = (float) (height * 0.9);
            targetHeight = (float) (blankHeight * 0.2);
            k = blankHeight;
            new Thread(mDrawAuto).start();
            isFirst = false;
        }
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        setPath();
        canvas.drawPath(path, wavePaint);
    }
    private void setPath()
    {
        path.reset();
        int x = 0;
        int y = 0;
        for (int i = 0; i < width; i++)
        {
            x = i;
            y = (int) (a * Math.sin((i * w + fai) * Math.PI / 180) + k);
            if (i == 0)
            {
                path.moveTo(x, y);
            }
            path.quadTo(x, y, x + 1, y);
        }
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.close();
    }

}
