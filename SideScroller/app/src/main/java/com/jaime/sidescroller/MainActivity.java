package com.jaime.sidescroller;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Arrays;

public class MainActivity extends Activity {
String stringData;
int height;
int width;
int[] data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ReadJson();
        setContentView(new GamePanel(this,height,width,data));

    }


    public void ReadJson(){
        try {
            InputStream is = this.getResources().openRawResource(R.raw.level1);
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
