package com.company;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends GameObject implements Entity, Resizable {
    private static final int TILEMAP_ROW_WIDTH = 36;
    private static final int TILEMAP_ROW_HEIGHT = 18;
    private int normalizedWidth;
    private int normalizedHeight;
    private int screenWidth;
    private int screenHeight;
    private double velX = 0;
    private double velY = 0;

    Game game;
    private Graphics g;
    private BufferedImage tank;



    public Player (int x, int y, Game game, Graphics g) {
        super(x, y);
        this.game = game;
        this.g = g;
        screenWidth = game.getWidth();
        screenHeight = game.getHeight();
        normalizedWidth = screenWidth / TILEMAP_ROW_WIDTH;
        normalizedHeight = screenHeight / TILEMAP_ROW_HEIGHT;
        String tankImageLocation = "hero.png";

        BufferedImageLoader loader = new BufferedImageLoader(tankImageLocation);

        try {
            tank = loader.loadImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setVelX  (double velX) {
        this.velX = velX;
    }

    public void setVelY (double velY) {
        this.velY = velY;
    }

    @Override
    public void scale (int pastScreenWidth, int pastScreenHeight, Graphics g) {
        Dimension screenSize = this.game.getSize();
        int currentScreenWidth = (int)screenSize.getWidth();
        int currentScreenHeight = (int)screenSize.getHeight();

        if (pastScreenWidth != currentScreenWidth || pastScreenHeight != currentScreenHeight) {
            screenWidth = currentScreenWidth;
            screenHeight = currentScreenHeight;
            normalizedWidth = (int) Math.round(screenSize.getWidth() / TILEMAP_ROW_WIDTH);
            normalizedHeight = (int) Math.round(screenSize.getHeight() / TILEMAP_ROW_HEIGHT);

            if (x == 0) {
                x = 0;
            } else {
                x = (int)(((double)x / pastScreenWidth) * screenWidth);
            }

            if (y == 0) {
                y = 0;
            } else {
                y = (int)(((double)y / pastScreenHeight) * screenHeight);
            }
        }
    }

    @Override
    public void tick () {
        scale(screenWidth, screenHeight, g);

        x += velX;
        y += velY;

        if (x <= 0) {
            x = 0;
        }

        if ( y <= 0) {
            y = 0;
        }

        if (x >= screenWidth - normalizedWidth) {
            x = screenWidth - normalizedWidth;
        }


        if (y >= screenHeight - normalizedHeight) {
            y = screenHeight - normalizedHeight ;
        }
    }

    @Override
    public void render (Graphics g) {
        g.drawImage(Scalr.resize(tank, normalizedWidth, normalizedHeight), x, y, normalizedWidth, normalizedHeight, null);
    }
}
