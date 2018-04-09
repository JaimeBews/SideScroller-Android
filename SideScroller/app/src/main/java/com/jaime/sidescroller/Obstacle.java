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
    Bitmap bitmap;
    public Obstacle(Bitmap bitmap,int xPos,int yPos){
        super();
        this.bitmap=bitmap;
        m_xPos=xPos;
        m_yPos=yPos;
    }
    public void update(){
        //this doesnt do anything could have ben my issue
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap,m_xPos,m_yPos,null);
    }
}
