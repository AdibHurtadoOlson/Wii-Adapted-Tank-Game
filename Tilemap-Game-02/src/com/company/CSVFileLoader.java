package com.company;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CSVFileLoader {
    private ArrayList<ArrayList<String>> rawTileMap = new ArrayList<ArrayList<String>>();
    private String csvFilePath;

    public CSVFileLoader (String path) {
        csvFilePath = path;
    }

    public ArrayList<ArrayList<String>> loadCSV () {
        try {
            FileReader csvFileReader = new FileReader(csvFilePath);
            BufferedReader csvFileBufferedReader = new BufferedReader(csvFileReader);

            String line = csvFileBufferedReader.readLine();
            int lineCounter = 0;
            while (line != null) {
                ArrayList<String> tilesInLine = new ArrayList<String>();
                for(String tile : line.split(",")) {
                    tilesInLine.add(tile);
                }
                rawTileMap.add(tilesInLine);
                line = csvFileBufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return rawTileMap;
    }
}