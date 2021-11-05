package com.company;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Player extends GameObject implements Entity {
    private BufferedImage tank;
    private String tankImageLocation = "hero.png";
    private double velX = 0;
    private double velY = 0;

    public Player (int x, int y) {
        super(x, y);

        BufferedImageLoader loader = new BufferedImageLoader(tankImageLocation);

        try {
            tank = loader.loadImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick () {
        x += velX;
        y += velY;

        if (x <= 0) {
            x = 0;
        }

        if (x >= Game.WIDTH - 64) {
            x = 640 - 64;
        }

        if ( y <= 0) {
            y = 0;
        }

        if (y >= Game.HEIGHT - 64) {
            y = Game.HEIGHT - 64;
        }
    }

    @Override
    public void render (Graphics g) {
        g.drawImage(tank, x, y, null);
    }

    public void setVelX  (double velX) {
        this.velX = velX;
    }

    public void setVelY (double velY) {
        this.velY = velY;
    }
}
