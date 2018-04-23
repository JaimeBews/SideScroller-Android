package com.jaime.sidescroller;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Arrays;

//MacDonald Maunder 101060770, James Bews 101085383, Dennis Nguyen 101068295
//side scroller game

public class MainActivity extends Activity {
String stringData;
int height;
int width;
int[] data;
int levelIDSafety =R.raw.level1;

    MediaPlayer player;
    //http://soundimage.org/wp-content/uploads/2016/07/Fantasy_Game_Background_Looping.mp3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        int levelID = getIntent().getIntExtra("LEVEL_TO_LOAD",levelIDSafety);
        ReadJson(levelID);
        player = MediaPlayer.create(MainActivity.this, R.raw.fantasy_bgm);
        player.setLooping(true); // Set looping
        player.setVolume(1.0f, 1.0f);
        player.start();
        setContentView(new GamePanel(this,height,width,data));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        player.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop();

    }

    @Override
    protected void onResume() {
        super.onResume();
        player.start();
    }
//reads the tiled file
    public void ReadJson(int levelID){
        try {
            InputStream is = this.getResources().openRawResource(levelID);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            //String jsonTxt = IOUtils.toString(is, "UTF-8");
            String json = new String(buffer, "UTF-8");
            System.out.println(json);
            JSONObject obj = new JSONObject(json);

            //save information
            String JSONheight = obj.getString("height");//height of the world
             height = Integer.parseInt(JSONheight);

            String JSONWidth = obj.getString("width");//width of the world
             width = Integer.parseInt(JSONWidth);
           // getJSONObject("layers") layers is an array with all its elements being objects NOTE
            JSONArray arr = obj.getJSONArray("layers");

            for (int i = 0; i < arr.length(); i++) {
                stringData = arr.getJSONObject(i).getString("data");//store the data into a string
            }
            String[] parts = stringData.split(",");// split the string to a string array

                data = new int[parts.length];
                for (int i = 1; i < parts.length-1; i++) {//how do I get rid of [ ] on the array index gets lost WILL MATTER
                    data[i] = Integer.parseInt(parts[i]);
                }
            System.out.println(Arrays.toString(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
