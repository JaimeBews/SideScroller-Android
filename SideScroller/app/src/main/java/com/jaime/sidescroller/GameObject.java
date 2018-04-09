package com.jaime.sidescroller;

import android.graphics.Canvas;

/**
 * Created by Jaime on 4/2/2018.
 */

public interface GameObject {
    void draw(Canvas canvas);
    void update();
}
