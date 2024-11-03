
package de.alniarez;

import static com.raylib.Raylib.*;

import com.raylib.examples.IBasicExample;
import com.raylib.examples.core.*;


/**
 * This is the main runner for the raylib examples and demos.
 * It contains a basic game loop and sets the main window by using the Screen class,
 * making some of the most basic examples look incomplete or broken.
 *
 * Inside the main just uncomment what example to run one at a time.
 */

public class Main {

    public static void main(String... args) {

        // Create screen
        Screen screen = Screen.instance();
        screen.height(480).width(800).fps(144).init();

        IBasicExample example;
        // example = new BasicWindow(screen);
        // example = new BasicScreenManager(screen);
        // example = new BasicShapes(screen);
        example = new Camera2DExample(screen);

        // Main game loop
        while (!windowShouldClose()) {    // Detect window close button or ESC key
            // Run example

            example.run();
        }

        closeWindow();
    }
}