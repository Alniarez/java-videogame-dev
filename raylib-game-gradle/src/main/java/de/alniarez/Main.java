package de.alniarez;

import com.raylib.examples.core.NPathDrawingExample;
import com.raylib.examples.shapes.BasicShapes;
import de.alniarez.core.GameLoop;
import de.alniarez.core.Screen;
import de.alniarez.day2.Day2TestScene;

import static com.raylib.Raylib.closeWindow;
import static com.raylib.Raylib.windowShouldClose;


/**
 * This is the main runner for the raylib examples and demos.
 * It contains a basic game loop and sets the main window by using the Screen class,
 * making some of the most basic examples look incomplete or broken.
 * <p>
 * Inside the main just uncomment what example to run one at a time.
 */

public class Main {

    public static void main(String... args) {
        // Raylib examples
         // runExample();


        Screen screen = Screen.instance();
        screen.width(800).height(480).fps(144).init();

        var scene = new Day2TestScene().width(800).height(480);
        var gameLoop = new GameLoop(screen, 30, scene);
        gameLoop.start();

    }

    static void runExample() {
        // Create screen
        Screen screen = Screen.instance();
        screen.width(800).height(480).fps(144);

        IBasicExample example;
        // example = new BasicWindow(screen);
        // example = new BasicScreenManager(screen);
        example = new BasicShapes(screen);
        // example = new Camera2DExample(screen);
        // example = new GamepadInputExample(screen);
        // example = new MoveBallArrowKeysExample(screen);
        // example = new MoveBallMousePositionExample(screen);
        // example = new NPathDrawingExample(screen);

        // Main game loop
        while (!windowShouldClose()) {    // Detect window close button or ESC key
            // Run example

            example.run();
        }

        closeWindow();

    }
}