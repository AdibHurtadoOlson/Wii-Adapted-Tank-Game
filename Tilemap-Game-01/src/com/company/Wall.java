package com.company;
import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferedImage;

public class Wall extends GameObject implements Entity {
    private BufferedImage wall;
    private double velY = 1;
    Random random;
    int rand;

    public Wall(int x, int y) {
        super(x, y);

        BufferedImageLoader loader = new BufferedImageLoader("wall.png");

        try {
            wall = loader.loadImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        random = new Random();
        rand = random.nextInt(10);
    }

    @Override
    public void tick() {
        y = y + (int)velY;

        if (y >= Game.HEIGHT) {
            y = 0;
            rand = random.nextInt(10);
            setVelY(velY + 0.5);
            Counter.counter++;
        }
    }

    @Override
    public void render(Graphics g) {
        for (int i = 0; i < Game.FACTOR; i++) {
            if (i != rand && i != rand + 1) {
                g.drawImage(wall, x + 64 * i, y, null);
            }
        }
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    private ArrayList<Rectangle> getRectangleWall() {
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();

        for (int i = 0; i < Game.FACTOR; i++) {
            if (i != rand && i != rand + 1) {
                rectangles.add(getBounds( 64 * i, y));
            }
        }
        return rectangles;
    }

    public boolean collision(Rectangle rectanglesPlayer) {
        for (Rectangle rec : getRectangleWall()) {
            if (rectanglesPlayer.intersects(rec)) {
                return true;
            }
        }
        return false;
    }

}

