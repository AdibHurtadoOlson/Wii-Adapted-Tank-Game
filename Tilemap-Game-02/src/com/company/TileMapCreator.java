package com.company;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TileMapCreator {
    Game game;
    private int screenWidth;
    private int screenHeight;
    private static final int TILEMAP_WIDTH = 36;
    private static final int TILEMAP_HEIGHT = 18;
    private ArrayList<ArrayList<String>> tileMapFromCSV;
    private ArrayList<ArrayList<TileSizes>> tileSizeMap;
    private ArrayList<Tile> tileMap;
    private ArrayList<PlayerPositionOutline> playerStarts;

    public TileMapCreator(int screenWidth, int screenHeight, Game game) {
        this.game = game;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        tileMapFromCSV = readFromCSV();
        tileSizeMap = tileSizeMap();
        tileMap = loadTiles();
    }

    private ArrayList<ArrayList<String>> readFromCSV () {
        Path currentDirectoryPath = Paths.get("").toAbsolutePath();
        String currentPath = currentDirectoryPath.toString();
        String tileMapLocation = currentPath + "\\src\\assets\\TileMapInfo\\FirstLevel.csv";
        return new CSVFileLoader(tileMapLocation).loadCSV();
    }

    // Load Sprite Sheets
    private boolean findCharInTileString (String tileId, String charSet) {
        for (int charCounter = 0; charCounter < charSet.length(); charCounter++) {
            if (tileId.charAt(charCounter) != charSet.charAt(charCounter)) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<Tile> loadTiles () {
        ArrayList<ArrayList<String>> tileMapMultiList = tileMapFromCSV;
        ArrayList<Tile> returnTileArrayList = new ArrayList<>();
        playerStarts = new ArrayList<>();

        int nextRowDelim = 0;
        int nextItemDelim;

        for (ArrayList<String> strings : tileMapMultiList) {
            nextItemDelim = 0;

            for (String string : strings) {
                if (findCharInTileString(string, "1")) {
                    continue; // Do nothing

                } else if (findCharInTileString(string, "-6")) {
                    returnTileArrayList.add(new Tile(tileWidthSum(nextItemDelim, nextRowDelim),
                            tileHeightSum(nextItemDelim, nextRowDelim),
                            tileSizeMap.get(nextRowDelim).get(nextItemDelim),
                            "wall.png", this.game));


                } else if (findCharInTileString(string, "-2f")) {
                    returnTileArrayList.add(new Tile(tileWidthSum(nextItemDelim, nextRowDelim),
                            tileHeightSum(nextItemDelim, nextRowDelim),
                            tileSizeMap.get(nextRowDelim).get(nextItemDelim),
                            "wall.png", this.game));


                } else if (findCharInTileString(string, "0")) {
                    returnTileArrayList.add(new Tile(tileWidthSum(nextItemDelim, nextRowDelim),
                            tileHeightSum(nextItemDelim, nextRowDelim),
                            tileSizeMap.get(nextRowDelim).get(nextItemDelim),
                            "wall.png", this.game));

                    playerStarts.add(new PlayerPositionOutline(
                            tileWidthSum(nextItemDelim, nextRowDelim), tileHeightSum(nextItemDelim, nextRowDelim)));
                }

                nextItemDelim++;
            }
            nextRowDelim++;
        }

        return returnTileArrayList;
    }

    private ArrayList<ArrayList<TileSizes>> tileSizeMap () {
        int baseWidth = screenWidth / TILEMAP_WIDTH;
        int baseHeight = screenHeight / TILEMAP_HEIGHT;
        int widthMargin = screenWidth % TILEMAP_WIDTH;
        int heightMargin = screenHeight % TILEMAP_HEIGHT;
        ArrayList<ArrayList<TileSizes>> tileSizeList = new ArrayList<>();
        int tempWidth;
        int tempHeight;

        for (int tileMapArrayListCounter = 0; tileMapArrayListCounter < TILEMAP_HEIGHT; tileMapArrayListCounter++) {
            ArrayList<TileSizes> tileSizeRow = new ArrayList<>();

            for (int tileCounter = 0; tileCounter < TILEMAP_WIDTH; tileCounter++) {
                if (tileCounter < TILEMAP_WIDTH - widthMargin) {
                    tempWidth = baseWidth;
                } else {
                    tempWidth = baseWidth + 1;
                }

                if (tileMapArrayListCounter < TILEMAP_HEIGHT - heightMargin) {
                    tempHeight = baseHeight;
                } else {
                    tempHeight = baseHeight + 1;
                }
                tileSizeRow.add(new TileSizes(tempWidth, tempHeight));
            }
            tileSizeList.add(tileSizeRow);
        }

        return tileSizeList;
    }

    private int tileWidthSum (int nextItemDelim, int nextRowDelim) {
        int sum = 0;

        for (int tileCounter = 0; tileCounter < nextItemDelim; tileCounter++) {
            sum += tileSizeMap.get(nextRowDelim).get(tileCounter).getTileWidth();
        }

        return sum;
    }

    private int tileHeightSum (int nextItemDelim, int nextRowDelim) {
        int sum = 0;

        for (int tileRowCounter = 0; tileRowCounter < nextRowDelim ; tileRowCounter++) {
            sum += tileSizeMap.get(tileRowCounter).get(nextItemDelim).getTileHeight();
        }

        return sum;
    }

    public ArrayList<PlayerPositionOutline> getPlayerStarts () {
        return playerStarts;
    }

    public ArrayList<Tile> getTileMap () {
        return tileMap;
    }

    public void render () {
        scale(screenWidth, screenHeight);
    }

    public boolean scale(int pastScreenWidth, int pastScreenHeight) {
        int currentScreenWidth = game.getWidth();
        int currentScreenHeight = game.getHeight();

        if (pastScreenWidth != currentScreenWidth || pastScreenHeight != currentScreenHeight) {
            screenWidth = currentScreenWidth;
            screenHeight = currentScreenHeight;

            tileSizeMap = tileSizeMap();
            tileMap = loadTiles();

            return true;
        }

        return false;
    }
}
