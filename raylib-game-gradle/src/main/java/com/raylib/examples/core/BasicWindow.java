/*******************************************************************************************
 *
 *   raylib [core] example - Basic window
 *
 *   Welcome to raylib!
 *
 *   To test examples, just press F6 and execute raylib_compile_execute script
 *   Note that compiled executable is placed in the same folder as .c file
 *
 *   You can find all basic examples on C:\raylib\raylib\examples folder or
 *   raylib official webpage: www.raylib.com
 *
 *   Enjoy using raylib. :)
 *
 *   Example originally created with raylib 1.0, last time updated with raylib 1.0
 *
 *   Example licensed under an unmodified zlib/libpng license, which is an OSI-certified,
 *   BSD-like license that allows static linking with closed source software
 *
 *   Copyright (c) 2013-2024 Ramon Santamaria (@raysan5)
 *
 ********************************************************************************************/
/*
 * This is a Java version of the original C example for raylib, adapted from the original example using jaylib-ffm.
 */


package com.raylib.examples.core;

import de.alniarez.IBasicExample;
import de.alniarez.Screen;

import static com.raylib.Raylib.*;

public class BasicWindow implements IBasicExample {

    private final Screen _screen;

    public BasicWindow(Screen screen) {
        _screen = screen;
        _screen.title("raylib [core] example - Basic window").init();
    }

    @Override
    public Screen getScreen() {
        return _screen;
    }

    @Override
    public void run(){
        beginDrawing();
        clearBackground(RAYWHITE);
        drawText("Congrats! You created your first window!", 190, 200, 20, LIGHTGRAY);
        endDrawing();
    }

}
