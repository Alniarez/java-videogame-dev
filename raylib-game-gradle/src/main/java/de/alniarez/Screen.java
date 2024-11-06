package de.alniarez;

import static com.raylib.Raylib.initWindow;
import static com.raylib.Raylib.setTargetFPS;

/**
 * Basic raylib window/screen.
 * It contains some defaults, and its implementation assumes there is only one possible screen at a time.
 */
public class Screen {

    /* - singleton -*/
    private static Screen _instance = null;

    public static Screen instance() {
        if (_instance == null) {
            _instance = new Screen();
        }

        return _instance;
    }

    /* - Screen class -*/
    private boolean initialized = false;

    // Default values
    private int _width = 640;
    private int _height = 480;
    private int _fps = 60;
    private String _title = "Demo";

    public int width(){
        return _width;
    }

    public Screen width(int width){
        _width = width;
        return this;
    }

    public int height(){
        return _height;
    }

    public Screen height(int height){
        _height = height;
        return this;
    }

    public int fps(){
        return _fps;
    }

    public Screen fps(int fps){
        _fps = fps;
        return this;
    }

    public String title(){
        return _title;
    }

    public Screen title(String title){
        _title = title;
        return this;
    }

    public void init(){
        if(initialized)
           return;

        initWindow(width(), height(), title());
        setTargetFPS(fps());

        this.initialized = true;
    }

    private Screen(){
        super();
    }

}
