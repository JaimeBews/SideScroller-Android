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
    float m_XSpeed;
    float m_YSpeed;
    float m_YAccel;
    boolean MovementRight = false;
    boolean MovementLeft = false;
    boolean ICANTMOVEWTFbutIwasgoingrightjustfyi;
    boolean ICANTMOVEWTFbutIwasgoingleftjustfyi ;
    boolean onGround=false;
    boolean hitHead = false;
    boolean Jump = false;
    public Player(Bitmap bitmap, float xPos, float yPos){
        m_Bitmap=bitmap;
        m_xPos=xPos;
        m_yPos=yPos;
        m_XSpeed = 5;
        m_YAccel=1.1f;
        m_YSpeed=1.0f;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(m_Bitmap,m_xPos,m_yPos,null);//TODO animate
    }
   // public void Jump(){
   //     if(onGround)
   //         m_YSpeed=-3;
   // }
    public void update(){
        if(MovementRight&&!ICANTMOVEWTFbutIwasgoingrightjustfyi){
            this.m_xPos+=m_XSpeed;
        }
        if(MovementLeft&&!ICANTMOVEWTFbutIwasgoingleftjustfyi){
            this.m_xPos-=m_XSpeed;
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
            m_YSpeed = -8;
            Jump=false;
            onGround=false;
        }

        if(hitHead){
            m_YSpeed=.2f;
        }
    }

}
