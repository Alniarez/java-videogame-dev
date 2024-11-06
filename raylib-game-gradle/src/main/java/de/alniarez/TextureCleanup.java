package de.alniarez;

import com.raylib.Raylib;
import com.raylib.Texture;

import java.util.Optional;

public record TextureCleanup(Texture... textures) implements Runnable {

    @Override
    public void run() {
        for (var texture : textures)
            Optional.ofNullable(texture).ifPresent(Raylib::unloadTexture);
    }

}