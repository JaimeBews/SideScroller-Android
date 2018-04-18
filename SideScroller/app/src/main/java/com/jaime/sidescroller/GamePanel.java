package com.jaime.sidescroller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jaime on 4/2/2018.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    Intent myIntent = new Intent(getContext(), MenuActivity.class);
    int height;
    int width;
    int[] data;

   // public Obstacle(Bitmap bitmap, float xPos, float yPos);
    private Bitmap grass = BitmapFactory.decodeResource(getResources(), R.drawable.left);
    private Bitmap bullet = BitmapFactory.decodeResource(getResources(), R.drawable.ice_bolt);
    Bitmap resizedbullet = Bitmap.createScaledBitmap(
            bullet, 200, 100, false);
    private Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.background_inside);
    private Bitmap stone = BitmapFactory.decodeResource(getResources(), R.drawable.right);
    Bitmap resizedstone = Bitmap.createScaledBitmap(
            stone, 100, 100, false);
    private Bitmap playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wizardwalk);
    Bitmap resizedplayerBitmap = Bitmap.createScaledBitmap(
            playerBitmap, 64, 64, false);
    private Bitmap playerSheetBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wizardwalk_sheet);
    Bitmap resizedplayerSheetBitmap = Bitmap.createScaledBitmap(
            playerSheetBitmap, 64*6, 64, false);
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
    Player player = new Player(resizedplayerSheetBitmap,300,200);

    public GamePanel(Context context, int height, int width, int[] data ){
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
        obstacles.add(new Obstacle(background,100,0,"Background"));
        obstacles.add(new Obstacle(background,100+background.getWidth(),0,"Background"));
        obstacles.add(new Obstacle(background,100+background.getWidth()*2,0,"Background"));
        obstacles.add(new Obstacle(background,100+background.getWidth()*3,0,"Background"));
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
     //   audio.execute();
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
    int faceRight=1;
    boolean doOnce = false;
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getX()>200&&player.onGround) {
                    player.Jump = true;
                }
                if (event.getX()<200&&event.getX()>100&&event.getY()>1000){
                    player.MovementRight = true;
                    faceRight=1;
                }if (event.getX()<100&&event.getX()>0&&event.getY()>1000) {
                    player.MovementLeft = true;
                faceRight=-1;
                }if (event.getX()<200&&event.getX()>0&&event.getY()<1000){//Shooting
                    obstacles.add(new Obstacle(bullet,(int)player.m_xPos,(int)player.m_yPos,"Bullet",faceRight));
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
        //surfaceDestroyed(this.getHolder());
        if (!doOnce) {

         //   audio.cancel(true);
            getContext().startActivity(myIntent);
            System.out.println("YOU DEAD MOTHERFUCKER");
            doOnce = true;
        }

    }
    private void GameWon(){
        if (!doOnce) {
            getContext().startActivity(myIntent);
            System.out.println("Heh, you won. so what");
            doOnce = true;
        }

    }
    public Obstacle checkCollision(Obstacle obs1, String tag){// do all Obstacles want this behaviour prolly should go in their class
        for(Obstacle obstacle : obstacles) {
            if (obstacle.tag == tag) {
                if ((obs1.m_xPos < obstacle.m_xPos + obstacle.width && obs1.m_xPos + obs1.width > obstacle.m_xPos) &&
                        (obs1.m_yPos < obstacle.m_yPos + obstacle.height && obs1.m_yPos + obs1.height > obstacle.m_yPos)) {
                    return obstacle;
                }
            }
        }
        return null;
    }
    public void update(){
        player.update();
        boolean touched=false ;
        boolean touched2=false ;
        boolean hitRight=false;
        boolean hitLeft=false;
        for( Obstacle obstacle : obstacles) {//object block obstalce TODO stick this inside a collision method and have 8 collision points corners and mids
            obstacle.update();
            if(obstacle.tag == "Bullet") {
                Obstacle tempObs =checkCollision(obstacle, "Enemy");
                if (tempObs != null) {
                    tempObs.m_yPos+=1000;
                    obstacle.m_yPos+=1000;
                }
                Obstacle tempObs2 =checkCollision(obstacle, "Wall");
                if (tempObs2 != null) {
                    obstacle.m_yPos+=1000;
                }

            }
            if ((player.m_xPos+11 < obstacle.m_xPos + obstacle.width && player.m_xPos + 64-11 > obstacle.m_xPos) &&
                 (player.m_yPos+ 64-10 < obstacle.m_yPos + obstacle.height && player.m_yPos + 64 > obstacle.m_yPos)) {//Player bottom
                if(obstacle.tag =="Wall") {
                    touched2 = true;
                    player.m_yPos = obstacle.m_yPos -64 + 1;
                    player.onGround = true;
                }if(obstacle.tag =="Enemy") {
                     Gameover();


                }if(obstacle.tag =="End_Point") {
                    GameWon();

                }
            }
            if ((player.m_xPos+11 < obstacle.m_xPos + obstacle.width && player.m_xPos +64-11 > obstacle.m_xPos) &&
                    (player.m_yPos < obstacle.m_yPos + obstacle.height && player.m_yPos + 10 > obstacle.m_yPos)){//Player top
                if(obstacle.tag =="Wall") {
                    player.hitHead = true;
                    touched = true;
                }

            }
            if ((player.m_xPos+ 64-20 < obstacle.m_xPos + obstacle.width && player.m_xPos + 64 > obstacle.m_xPos) &&
                    (player.m_yPos < obstacle.m_yPos + obstacle.height && player.m_yPos + 64-2 > obstacle.m_yPos)){//Player right
                if(obstacle.tag =="Wall") {
                    touched = true;
                    if (player.MovementRight) {
                        player.ICANTMOVEWTFbutIwasgoingrightjustfyi = true;

                    }
                }
            }
            else if ((player.m_xPos < obstacle.m_xPos + obstacle.width && player.m_xPos +10 > obstacle.m_xPos) &&
                    (player.m_yPos < obstacle.m_yPos + obstacle.height && player.m_yPos + 64-2 > obstacle.m_yPos)){//Player left
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
        canvas.drawBitmap(grass,0,canvas.getHeight()-100,null);
        canvas.drawBitmap(resizedstone,100,canvas.getHeight()-100,null);
        canvas.drawBitmap(resizedbullet,0,canvas.getHeight()-200,null);

        player.draw(canvas);

    }
}

