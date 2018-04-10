package com.jaime.sidescroller;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Jaime on 4/10/2018.
 */

public class Player implements GameObject {
    Bitmap m_Bitmap;
    float m_xPos;
    float m_yPos;
    public Player(Bitmap bitmap, float xPos, float yPos){
        m_Bitmap=bitmap;
        m_xPos=xPos;
        m_yPos=yPos;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(m_Bitmap,m_xPos,m_yPos,null);//TODO animate
    }
    public void update(){

    }

}
