package de.alniarez;

import com.raylib.Raylib;
import com.raylib.Texture;

import static com.raylib.Raylib.loadTexture;

public class Utils {

   public static Texture loadTexture(String path) {
       var resource = Utils.class.getResource(path);
       Texture texture = null;
       if (resource != null) texture = Raylib.loadTexture(resource.getPath());
       return texture;
   }

}
