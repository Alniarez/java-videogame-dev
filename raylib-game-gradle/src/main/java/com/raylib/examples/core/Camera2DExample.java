/*******************************************************************************************
 *
 *   raylib [core] example - 2D Camera system
 *
 *   Example originally created with raylib 1.5, last time updated with raylib 3.0
 *
 *   Example licensed under an unmodified zlib/libpng license, which is an OSI-certified,
 *   BSD-like license that allows static linking with closed source software
 *
 *   Copyright (c) 2016-2024 Ramon Santamaria (@raysan5)
 *
 ********************************************************************************************/
/*
 * This is a Java version of the original C example for raylib, adapted from the original example using jaylib-ffm.
 */

package com.raylib.examples.core;

import com.raylib.Camera2D;
import com.raylib.Color;
import com.raylib.Rectangle;
import com.raylib.Vector2;
import de.alniarez.IBasicExample;
import de.alniarez.Screen;

import static com.raylib.Raylib.*;
import static com.raylib.Raylib.KeyboardKey.*;

public class Camera2DExample implements IBasicExample {

    private static final int MAX_BUILDINGS = 100;

    private final Screen _screen;

    Rectangle player = new Rectangle(400, 310, 40, 40 );
    Rectangle[] buildings = new Rectangle[MAX_BUILDINGS];
    Color[] buildColors = new Color[MAX_BUILDINGS];
    Camera2D camera;

    public Camera2DExample(Screen screen) {
        _screen = screen;
        _screen.title("raylib [core] example - 2D Camera system").init();

        // Initialization
        //--------------------------------------------------------------------------------------
        int spacing = 0;

        for (int i = 0; i < MAX_BUILDINGS; i++) {

            buildings[i] = new Rectangle();
            buildings[i].width((float)getRandomValue(50, 200));
            buildings[i].height ( (float)getRandomValue(100, 800));
            buildings[i].y(screen.height() - 130.0f - buildings[i].height());
            buildings[i].x(-6000.0f + spacing);

            spacing += (int) buildings[i].width();

            buildColors[i] = new Color((byte) getRandomValue(200, 240),  (byte) getRandomValue(200, 240),  (byte) getRandomValue(200, 250),  (byte) 255 );
        }

        Vector2 target = new Vector2( player.x() + 20.0f, player.y() + 20.0f );
        Vector2 offset = new Vector2(screen.width()/2.0f, screen.height()/2.0f );
        float rotation = 0.0f;
        float zoom = 1.0f;

        camera = new Camera2D(target, offset, rotation, zoom);
        //--------------------------------------------------------------------------------------
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
        // Player movement
        if (isKeyDown(KEY_RIGHT))
            player.x(player.x() + 2);
        else if (isKeyDown(KEY_LEFT))
            player.x(player.x() - 2);

        // Camera target follows player
        camera.target(new Vector2( player.x() + 20, player.y() + 20 ));

        // Camera rotation controls
        if (isKeyDown(KEY_A))
            camera.rotation(camera.rotation() - 1);

        else if (isKeyDown(KEY_S))
            camera.rotation(camera.rotation() + 1);

        // Limit camera rotation to 80 degrees (-40 to 40)
        if (camera.rotation() > 40)
            camera.rotation(40);
        else if (camera.rotation() < -40)
            camera.rotation(-40);

        // Camera zoom controls
        camera.zoom(camera.zoom() + (getMouseWheelMove() *0.05f));

        if (camera.zoom() > 3.0f)
            camera.zoom(3.0f);
        else if (camera.zoom() < 0.1f) camera.zoom(0.1f);

        // Camera reset (zoom and rotation)
        if (isKeyPressed(KEY_R)) {
            camera.zoom(1.0f);
            camera.rotation(0.0f);
        }
        //----------------------------------------------------------------------------------

        // Draw
        //----------------------------------------------------------------------------------
        beginDrawing();

        clearBackground(RAYWHITE);

        beginMode2D(camera);

        drawRectangle(-6000, 320, 13000, 8000, DARKGRAY);

        for (int i = 0; i < MAX_BUILDINGS; i++)
            drawRectangleRec(buildings[i], buildColors[i]);

        drawRectangleRec(player, RED);

        drawLine((int)camera.target().x(), -screen.height()*10, (int)camera.target().x(), screen.height()*10, GREEN);
        drawLine(-screen.width()*10, (int)camera.target().y(), screen.width()*10, (int)camera.target().y(), GREEN);


        endMode2D();

        drawText("SCREEN AREA", 640, 10, 20, RED);

        drawRectangle(0, 0, screen.width(), 5, RED);
        drawRectangle(0, 5, 5, screen.height() - 10, RED);
        drawRectangle(screen.width() - 5, 5, 5, screen.height() - 10, RED);
        drawRectangle(0, screen.height() - 5, screen.width(), 5, RED);

        drawRectangle( 10, 10, 250, 113, fade(SKYBLUE, 0.5f));
        drawRectangleLines( 10, 10, 250, 113, BLUE);

        drawText("Free 2d camera controls:", 20, 20, 10, BLACK);
        drawText("- Right/Left to move Offset", 40, 40, 10, DARKGRAY);
        drawText("- Mouse Wheel to Zoom in-out", 40, 60, 10, DARKGRAY);
        drawText("- A / S to Rotate", 40, 80, 10, DARKGRAY);
        drawText("- R to reset Zoom and Rotation", 40, 100, 10, DARKGRAY);

        endDrawing();
        //----------------------------------------------------------------------------------
    }

}
