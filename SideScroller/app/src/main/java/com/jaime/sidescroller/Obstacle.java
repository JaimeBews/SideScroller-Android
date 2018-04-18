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
    int i = 0;
    int j = 0;
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
        if(this.tag =="Enemy")
        {
            i++;
            canvas.drawBitmap(bitmap, new Rect((i%20)*64,0,(i%20)*64+64,64), new Rect((int)m_xPos,(int)m_yPos,(int)m_xPos+64,(int)m_yPos+64), null);
        }
        else if(this.tag =="Bullet")
        {
            j++;
            canvas.drawBitmap(bitmap, new Rect((j%8)*16,0,(j%8)*16+16,16), new Rect((int)m_xPos,(int)m_yPos,(int)m_xPos+16,(int)m_yPos+16), null);
        }
        else if(m_xPos>0&&m_xPos<canvas.getWidth()||tag=="Background"){
            canvas.drawBitmap(bitmap,m_xPos,m_yPos,null);
        }


    }
}
