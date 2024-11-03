package de.alniarez.day1;

import com.raylib.Camera3D;
import com.raylib.Vector3;

import static com.raylib.Raylib.CameraMode.CAMERA_ORBITAL;
import static com.raylib.Raylib.CameraProjection.CAMERA_PERSPECTIVE;
import static com.raylib.Raylib.*;

public class BasicHelloWorld {
    public static void main(String... args) {
        initWindow(800, 450, "Demo");
        setTargetFPS(60);
        Camera3D camera = new Camera3D(new Vector3(18,16,18),
                new Vector3(),
                new Vector3(0,1,0),
                45, CAMERA_PERSPECTIVE);

        while (!windowShouldClose()) {
            updateCamera(camera, CAMERA_ORBITAL);
            beginDrawing();
            clearBackground(RAYWHITE);
            beginMode3D(camera);
            drawGrid(20, 1.0f);
            endMode3D();
            drawText("Hello world", 190, 200, 20, VIOLET);
            drawFPS(20, 20);
            endDrawing();
        }
        closeWindow();
    }
}