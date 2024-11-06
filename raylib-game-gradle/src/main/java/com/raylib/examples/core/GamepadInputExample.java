/*******************************************************************************************
 *
 *   raylib [core] example - Gamepad input
 *
 *   NOTE: This example requires a Gamepad connected to the system
 *         raylib is configured to work with the following gamepads:
 *                - Xbox 360 Controller (Xbox 360, Xbox One)
 *                - PLAYSTATION(R)3 Controller
 *         Check raylib.h for buttons configuration
 *
 *   Example originally created with raylib 1.1, last time updated with raylib 4.2
 *
 *   Example licensed under an unmodified zlib/libpng license, which is an OSI-certified,
 *   BSD-like license that allows static linking with closed source software
 *
 *   Copyright (c) 2013-2024 Ramon Santamaria (@raysan5)
 *
 ********************************************************************************************/
/*
 * This is a Java version of the original C example for raylib, adapted from the original example using jaylib-ffm.
 *
 * TODO image resources do not load:
 *  WARNING: FILEIO: [resources/ps3.png] Failed to open file
 *  WARNING: FILEIO: [resources/xbox.png] Failed to open file
 */

package com.raylib.examples.core;

import com.raylib.Color;
import com.raylib.Rectangle;
import com.raylib.Texture;
import com.raylib.Vector2;
import de.alniarez.IBasicExample;
import de.alniarez.Screen;
import de.alniarez.TextureCleanup;

import java.lang.ref.Cleaner;

import static com.raylib.Raylib.*;
import static com.raylib.Raylib.ConfigFlags.FLAG_MSAA_4X_HINT;
import static com.raylib.Raylib.GamepadAxis.*;
import static com.raylib.Raylib.GamepadButton.*;
import static com.raylib.Raylib.KeyboardKey.KEY_LEFT;
import static com.raylib.Raylib.KeyboardKey.KEY_RIGHT;

public class GamepadInputExample implements IBasicExample {

    private static final Cleaner cleaner = Cleaner.create();
    private final Cleaner.Cleanable _cleanable;
    private final Screen _screen;

    private static final String XBOX_ALIAS_1 = "xbox";
    private static final String XBOX_ALIAS_2 = "x-box";
    private static final String PS_ALIAS = "playstation";

    // Set axis deadzones
    float leftStickDeadzoneX = 0.1f;
    float leftStickDeadzoneY = 0.1f;
    float rightStickDeadzoneX = 0.1f;
    float rightStickDeadzoneY = 0.1f;
    float leftTriggerDeadzone = -0.9f;
    float rightTriggerDeadzone = -0.9f;

    // Texture2D
    Texture texPs3Pad;
    Texture texXboxPad;

    int gamepad = 0; // which gamepad to display

    public GamepadInputExample(Screen screen) {
        _screen = screen;

        setConfigFlags(FLAG_MSAA_4X_HINT);  // Set MSAA 4X hint before windows creation
        _screen.title("raylib [core] example - Gamepad input").init();

        var ps3Resource = getClass().getResource("/ps3.png");
        var xboxResource = getClass().getResource("/xbox.png");

        if (ps3Resource != null) texPs3Pad = loadTexture(ps3Resource.getPath());

        if (xboxResource != null) texXboxPad = loadTexture(xboxResource.getPath());

        this._cleanable = cleaner.register(this, new TextureCleanup(texPs3Pad, texXboxPad));
    }


    @Override
    public Screen getScreen() {
        return _screen;
    }

    @Override
    public void run() {
        // Draw
        //----------------------------------------------------------------------------------
        beginDrawing();

        clearBackground(RAYWHITE);

        if (isKeyPressed(KEY_LEFT) && gamepad > 0) gamepad--;
        if (isKeyPressed(KEY_RIGHT)) gamepad++;

        if (isGamepadAvailable(gamepad)) {
            drawText(String.format("GP%d: %s", gamepad, getGamepadName(gamepad)), 10, 10, 10, BLACK);

            // Get axis values
            float leftStickX = getGamepadAxisMovement(gamepad, GAMEPAD_AXIS_LEFT_X);
            float leftStickY = getGamepadAxisMovement(gamepad, GAMEPAD_AXIS_LEFT_Y);
            float rightStickX = getGamepadAxisMovement(gamepad, GAMEPAD_AXIS_RIGHT_X);
            float rightStickY = getGamepadAxisMovement(gamepad, GAMEPAD_AXIS_RIGHT_Y);
            float leftTrigger = getGamepadAxisMovement(gamepad, GAMEPAD_AXIS_LEFT_TRIGGER);
            float rightTrigger = getGamepadAxisMovement(gamepad, GAMEPAD_AXIS_RIGHT_TRIGGER);

            // Calculate deadzones
            if (leftStickX > -leftStickDeadzoneX && leftStickX < leftStickDeadzoneX) leftStickX = 0.0f;
            if (leftStickY > -leftStickDeadzoneY && leftStickY < leftStickDeadzoneY) leftStickY = 0.0f;
            if (rightStickX > -rightStickDeadzoneX && rightStickX < rightStickDeadzoneX) rightStickX = 0.0f;
            if (rightStickY > -rightStickDeadzoneY && rightStickY < rightStickDeadzoneY) rightStickY = 0.0f;
            if (leftTrigger < leftTriggerDeadzone) leftTrigger = -1.0f;
            if (rightTrigger < rightTriggerDeadzone) rightTrigger = -1.0f;

            if (textFindIndex(getGamepadName(gamepad).toLowerCase(), XBOX_ALIAS_1) > -1 || textFindIndex(getGamepadName(gamepad).toLowerCase(), XBOX_ALIAS_2) > -1) {
                drawTexture(texXboxPad, 0, 0, DARKGRAY);

                // Draw buttons: xbox home
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_MIDDLE)) drawCircle(394, 89, 19, RED);

                // Draw buttons: basic
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_MIDDLE_RIGHT)) drawCircle(436, 150, 9, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_MIDDLE_LEFT)) drawCircle(352, 150, 9, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_FACE_LEFT)) drawCircle(501, 151, 15, BLUE);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_FACE_DOWN)) drawCircle(536, 187, 15, LIME);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_FACE_RIGHT)) drawCircle(572, 151, 15, MAROON);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_FACE_UP)) drawCircle(536, 115, 15, GOLD);

                // Draw buttons: d-pad
                drawRectangle(317, 202, 19, 71, BLACK);
                drawRectangle(293, 228, 69, 19, BLACK);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_FACE_UP)) drawRectangle(317, 202, 19, 26, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_FACE_DOWN))
                    drawRectangle(317, 202 + 45, 19, 26, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_FACE_LEFT)) drawRectangle(292, 228, 25, 19, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_FACE_RIGHT))
                    drawRectangle(292 + 44, 228, 26, 19, RED);

                // Draw buttons: left-right back
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_TRIGGER_1)) drawCircle(259, 61, 20, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_TRIGGER_1)) drawCircle(536, 61, 20, RED);

                // Draw axis: left joystick
                Color leftGamepadColor = BLACK;
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_THUMB)) leftGamepadColor = RED;
                drawCircle(259, 152, 39, BLACK);
                drawCircle(259, 152, 34, LIGHTGRAY);
                drawCircle(259 + (int) (leftStickX * 20), 152 + (int) (leftStickY * 20), 25, leftGamepadColor);

                // Draw axis: right joystick
                Color rightGamepadColor = BLACK;
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_THUMB)) rightGamepadColor = RED;
                drawCircle(461, 237, 38, BLACK);
                drawCircle(461, 237, 33, LIGHTGRAY);
                drawCircle(461 + (int) (rightStickX * 20), 237 + (int) (rightStickY * 20), 25, rightGamepadColor);

                // Draw axis: left-right triggers
                drawRectangle(170, 30, 15, 70, GRAY);
                drawRectangle(604, 30, 15, 70, GRAY);
                drawRectangle(170, 30, 15, (int) (((1 + leftTrigger) / 2) * 70), RED);
                drawRectangle(604, 30, 15, (int) (((1 + rightTrigger) / 2) * 70), RED);

                // drawText(TextFormat("Xbox axis LT: %02.02f", getGamepadAxisMovement(gamepad, GAMEPAD_AXIS_LEFT_TRIGGER)), 10, 40, 10, BLACK);
                // drawText(TextFormat("Xbox axis RT: %02.02f", getGamepadAxisMovement(gamepad, GAMEPAD_AXIS_RIGHT_TRIGGER)), 10, 60, 10, BLACK);
            } else if (textFindIndex(getGamepadName(gamepad).toLowerCase(), PS_ALIAS) > -1) {
                drawTexture(texPs3Pad, 0, 0, DARKGRAY);

                // Draw buttons: ps
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_MIDDLE)) drawCircle(396, 222, 13, RED);

                // Draw buttons: basic
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_MIDDLE_LEFT)) drawRectangle(328, 170, 32, 13, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_MIDDLE_RIGHT))
                    drawTriangle(new Vector2(436, 168), new Vector2(436, 185), new Vector2(464, 177), RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_FACE_UP)) drawCircle(557, 144, 13, LIME);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_FACE_RIGHT)) drawCircle(586, 173, 13, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_FACE_DOWN)) drawCircle(557, 203, 13, VIOLET);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_FACE_LEFT)) drawCircle(527, 173, 13, PINK);

                // Draw buttons: d-pad
                drawRectangle(225, 132, 24, 84, BLACK);
                drawRectangle(195, 161, 84, 25, BLACK);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_FACE_UP)) drawRectangle(225, 132, 24, 29, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_FACE_DOWN))
                    drawRectangle(225, 132 + 54, 24, 30, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_FACE_LEFT)) drawRectangle(195, 161, 30, 25, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_FACE_RIGHT))
                    drawRectangle(195 + 54, 161, 30, 25, RED);

                // Draw buttons: left-right back buttons
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_TRIGGER_1)) drawCircle(239, 82, 20, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_TRIGGER_1)) drawCircle(557, 82, 20, RED);

                // Draw axis: left joystick
                Color leftGamepadColor = BLACK;
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_THUMB)) leftGamepadColor = RED;
                drawCircle(319, 255, 35, BLACK);
                drawCircle(319, 255, 31, LIGHTGRAY);
                drawCircle(319 + (int) (leftStickX * 20), 255 + (int) (leftStickY * 20), 25, leftGamepadColor);

                // Draw axis: right joystick
                Color rightGamepadColor = BLACK;
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_THUMB)) rightGamepadColor = RED;
                drawCircle(475, 255, 35, BLACK);
                drawCircle(475, 255, 31, LIGHTGRAY);
                drawCircle(475 + (int) (rightStickX * 20), 255 + (int) (rightStickY * 20), 25, rightGamepadColor);

                // Draw axis: left-right triggers
                drawRectangle(169, 48, 15, 70, GRAY);
                drawRectangle(611, 48, 15, 70, GRAY);
                drawRectangle(169, 48, 15, (int) (((1 + leftTrigger) / 2) * 70), RED);
                drawRectangle(611, 48, 15, (int) (((1 + rightTrigger) / 2) * 70), RED);
            } else {

                // Draw background: generic
                drawRectangleRounded(new Rectangle(175, 110, 460, 220), 0.3f, 0, DARKGRAY);

                // Draw buttons: basic
                drawCircle(365, 170, 12, RAYWHITE);
                drawCircle(405, 170, 12, RAYWHITE);
                drawCircle(445, 170, 12, RAYWHITE);
                drawCircle(516, 191, 17, RAYWHITE);
                drawCircle(551, 227, 17, RAYWHITE);
                drawCircle(587, 191, 17, RAYWHITE);
                drawCircle(551, 155, 17, RAYWHITE);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_MIDDLE_LEFT)) drawCircle(365, 170, 10, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_MIDDLE)) drawCircle(405, 170, 10, GREEN);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_MIDDLE_RIGHT)) drawCircle(445, 170, 10, BLUE);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_FACE_LEFT)) drawCircle(516, 191, 15, GOLD);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_FACE_DOWN)) drawCircle(551, 227, 15, BLUE);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_FACE_RIGHT)) drawCircle(587, 191, 15, GREEN);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_FACE_UP)) drawCircle(551, 155, 15, RED);

                // Draw buttons: d-pad
                drawRectangle(245, 145, 28, 88, RAYWHITE);
                drawRectangle(215, 174, 88, 29, RAYWHITE);
                drawRectangle(247, 147, 24, 84, BLACK);
                drawRectangle(217, 176, 84, 25, BLACK);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_FACE_UP)) drawRectangle(247, 147, 24, 29, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_FACE_DOWN))
                    drawRectangle(247, 147 + 54, 24, 30, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_FACE_LEFT)) drawRectangle(217, 176, 30, 25, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_FACE_RIGHT))
                    drawRectangle(217 + 54, 176, 30, 25, RED);

                // Draw buttons: left-right back
                drawRectangleRounded(new Rectangle(215, 98, 100, 10), 0.5f, 0, DARKGRAY);
                drawRectangleRounded(new Rectangle(495, 98, 100, 10), 0.5f, 0, DARKGRAY);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_TRIGGER_1))
                    drawRectangleRounded(new Rectangle(215, 98, 100, 10), 0.5f, 0, RED);
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_TRIGGER_1))
                    drawRectangleRounded(new Rectangle(495, 98, 100, 10), 0.5f, 0, RED);

                // Draw axis: left joystick
                Color leftGamepadColor = BLACK;
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_LEFT_THUMB)) leftGamepadColor = RED;
                drawCircle(345, 260, 40, BLACK);
                drawCircle(345, 260, 35, LIGHTGRAY);
                drawCircle(345 + (int) (leftStickX * 20), 260 + (int) (leftStickY * 20), 25, leftGamepadColor);

                // Draw axis: right joystick
                Color rightGamepadColor = BLACK;
                if (isGamepadButtonDown(gamepad, GAMEPAD_BUTTON_RIGHT_THUMB)) rightGamepadColor = RED;
                drawCircle(465, 260, 40, BLACK);
                drawCircle(465, 260, 35, LIGHTGRAY);
                drawCircle(465 + (int) (rightStickX * 20), 260 + (int) (rightStickY * 20), 25, rightGamepadColor);

                // Draw axis: left-right triggers
                drawRectangle(151, 110, 15, 70, GRAY);
                drawRectangle(644, 110, 15, 70, GRAY);
                drawRectangle(151, 110, 15, (int) (((1 + leftTrigger) / 2) * 70), RED);
                drawRectangle(644, 110, 15, (int) (((1 + rightTrigger) / 2) * 70), RED);

            }

            drawText(String.format("DETECTED AXIS [%d]:", getGamepadAxisCount(0)), 10, 50, 10, MAROON);

            for (int i = 0; i < getGamepadAxisCount(0); i++) {
                drawText(String.format("AXIS %d: %.02f", i, getGamepadAxisMovement(0, i)), 20, 70 + 20 * i, 10, DARKGRAY);
            }

            if (getGamepadButtonPressed() != GAMEPAD_BUTTON_UNKNOWN)
                drawText(String.format("DETECTED BUTTON: %d", getGamepadButtonPressed()), 10, 430, 10, RED);
            else drawText("DETECTED BUTTON: NONE", 10, 430, 10, GRAY);
        } else {
            drawText(String.format("GP%d: NOT DETECTED", gamepad), 10, 10, 10, GRAY);

            drawTexture(texXboxPad, 0, 0, LIGHTGRAY);
        }

        endDrawing();
        //----------------------------------------------------------------------------------
    }

}
