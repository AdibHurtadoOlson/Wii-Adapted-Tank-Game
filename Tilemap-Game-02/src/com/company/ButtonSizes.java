package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class ButtonSizes {
    Game game;
    private int width;
    private int height;
    private int x;
    private int y;
    private BufferedImage image;
    private String imageLocation;
    private Rectangle imageRect;

    public ButtonSizes (int x, int y, int width, int height, String imageLocation, Game game) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imageLocation = imageLocation;
        this.imageRect = new Rectangle(this.x, this.y, this.width, this.height);

        BufferedImageLoader loader = new BufferedImageLoader(this.imageLocation);

        try {
            image = loader.loadImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Rectangle getRect() {
        return imageRect;
    }

    public void setRect(int x, int y, int width, int height) {
        imageRect = new Rectangle(x, y, width, height);
    }
}
