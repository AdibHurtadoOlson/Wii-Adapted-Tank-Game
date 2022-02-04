package com.company;

public class TileSizes {
    private int tileWidth;
    private int tileHeight;

    public TileSizes (int width, int height) {
        this.tileWidth = width;
        this.tileHeight = height;
    }

    public int getTileWidth () {
        return this.tileWidth;
    }

    public int getTileHeight () {
        return this.tileHeight;
    }

    public void setTileWidth (int newWidth) {
        this.tileWidth = newWidth;
    }

    public void setTileHeight (int newHeight) {
        this.tileHeight = newHeight;
    }
}
