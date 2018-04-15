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
    int m_direction =0;
    public Obstacle(Bitmap bitmap,int xPos,int yPos,String tag,int direction){
        super();
        this.bitmap=bitmap;
        this.tag = tag;
         m_direction = direction;
        m_xPos=xPos;
        m_yPos=yPos;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }
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
        if(m_direction!=0)
            m_xPos += m_direction * 3;


    }
    public void draw(Canvas canvas){
        if(m_xPos>0&&m_xPos<canvas.getWidth())
             canvas.drawBitmap(bitmap,m_xPos,m_yPos,null);

    }
}
