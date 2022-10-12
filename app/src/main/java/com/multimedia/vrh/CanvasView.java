package com.multimedia.vrh;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

public class CanvasView extends View {
    public int x = 0;
    public int y = 0;
     Paint paint;
     Rect rect;
     Paint paintLine;
    BitmapDrawable ball;
    Bitmap background;
    Rect canvasDimensions;
    Rect bitmapDimensions;

    public CanvasView(Context context) {
        super(context);
        init(context);


    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        rect = new Rect();
        paintLine = new Paint();
        rect = new Rect();
        paint = new Paint();
        Bitmap bitmapBall = BitmapFactory.decodeResource(context.getResources(),R.drawable.football3);
        background = BitmapFactory.decodeResource(context.getResources(),R.drawable.background);


        ball = new BitmapDrawable(context.getResources(),bitmapBall);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            canvasDimensions = new Rect(0,100,this.getWidth(),this.getHeight());
        } else {
            canvasDimensions = new Rect(0,100,this.getWidth(),this.getHeight()/8);
        }

    }

    public CanvasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Drawing commands go here
        canvas.drawColor(Color.WHITE);
        rect.right = 50 +x;
        rect.top = this.getHeight() -50 -ball.getBitmap().getHeight()/2 - y;
        rect.left = rect.right + 50 ;
        rect.bottom = rect.top + 50 ;

        canvasDimensions.right = this.getWidth();
        canvasDimensions.bottom = this.getHeight();
        paint.setColor(Color.MAGENTA);
        paintLine.setColor(Color.BLACK);
        canvas.drawLine(50, this.getHeight() - 1, this.getWidth()-50 , this.getHeight() - 1, paintLine);
        canvas.drawBitmap(background,null,canvasDimensions,null);
        canvas.drawBitmap(ball.getBitmap(),rect.right ,rect.top,null);

    }
}