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
                int somemath = i*j;
                createPieceofWorld(data[j+i*width],j*40,i*40);
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
        return true;
    }
    public void update(){
      //  for( Obstacle obstacle : obstacles) {
         //   obstacle.update();
         //   System.out.println(counter++);
      //  }
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        for( Obstacle obstacle : obstacles) {
            obstacle.draw(canvas);
        }
    }
}

