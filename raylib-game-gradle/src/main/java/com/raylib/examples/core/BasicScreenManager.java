/*******************************************************************************************
 *
 *   raylib [core] examples - basic screen manager
 *
 *   NOTE: This example illustrates a very simple screen manager based on a states machines
 *
 *   Example originally created with raylib 4.0, last time updated with raylib 4.0
 *
 *   Example licensed under an unmodified zlib/libpng license, which is an OSI-certified,
 *   BSD-like license that allows static linking with closed source software
 *
 *   Copyright (c) 2021-2024 Ramon Santamaria (@raysan5)
 *
 ********************************************************************************************/
/*
 * This is a Java version of the original C example for raylib, adapted from the original example using jaylib-ffm.
 */

package com.raylib.examples.core;

import de.alniarez.IBasicExample;
import de.alniarez.core.Screen;

import static com.raylib.Raylib.*;
import static com.raylib.Raylib.Gesture.GESTURE_TAP;
import static com.raylib.Raylib.KeyboardKey.KEY_ENTER;

enum GameScreen {
    Logo,
    Title,
    Gameplay,
    Ending
}

public class BasicScreenManager implements IBasicExample {

    private final Screen _screen;

    GameScreen currentScreen = GameScreen.Logo;

    int framesCounter = 0;

    public BasicScreenManager(Screen screen) {
        _screen = screen;
        _screen.title("raylib [core] examples - basic screen manager").init();
    }

    @Override
    public Screen getScreen() {
        return _screen;
    }

    @Override
    public void run() {
        Screen screen = getScreen();

        // Update
        //----------------------------------------------------------------------------------
        switch (currentScreen) {
            case Logo: {
                framesCounter = framesCounter + 1;    // Count frames

                // Wait for 2 seconds (120 frames) before jumping to TITLE screen
                if (framesCounter > 120)
                {
                    currentScreen = GameScreen.Title;
                }
            } break;
            case Title: {
                // TODO: Update TITLE screen variables here!

                // Press enter to change to GAMEPLAY screen
                if (isKeyPressed(KEY_ENTER) || isGestureDetected(GESTURE_TAP))
                {
                    currentScreen = GameScreen.Gameplay;
                }
            } break;
            case Gameplay:
            {
                // TODO: Update GAMEPLAY screen variables here!

                // Press enter to change to ENDING screen
                if (isKeyPressed(KEY_ENTER) || isGestureDetected(GESTURE_TAP))
                {
                    currentScreen = GameScreen.Ending;
                }
            } break;
            case Ending:
            {
                // TODO: Update ENDING screen variables here!

                // Press enter to return to TITLE screen
                if (isKeyPressed(KEY_ENTER) || isGestureDetected(GESTURE_TAP))
                {
                    currentScreen = GameScreen.Title;
                }
            } break;
            default: break;
        }
        //----------------------------------------------------------------------------------

        // Draw
        //----------------------------------------------------------------------------------
        beginDrawing();

        clearBackground(RAYWHITE);

        switch(currentScreen)
        {
            case Logo:
            {
                // TODO: Draw LOGO screen here!
                drawText("LOGO SCREEN", 20, 20, 40, LIGHTGRAY);
                drawText("WAIT for 2 SECONDS...", 290, 220, 20, GRAY);

            } break;
            case Title:
            {
                // TODO: Draw TITLE screen here!
                drawRectangle(0, 0, screen.width(), screen.height(), GREEN);
                drawText("TITLE SCREEN", 20, 20, 40, DARKGREEN);
                drawText("PRESS ENTER or TAP to JUMP to GAMEPLAY SCREEN", 120, 220, 20, DARKGREEN);

            } break;
            case Gameplay:
            {
                // TODO: Draw GAMEPLAY screen here!
                drawRectangle(0, 0,  screen.width(), screen.height(), PURPLE);
                drawText("GAMEPLAY SCREEN", 20, 20, 40, MAROON);
                drawText("PRESS ENTER or TAP to JUMP to ENDING SCREEN", 130, 220, 20, MAROON);

            } break;
            case Ending:
            {
                // TODO: Draw ENDING screen here!
                drawRectangle(0, 0,  screen.width(), screen.height(), BLUE);
                drawText("ENDING SCREEN", 20, 20, 40, DARKBLUE);
                drawText("PRESS ENTER or TAP to RETURN to TITLE SCREEN", 120, 220, 20, DARKBLUE);

            } break;
            default: break;
        }

        endDrawing();
        //----------------------------------------------------------------------------------
    }

}
