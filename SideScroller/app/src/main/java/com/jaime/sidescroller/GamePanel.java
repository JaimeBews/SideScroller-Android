package com.jaime.sidescroller;

import android.content.Context;
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

    int height;
    int width;
    int[] data;

   // public Obstacle(Bitmap bitmap, float xPos, float yPos);
    private Bitmap grass = BitmapFactory.decodeResource(getResources(), R.drawable.greenbushlol);
    private Bitmap stone = BitmapFactory.decodeResource(getResources(), R.drawable.repeatworldtile);
    private Bitmap playerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player);
    Player player = new Player(playerBitmap,300,200);
    public ArrayList<Obstacle> obstacles= new ArrayList<>();
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
                createPieceofWorld(data[j+i*width],j*52,i*52);
            }
        }
    }
    public void createPieceofWorld(int ID,int xPos,int yPos){

        switch (ID) {
            case 0:
                break;
            default:
                break;
            case 4:
                obstacles.add(new Obstacle(grass,xPos,yPos));
                break;
            case 3:
                obstacles.add(new Obstacle(stone,xPos,yPos));
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
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX()<200&&event.getX()>100){
                    player.MovementRight = true;
                }
                if (event.getX()<100&&event.getX()>0){
                    player.MovementLeft = true;
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
    public void update(){
        for( Obstacle obstacle : obstacles) {
            if (player.m_xPos < obstacle.m_xPos + obstacle.width && player.m_xPos + player.m_Bitmap.getWidth() > obstacle.m_xPos)
                if (player.m_yPos < obstacle.m_yPos + obstacle.height && player.m_yPos+ player.m_Bitmap.getHeight() > obstacle.m_yPos){
                    System.out.println(obstacle);
                }
        }
         player.update();
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        for( Obstacle obstacle : obstacles) {
            obstacle.draw(canvas);
        }
        player.draw(canvas);
    }
}

