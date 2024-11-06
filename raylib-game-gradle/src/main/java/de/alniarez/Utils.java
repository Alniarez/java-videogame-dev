package de.alniarez;

import com.raylib.Raylib;
import com.raylib.Texture;

public class Utils {

   public static Texture loadTexture(String path) {
       var resource = Utils.class.getResource(path);
       return (resource != null) ? Raylib.loadTexture(resource.getPath()) : null;
   }

}
