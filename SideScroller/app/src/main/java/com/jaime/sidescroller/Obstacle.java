package com.jaime.sidescroller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Jaime on 4/9/2018.
 */

//sets up obstacles
public class Obstacle implements GameObject{

    float m_xPos;
    float m_yPos;
    public float width, height;
    public String tag;
    Bitmap bitmap;
    int m_direction =0;
    int i = 0;
    int j = 0;
    public Obstacle(Bitmap bitmap,int xPos,int yPos,String tag,int direction){
        super();
        this.bitmap=bitmap;
        this.tag = tag;
         m_direction = direction;
        m_xPos=xPos;
        m_yPos=yPos;
        width = 64;
        height = bitmap.getHeight();
    }
    //creates bitmaps
    public Obstacle(Bitmap bitmap,int xPos,int yPos,String tag){
        super();
        this.bitmap=bitmap;
        this.tag = tag;
        m_xPos=xPos;
        m_yPos=yPos;
        width = 64;
        height = bitmap.getHeight();
    }
    //update obstacles
    public void update(){
        if(m_direction!=0)
            m_xPos += m_direction * 15;


    }
    //handle enemy animation and background
    public void draw(Canvas canvas){
        if(this.tag =="Enemy")
        {
            i++;
            canvas.drawBitmap(bitmap, new Rect((i%20)*64,0,(i%20)*64+64,64), new Rect((int)m_xPos,(int)m_yPos,(int)m_xPos+64,(int)m_yPos+64), null);
        }
        else if(m_xPos>0&&m_xPos<canvas.getWidth()||tag=="Background"){
            canvas.drawBitmap(bitmap,m_xPos,m_yPos,null);
        }


    }
}
