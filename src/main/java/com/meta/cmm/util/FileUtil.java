package com.meta.cmm.util;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileUtil {
    
    public static boolean makePath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return file.mkdirs();
        }
        
        return false;
    }

    public static boolean isImage(String filepath) {
        try {
            return ImageIO.read(new File(filepath)) != null;
        } catch (IOException e) {
            return false;
        }
    }
}
