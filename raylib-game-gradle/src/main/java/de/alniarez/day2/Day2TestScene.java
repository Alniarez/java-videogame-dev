package de.alniarez.day2;

import com.raylib.Vector2;
import de.alniarez.core.Scene;

import static com.raylib.Raylib.*;
import static com.raylib.Raylib.endDrawing;

public class Day2TestScene implements Scene {

    // Default values
    private int _width = 640;
    private int _height = 480;
    private float _rotation = 0.0f;

    public int width(){
        return _width;
    }

    public Day2TestScene width(int width){
        _width = width;
        return this;
    }

    public int height(){
        return _height;
    }

    public Day2TestScene height(int height){
        _height = height;
        return this;
    }

    public float rotation(){
        return _rotation;
    }

    public Day2TestScene rotation(float rotation){
        _rotation = rotation;
        return this;
    }

    @Override
    public void logicUpdate() {
        rotation(rotation() + 0.6f);
    }

    @Override
    public void render() {
        beginDrawing();

        clearBackground(RAYWHITE);

        // System.out.println("Drawing with rotation: " + _rotation);

        drawText("some basic shapes available on raylib", 20, 20, 20, DARKGRAY);

        // Circle shapes and lines
        drawCircle(width() / 5, 120, 35, DARKBLUE);
        drawCircleGradient(width() / 5, 220, 60, GREEN, SKYBLUE);
        drawCircleLines(width() / 5, 340, 80, DARKBLUE);

        // Rectangle shapes and lines
        drawRectangle(width() / 4 * 2 - 60, 100, 120, 60, RED);
        drawRectangleGradientH(width() / 4 * 2 - 90, 170, 180, 130, MAROON, GOLD);
        drawRectangleLines(width() / 4 * 2 - 40, 320, 80, 60, ORANGE);  // NOTE: Uses QUADS internally, not lines

        // Triangle shapes and lines
        drawTriangle(new Vector2(width() / 4.0f * 3.0f, 80.0f),
                new Vector2(width() / 4.0f * 3.0f - 60.0f, 150.0f),
                new Vector2(width() / 4.0f * 3.0f + 60.0f, 150.0f), VIOLET);

        drawTriangleLines(new Vector2(width() / 4.0f * 3.0f, 160.0f),
                new Vector2(width() / 4.0f * 3.0f - 20.0f, 230.0f),
                new Vector2(width() / 4.0f * 3.0f + 20.0f, 230.0f), DARKBLUE);

        // Polygon shapes and lines
        drawPoly(new Vector2(width() / 4.0f * 3, 330), 6, 80, rotation(), BROWN);
        drawPolyLines(new Vector2(width() / 4.0f * 3, 330), 6, 90, rotation(), BROWN);
        drawPolyLinesEx(new Vector2(width() / 4.0f * 3, 330), 6, 85, rotation(), 6, BEIGE);

        // NOTE: We draw all LINES based shapes together to optimize internal drawing,
        // this way, all LINES are rendered in a single draw pass
        drawLine(18, 42, width() - 18, 42, BLACK);
        endDrawing();
    }

}
