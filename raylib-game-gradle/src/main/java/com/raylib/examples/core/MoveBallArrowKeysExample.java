/*******************************************************************************************
 *
 *   raylib [core] example - Keyboard input
 *
 *   Example originally created with raylib 1.0, last time updated with raylib 1.0
 *
 *   Example licensed under an unmodified zlib/libpng license, which is an OSI-certified,
 *   BSD-like license that allows static linking with closed source software
 *
 *   Copyright (c) 2014-2024 Ramon Santamaria (@raysan5)
 *
 ********************************************************************************************/
/*
 * This is a Java version of the original C example for raylib, adapted from the original example using jaylib-ffm.
 */

package com.raylib.examples.core;

import com.raylib.Vector2;
import de.alniarez.IBasicExample;
import de.alniarez.Screen;

import static com.raylib.Raylib.*;
import static com.raylib.Raylib.KeyboardKey.*;

public class MoveBallArrowKeysExample implements IBasicExample {

    private final Screen _screen;

    Vector2 ballPosition;

    public MoveBallArrowKeysExample(Screen screen) {
        _screen = screen;
        _screen.title("raylib [core] example - Keyboard input").init();

        ballPosition = new Vector2((float) _screen.width() / 2, (float) _screen.height() / 2);
    }

    @Override
    public Screen getScreen() {
        return _screen;
    }

    @Override
    public void run() {
        // Update
        //----------------------------------------------------------------------------------
        if (isKeyDown(KEY_RIGHT)) ballPosition.x(ballPosition.x() + 2.0f);
        if (isKeyDown(KEY_LEFT)) ballPosition.x(ballPosition.x() - 2.0f);
        if (isKeyDown(KEY_UP)) ballPosition.y(ballPosition.y() - 2.0f);
        if (isKeyDown(KEY_DOWN)) ballPosition.y(ballPosition.y() + 2.0f);
        //----------------------------------------------------------------------------------

        // Draw
        //----------------------------------------------------------------------------------
        beginDrawing();

        clearBackground(RAYWHITE);

        drawText("move the ball with arrow keys", 10, 10, 20, DARKGRAY);

        drawCircleV(ballPosition, 50, MAROON);

        endDrawing();
        //----------------------------------------------------------------------------------


    }

}
