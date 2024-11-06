/*******************************************************************************************
 *
 *   raylib [shapes] example - Draw basic shapes 2d (rectangle, circle, line...)
 *
 *   Example originally created with raylib 1.0, last time updated with raylib 4.2
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


package com.raylib.examples.shapes;

import com.raylib.Vector2;
import de.alniarez.IBasicExample;
import de.alniarez.Screen;

import static com.raylib.Raylib.*;

public class BasicShapes implements IBasicExample {

    private final Screen _screen;
    float rotation = 0.0f;

    public BasicShapes(Screen screen) {
        _screen = screen;
        _screen.title("raylib [shapes] example - basic shapes drawing").init();
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
        rotation += 0.2f;
        //----------------------------------------------------------------------------------

        // Draw
        //----------------------------------------------------------------------------------
        beginDrawing();

        clearBackground(RAYWHITE);

        drawText("some basic shapes available on raylib", 20, 20, 20, DARKGRAY);

        // Circle shapes and lines
        drawCircle(screen.width() / 5, 120, 35, DARKBLUE);
        drawCircleGradient(screen.width() / 5, 220, 60, GREEN, SKYBLUE);
        drawCircleLines(screen.width() / 5, 340, 80, DARKBLUE);

        // Rectangle shapes and lines
        drawRectangle(screen.width() / 4 * 2 - 60, 100, 120, 60, RED);
        drawRectangleGradientH(screen.width() / 4 * 2 - 90, 170, 180, 130, MAROON, GOLD);
        drawRectangleLines(screen.width() / 4 * 2 - 40, 320, 80, 60, ORANGE);  // NOTE: Uses QUADS internally, not lines

        // Triangle shapes and lines
        drawTriangle(new Vector2(screen.width() / 4.0f * 3.0f, 80.0f),
                new Vector2(screen.width() / 4.0f * 3.0f - 60.0f, 150.0f),
                new Vector2(screen.width() / 4.0f * 3.0f + 60.0f, 150.0f), VIOLET);

        drawTriangleLines(new Vector2(screen.width() / 4.0f * 3.0f, 160.0f),
                new Vector2(screen.width() / 4.0f * 3.0f - 20.0f, 230.0f),
                new Vector2(screen.width() / 4.0f * 3.0f + 20.0f, 230.0f), DARKBLUE);

        // Polygon shapes and lines
        drawPoly(new Vector2(screen.width() / 4.0f * 3, 330), 6, 80, rotation, BROWN);
        drawPolyLines(new Vector2(screen.width() / 4.0f * 3, 330), 6, 90, rotation, BROWN);
        drawPolyLinesEx(new Vector2(screen.width() / 4.0f * 3, 330), 6, 85, rotation, 6, BEIGE);

        // NOTE: We draw all LINES based shapes together to optimize internal drawing,
        // this way, all LINES are rendered in a single draw pass
        drawLine(18, 42, screen.width() - 18, 42, BLACK);
        endDrawing();
        //----------------------------------------------------------------------------------

    }

}
