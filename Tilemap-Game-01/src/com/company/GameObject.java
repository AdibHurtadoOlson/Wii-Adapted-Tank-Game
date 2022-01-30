package com.company;
import java.awt.*;

public class GameObject {
    public int x, y;
    private static final int width = 64;
    private static final int height = 64;

    public GameObject (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public void setX (int x) {
        this.x = x;
    }

    public void setY (int y) {
        this.y = y;
    }

    public Rectangle getBounds (int x, int y) {
        return new Rectangle(x, y, width, height);
    }
}
