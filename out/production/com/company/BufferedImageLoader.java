package com.company;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

public class BufferedImageLoader {
    private BufferedImage image;
    private String fileExtension =
            "src/assets/TankImages/";
    private String imageFilePath;

    public BufferedImageLoader (String path) {
        imageFilePath = fileExtension + path;
    }

    public BufferedImage loadImage () throws IOException {
        image = ImageIO.read(new File(new File(imageFilePath).getAbsolutePath()));
        return image;
    }

}