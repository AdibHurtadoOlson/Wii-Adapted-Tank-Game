package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TileMapCreator extends GameObject implements Entity{
    private double normalizedWidth;
    private double normalizedHeight;
    private double tileWidth;
    private double tileHeight;
    private int startX;
    private int startY;
    private String tileMapLocation = "FirstLevel.csv";
    private String line = "";
    private String charSeparator = ",";
    private BufferedImage tile;
    private String[] tileAttributes;
    private ArrayList<Rectangle> tiles = loadTiles();
    private static final int TILEMAP_ROW_WIDTH = 10;
    private static final int TILEMAP_ROW_HEIGHT = 10;
    private ArrayList<ArrayList<String>> multiListTileMap;

    public TileMapCreator (int x, int y) {
        super(x, y);

        BufferedImageLoader loader = new BufferedImageLoader(tileMapLocation);

        try {
            tile = loader.loadImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] readFromCSV () {
        try {
            BufferedReader tileMapCSVReader = new BufferedReader(new FileReader(tileMapLocation));

            while ((line = tileMapCSVReader.readLine()) != null) {
                tileAttributes = line.split(charSeparator);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tileAttributes;
    }

    // Load Sprite Sheets
    private boolean findCharInTileString (String tile, char character) {
        int count = (int) tile.chars().filter(ch -> ch == character).count();
        return (count > 0 ? true : false);
    }

    private ArrayList<ArrayList<String>> tileMapInMultiList (String[] tileMapInSingleList) {
        for (int rowSeparator = 0;
            rowSeparator < TILEMAP_ROW_HEIGHT;
            rowSeparator++) {
            ArrayList<String> tempList = new ArrayList<String>();

            for (int colSeparator = 0;
            colSeparator < TILEMAP_ROW_WIDTH;
            colSeparator++) {
                tempList.add(tileMapInSingleList[(rowSeparator * 10) + colSeparator]);
            }
            multiListTileMap.add(tempList);
        }
        return multiListTileMap;
    }

    private ArrayList<Rectangle> loadTiles () {
        ArrayList<Rectangle> tileMap = new ArrayList<Rectangle>();
        ArrayList<ArrayList<String>> tileMapMultiList = tileMapInMultiList(readFromCSV());
        int nextRowDelim = 0;
        int nextItemDelim = 0;

        for (ArrayList<String> tileMapRow : tileMapMultiList) {
            nextItemDelim = 0;

            for (String tile : tileMapRow) {
                if (findCharInTileString(tile, '0')) {
                    startX = (int) (nextItemDelim * tileWidth);
                    startY = (int) (nextRowDelim * tileHeight);
                } else if (findCharInTileString(tile, '2')) {
                    tileMap.add(getBounds((int) (nextItemDelim * tileWidth),
                            (int) (nextRowDelim * tileHeight)));
                } else if (findCharInTileString(tile, '-6')) {
                    tileMap.add(getBounds((int) (nextItemDelim * tileWidth),
                            (int) (nextRowDelim * tileHeight)));
                }
                nextItemDelim++;
            }
        }
        return tileMap;
    }

    public boolean collision (Rectangle playerRectangle) {
        for (Rectangle rect : loadTiles()) {
            if (playerRectangle.intersects(rect)){
                return true;
            }
        }
        return false;
    }
}
