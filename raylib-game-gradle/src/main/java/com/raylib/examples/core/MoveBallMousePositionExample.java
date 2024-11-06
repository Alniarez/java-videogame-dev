/*******************************************************************************************
 *
 *   raylib [core] example - Mouse input
 *
 *   Example originally created with raylib 1.0, last time updated with raylib 4.0
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

import com.raylib.Color;
import com.raylib.Vector2;
import de.alniarez.IBasicExample;
import de.alniarez.Screen;

import static com.raylib.Raylib.*;
import static com.raylib.Raylib.MouseButton.*;

public class MoveBallMousePositionExample implements IBasicExample {

    private final Screen _screen;
    Vector2 ballPosition;
    Color ballColor;

    public MoveBallMousePositionExample(Screen screen) {
        _screen = screen;
        _screen.title("raylib [core] example - Mouse input").init();

        ballPosition = new Vector2(-100.0f, -100.0f );
        ballColor = DARKBLUE;
    }

    @Override
    public Screen getScreen() {
        return _screen;
    }

    @Override
    public void run() {
        // Update
        //----------------------------------------------------------------------------------
        ballPosition = getMousePosition();

        if (isMouseButtonPressed(MOUSE_BUTTON_LEFT)) ballColor = MAROON;
        else if (isMouseButtonPressed(MOUSE_BUTTON_MIDDLE)) ballColor = LIME;
        else if (isMouseButtonPressed(MOUSE_BUTTON_RIGHT)) ballColor = DARKBLUE;
        else if (isMouseButtonPressed(MOUSE_BUTTON_SIDE)) ballColor = PURPLE;
        else if (isMouseButtonPressed(MOUSE_BUTTON_EXTRA)) ballColor = YELLOW;
        else if (isMouseButtonPressed(MOUSE_BUTTON_FORWARD)) ballColor = ORANGE;
        else if (isMouseButtonPressed(MOUSE_BUTTON_BACK)) ballColor = BEIGE;
        //----------------------------------------------------------------------------------

        // Draw
        //----------------------------------------------------------------------------------
        beginDrawing();

        clearBackground(RAYWHITE);

        drawCircleV(ballPosition, 40, ballColor);

        drawText("move ball with mouse and click mouse button to change color", 10, 10, 20, DARKGRAY);

        endDrawing();
        //----------------------------------------------------------------------------------
    }
}
