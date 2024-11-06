package com.raylib.examples.core;

import com.raylib.NPatchInfo;
import com.raylib.Rectangle;
import com.raylib.Texture;
import com.raylib.Vector2;
import de.alniarez.IBasicExample;
import de.alniarez.Screen;
import de.alniarez.TextureCleanup;
import de.alniarez.Utils;

import java.lang.ref.Cleaner;

import static com.raylib.Raylib.*;
import static com.raylib.Raylib.NPatchLayout.*;

public class NPathDrawingExample implements IBasicExample {

    private static final Cleaner cleaner = Cleaner.create();
    private final Cleaner.Cleanable _cleanable;

    private final Screen _screen;

    Texture nPatchTexture;

    Vector2 mousePosition, origin;

    Rectangle dstRec1, dstRec2,dstRecH, dstRecV;

    NPatchInfo ninePatchInfo1, ninePatchInfo2, h3PatchInfo, v3PatchInfo;

    public NPathDrawingExample(Screen screen) {
        _screen = screen;
        _screen.title("raylib [textures] example - N-patch drawing").init();

        // Initialization
        //--------------------------------------------------------------------------------------

        // NOTE: Textures MUST be loaded after Window initialization (OpenGL context is required)
        nPatchTexture = Utils.loadTexture("/ninepatch_button.png");

        mousePosition = new Vector2(0,0);
        origin = new Vector2(0,0);

        // Position and size of the n-patches
        dstRec1 = new Rectangle(480.0f, 160.0f, 32.0f, 32.0f );
        dstRec2 = new Rectangle( 160.0f, 160.0f, 32.0f, 32.0f );
        dstRecH = new Rectangle( 160.0f, 93.0f, 32.0f, 32.0f );
        dstRecV = new Rectangle( 92.0f, 160.0f, 32.0f, 32.0f );

        // A 9-patch (NPATCH_NINE_PATCH) changes its sizes in both axis
        ninePatchInfo1 = new NPatchInfo( new Rectangle(0.0f, 0.0f, 64.0f, 64.0f ), 12, 40, 12, 12, NPATCH_NINE_PATCH );
        ninePatchInfo2 = new NPatchInfo( new Rectangle( 0.0f, 128.0f, 64.0f, 64.0f ), 16, 16, 16, 16, NPATCH_NINE_PATCH );

        // A horizontal 3-patch (NPATCH_THREE_PATCH_HORIZONTAL) changes its sizes along the x axis only
        h3PatchInfo =  new NPatchInfo( new Rectangle(0.0f,  64.0f, 64.0f, 64.0f ), 8, 8, 8, 8, NPATCH_THREE_PATCH_HORIZONTAL );

        // A vertical 3-patch (NPATCH_THREE_PATCH_VERTICAL) changes its sizes along the y axis only
        v3PatchInfo =  new NPatchInfo( new Rectangle( 0.0f, 192.0f, 64.0f, 64.0f ), 6, 6, 6, 6, NPATCH_THREE_PATCH_VERTICAL );


        this._cleanable = cleaner.register(this, new TextureCleanup(nPatchTexture));
        //---------------------------------------------------------------------------------------
    }

    @Override
    public Screen getScreen() {
        return _screen;
    }

    @Override
    public void run() {
        // Update
        //----------------------------------------------------------------------------------
        mousePosition = getMousePosition();

        // Resize the n-patches based on mouse position
        dstRec1.width(mousePosition.x() - dstRec1.x()) ;
        dstRec1.height(mousePosition.y() - dstRec1.y());
        dstRec2.width( mousePosition.x() - dstRec2.x());
        dstRec2.height(mousePosition.y() - dstRec2.y());
        dstRecH.width(mousePosition.x ()- dstRecH.x());
        dstRecV.height( mousePosition.y() - dstRecV.y());

        // Set a minimum width and/or height
        if (dstRec1.width() < 1.0f) dstRec1.width( 1.0f);
        if (dstRec1.width() > 300.0f) dstRec1.width( 300.0f);
        if (dstRec1.height() < 1.0f) dstRec1.height( 1.0f);
        if (dstRec2.width() < 1.0f) dstRec2.width( 1.0f);
        if (dstRec2.width() > 300.0f) dstRec2.width( 300.0f);
        if (dstRec2.height() < 1.0f) dstRec2.height( 1.0f);
        if (dstRecH.width() < 1.0f) dstRecH.width( 1.0f);
        if (dstRecV.height() < 1.0f) dstRecV.height(1.0f);
        //----------------------------------------------------------------------------------


        // Draw
        //----------------------------------------------------------------------------------
        beginDrawing();

        clearBackground(RAYWHITE);

        // Draw the n-patches
        drawTextureNPatch(nPatchTexture, ninePatchInfo2, dstRec2, origin, 0.0f, WHITE);
        drawTextureNPatch(nPatchTexture, ninePatchInfo1, dstRec1, origin, 0.0f, WHITE);
        drawTextureNPatch(nPatchTexture, h3PatchInfo, dstRecH, origin, 0.0f, WHITE);
        drawTextureNPatch(nPatchTexture, v3PatchInfo, dstRecV, origin, 0.0f, WHITE);

        // Draw the source texture
        drawRectangleLines(5, 88, 74, 266, BLUE);
        drawTexture(nPatchTexture, 10, 93, WHITE);
        drawText("TEXTURE", 15, 360, 10, DARKGRAY);

        drawText("Move the mouse to stretch or shrink the n-patches", 10, 20, 20, DARKGRAY);

        endDrawing();
        //----------------------------------------------------------------------------------
    }
}
