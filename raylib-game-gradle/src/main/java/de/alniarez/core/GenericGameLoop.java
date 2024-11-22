package de.alniarez.core;

import static com.raylib.Raylib.closeWindow;
import static com.raylib.Raylib.windowShouldClose;

public abstract class GenericGameLoop {

    public void run() {
        while (!windowShouldClose()) {    // Detect window close button or ESC key
            logicUpdate();

            render();
        }

        closeWindow();
    }

    protected abstract void logicUpdate();

    protected abstract void render();


}
