package com.jaime.sidescroller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jaime on 4/2/2018.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
   // Intent myIntent = new Intent(this, "Mac's Menu Scene".class);
    int height;
    int width;
    int[] data;

   // public Obstacle(Bitmap bitmap, float xPos, float yPos);
    private Bitmap grass = BitmapFactory.decodeResource(getResources(), R.drawable.greenbushlol);
    private Bitmap stone = BitmapFactory.decodeResource(getResources(), R.drawable.repeatworldtile);
    private Bitmap playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player);
    private Bitmap brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick);
    Bitmap resizedBrick = Bitmap.createScaledBitmap(
            brick, 64, 64, false);
    private Bitmap dirt_top = BitmapFactory.decodeResource(getResources(), R.drawable.dirt_grass);
    Bitmap resizeddirt_top = Bitmap.createScaledBitmap(
            dirt_top, 64, 64, false);
    private Bitmap dirt_bot = BitmapFactory.decodeResource(getResources(), R.drawable.dirt);
    Bitmap resizeddirt_bot = Bitmap.createScaledBitmap(
            dirt_bot, 64, 64, false);
    private Bitmap end_point = BitmapFactory.decodeResource(getResources(), R.drawable.end_point);
    private Bitmap enemy = BitmapFactory.decodeResource(getResources(), R.drawable.golem_still);
    Bitmap resizedenemy = Bitmap.createScaledBitmap(
            enemy, 64, 64, false);

    public ArrayList<Obstacle> obstacles= new ArrayList<>();
    Player player = new Player(playerBitmap,300,200);
    public GamePanel(Context context,int height, int width, int[] data){
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        this.height=height;
        this.width=width;
        this.data=data;

        createWorld();
        setFocusable(true);
    }
    public void createWorld(){
        for(int i=0; i<height;i++){
            for(int j=1; j<width;j++){
                createPieceofWorld(data[j+i*width],j*64,i*64);
            }
        }
    }
    public void createPieceofWorld(int ID,int xPos,int yPos){

        switch (ID) {
            case 0:
                break;
            default:
                break;
            case 1:
                obstacles.add(new Obstacle(resizedBrick,xPos,yPos,"Wall"));
                break;
            case 2:
                obstacles.add(new Obstacle(resizeddirt_bot,xPos,yPos,"Wall"));
                break;
            case 3:
                obstacles.add(new Obstacle(resizeddirt_top,xPos,yPos,"Wall"));
                break;
            case 4:
                obstacles.add(new Obstacle(end_point,xPos,yPos,"End_Point"));//Bitmap, left, top, GameTag
                break;
            case 5:
                obstacles.add(new Obstacle(resizedenemy,xPos,yPos,"Enemy"));
                break;

        }
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while (retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){e.printStackTrace();}
            retry = false;
        }
    }
    int counter=0;
    int faceRight;
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX()<200&&event.getX()>100&&event.getY()>1000){
                    player.MovementRight = true;
                    faceRight=1;
                }if (event.getX()<100&&event.getX()>0&&event.getY()>1000) {
                    player.MovementLeft = true;
                faceRight=-1;
                }if (event.getX()<200&&event.getX()>0&&event.getY()<1000){//Shooting
                    obstacles.add(new Obstacle(resizedenemy,(int)player.m_xPos,(int)player.m_yPos,"Bullet",faceRight));
                }if (event.getX()>200&&player.onGround) {
                  player.Jump = true;

            }
                break;

            case MotionEvent.ACTION_MOVE:
                // touch move code
                break;

            case MotionEvent.ACTION_UP:
                player.MovementRight=false;
                player.MovementLeft = false;
                break;
        }
        return true;
    }
    private void Gameover(){
        //this should happen if you die
        //  CurrentActivity.this.startActivity(myIntent);
        System.out.println("YOU DEAD MOTHERFUCKER");
    }
    private void GameWon(){
        //  what happens when you win
        //  CurrentActivity.this.startActivity(myIntent);
        System.out.println("Heh, you won. so what");
    }
    public void update(){
        player.update();
        boolean touched=false ;
        boolean touched2=false ;
        boolean hitRight=false;
        boolean hitLeft=false;
        for( Obstacle obstacle : obstacles) {//object block obstalce TODO stick this inside a collision method and have 8 collision points corners and mids
            obstacle.update();
            if ((player.m_xPos+11 < obstacle.m_xPos + obstacle.width && player.m_xPos + player.m_Bitmap.getWidth()-11 > obstacle.m_xPos) &&
                 (player.m_yPos+ player.m_Bitmap.getHeight()-10 < obstacle.m_yPos + obstacle.height && player.m_yPos + player.m_Bitmap.getHeight() > obstacle.m_yPos)) {//Player bottom
                if(obstacle.tag =="Wall") {
                    touched2 = true;
                    player.m_yPos = obstacle.m_yPos - player.m_Bitmap.getHeight() + 1;
                    player.onGround = true;
                }if(obstacle.tag =="Enemy") {
                     Gameover();


                }if(obstacle.tag =="End_Point") {
                    GameWon();

                }
            }
            if ((player.m_xPos+11 < obstacle.m_xPos + obstacle.width && player.m_xPos + player.m_Bitmap.getWidth()-11 > obstacle.m_xPos) &&
                    (player.m_yPos < obstacle.m_yPos + obstacle.height && player.m_yPos + 10 > obstacle.m_yPos)){//Player top
                if(obstacle.tag =="Wall") {
                    player.hitHead = true;
                    touched = true;
                }

            }
            if ((player.m_xPos+ player.m_Bitmap.getWidth()-20 < obstacle.m_xPos + obstacle.width && player.m_xPos + player.m_Bitmap.getWidth() > obstacle.m_xPos) &&
                    (player.m_yPos < obstacle.m_yPos + obstacle.height && player.m_yPos + player.m_Bitmap.getHeight()-2 > obstacle.m_yPos)){//Player right
                if(obstacle.tag =="Wall") {
                    touched = true;
                    if (player.MovementRight) {
                        player.ICANTMOVEWTFbutIwasgoingrightjustfyi = true;

                    }
                }
            }
            else if ((player.m_xPos < obstacle.m_xPos + obstacle.width && player.m_xPos +10 > obstacle.m_xPos) &&
                    (player.m_yPos < obstacle.m_yPos + obstacle.height && player.m_yPos + player.m_Bitmap.getHeight()-2 > obstacle.m_yPos)){//Player left
                if(obstacle.tag =="Wall") {
                    touched = true;
                    if (player.MovementLeft) {
                        player.ICANTMOVEWTFbutIwasgoingleftjustfyi = true;
                    }
                }
            }
            if(player.hitRight&&!player.ICANTMOVEWTFbutIwasgoingrightjustfyi)
                hitRight=true;
                //obstacle.m_xPos-=player.m_XSpeed;
            if(player.hitLeft&&!player.ICANTMOVEWTFbutIwasgoingleftjustfyi)
                hitLeft=true;
                //obstacle.m_xPos+=player.m_XSpeed;
        }
        for( Obstacle obstacle : obstacles) {
            if (hitRight)
                obstacle.m_xPos-=player.m_XSpeed;
            if (hitLeft)
                obstacle.m_xPos+=player.m_XSpeed;
        }
        if(!touched2) {
            player.onGround=false;
        }
             if(!touched) {
                 player.hitHead = false;
               //  player.onGround=false;
                 player.ICANTMOVEWTFbutIwasgoingleftjustfyi = false;
                 player.ICANTMOVEWTFbutIwasgoingrightjustfyi = false;
             }
        }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
      //  System.out.println( player.ICANTMOVEWTFbutIwasgoingrightjustfyi);
        for( Obstacle obstacle : obstacles) {
            obstacle.draw(canvas);
        }
        player.draw(canvas);
    }
}

