package com.jaime.sidescroller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Jaime on 4/9/2018.
 */

public class Obstacle implements GameObject{

    float m_xPos;
    float m_yPos;
    public float width, height;
    public String tag;
    Bitmap bitmap;
    public Obstacle(Bitmap bitmap,int xPos,int yPos,String tag){
        super();
        this.bitmap=bitmap;
        this.tag = tag;
        m_xPos=xPos;
        m_yPos=yPos;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }
    public void update(){
        //this doesnt do anything could have ben my issue
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,m_xPos,m_yPos,null);

    }
}
