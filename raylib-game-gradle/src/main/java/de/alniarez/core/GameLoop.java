package de.alniarez.core;

import static com.raylib.Raylib.windowShouldClose;

public class GameLoop {

    private final Screen _screen;
    private final Scene _scene;
    private int _ups;

    private boolean running = false;

    public GameLoop(Screen screen, int updatesPerSecond, Scene scene) {
        this._scene = scene;
        this._screen = screen;
        this._ups = updatesPerSecond;
    }

    public void start() {
        if(!initialize())
            return;

        loop();

        cleanup();
    }


    private boolean initialize(){
        // initialize
        if(_screen == null) {
            System.out.println("No screen selected");
            return false;
        }
        if(_ups <= 0)
            _ups = _screen.fps(); // Assume target updates per second to match target frames (one update on each draw)

        _screen.init(); // create window

        running = true;

        return true;
    }

    private void loop() {
        Thread logicThread = new Thread(this::runLogicLoop, "LogicThread");

        // Start the logic thread
        logicThread.start();

        // Run rendering on the main thread
        runRenderLoop();

        try {
            logicThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

    }


    private void cleanup() {
        running = false;
    }

    private void runLogicLoop() {
        final long updateInterval = 1000000000 / _ups; // Nanoseconds per update
        long lastUpdate = System.nanoTime();

        while (running && running()) {
            long now = System.nanoTime();
            long elapsed = now - lastUpdate;

            if (elapsed >= updateInterval) {
                synchronized (_scene) {
                    _scene.logicUpdate(); // Update game logic
                }
                lastUpdate += updateInterval;
            } else {
                try {
                    Thread.sleep((updateInterval - elapsed) / 1_000_000); // Sleep to save CPU
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void runRenderLoop() {
        while (running && running()) {
            synchronized (_scene) {
                _scene.render(); // Render the scene on the main thread
            }
        }
    }


    boolean running(){
        return !windowShouldClose();
    }


}
