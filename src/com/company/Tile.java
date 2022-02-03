package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tile extends GameObject implements Entity {
    Game game;
    private String tileType;
    private Rectangle tileRect;
    BufferedImage tileImage;
    TileSizes tileSize;
    private int x;
    private int y;

    public Tile (int x, int y, TileSizes tileSize, String tileString, Game game) {
        super(x, y);
        this.tileSize = tileSize;
        this.game = game;
        this.tileType = tileString;
        this.x = x;
        this.y = y;
        this.tileRect = createRect();

        BufferedImageLoader loader = new BufferedImageLoader(tileType);

        try {
            this.tileImage = loader.loadImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Rectangle createRect () {
        return new Rectangle(this.x, this.y, this.tileSize.getTileWidth(), this.tileSize.getTileHeight());
    }

    private void tileAnimation () {

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Scalr.resize(tileImage, tileRect.width, tileRect.height),
                 tileRect.x, tileRect.y,tileRect.width, tileRect.height,null);
    }
}
