package com.jaime.sidescroller;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Jaime on 4/10/2018.
 */

public class Player implements GameObject {
    Bitmap m_Bitmap;
    float m_xPos;
    float m_yPos;
    float m_XSpeed;
    float m_YSpeed;
    float m_YAccel;

    boolean MovementRight = false;
    boolean MovementLeft = false;
    boolean Jump = false;
    boolean ICANTMOVEWTFbutIwasgoingrightjustfyi;
    boolean ICANTMOVEWTFbutIwasgoingleftjustfyi ;
    boolean onGround=false;
    boolean hitHead = false;

    boolean hitRight = false;
    boolean hitLeft = false;
int i;
    public Player(Bitmap bitmap, float xPos, float yPos){
        m_Bitmap=bitmap;
        m_xPos=xPos;
        m_yPos=yPos;
        m_XSpeed = 5;
        m_YAccel=1.1f;
        m_YSpeed=1.0f;
    }
    public void draw(Canvas canvas){
        i++;
        canvas.drawBitmap(m_Bitmap, new Rect((i%6)*64,0,(i%6)*64+64,64), new Rect((int)m_xPos,(int)m_yPos,(int)m_xPos+64,(int)m_yPos+64), null);
    }
   // public void Jump(){
   //     if(onGround)
   //         m_YSpeed=-3;
   // }
    public void update(){
        if(MovementRight&&!ICANTMOVEWTFbutIwasgoingrightjustfyi){
            if(this.m_xPos>1500)
                hitRight=true;
            else {
                hitLeft=false;
                this.m_xPos += m_XSpeed;
            }
        }
        if(!MovementRight){
            hitRight=false;
        }
        if(MovementLeft&&!ICANTMOVEWTFbutIwasgoingleftjustfyi){
            if(this.m_xPos<50)
                hitLeft=true;
            else {
                hitRight=false;
                this.m_xPos -= m_XSpeed;
            }
        }
        if(!onGround){
            if(this.m_YSpeed>0)
                this.m_YSpeed=this.m_YSpeed *m_YAccel;
            else if (this.m_YSpeed<-.3f)
                this.m_YSpeed=this.m_YSpeed *1/m_YAccel;
            else
                this.m_YSpeed=0.3f;
            this.m_yPos+=m_YSpeed;
        }

        if(Jump) {
            this.m_yPos-=5;
            m_YSpeed = -20;
            Jump=false;
            onGround=false;
        }

        if(hitHead){
            m_YSpeed=.5f;
            this.m_yPos+=5;
        }
    }

}
