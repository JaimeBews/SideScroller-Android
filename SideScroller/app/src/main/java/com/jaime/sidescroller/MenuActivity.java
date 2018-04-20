package com.jaime.sidescroller;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {
int levelID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button playButtonVariable = (Button) findViewById(R.id.b_level1);
        PlayButtonListener playListenerVariable = new PlayButtonListener();
        playButtonVariable.setOnClickListener(playListenerVariable);

        Button ButtonVariable = (Button) findViewById(R.id.b_level2);
        ButtonListener ListenerVariable = new ButtonListener();
        ButtonVariable.setOnClickListener(ListenerVariable);
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startPlay2();
        }
    }

    private void startPlay1() {
        Intent intentInstance = new Intent(this, MainActivity.class);
        levelID =R.raw.level1;
        intentInstance.putExtra("LEVEL_TO_LOAD", levelID);
        startActivity(intentInstance);

    }

    private void startPlay2() {
        Intent intentInstance = new Intent(this, MainActivity.class);
        levelID =R.raw.level3;
        intentInstance.putExtra("LEVEL_TO_LOAD", levelID);
        startActivity(intentInstance);
    }

    private class PlayButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startPlay1();
        }
    }
}
