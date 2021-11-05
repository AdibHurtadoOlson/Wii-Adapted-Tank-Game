package com.company;
import java.awt.*;

public class Counter {
    private static final int fontSize = 20;
    private Color counterFontColor = Color.BLACK;
    private Font levelPassedCounterFont = new Font("Comics", Font.BOLD, fontSize);
    private static final int counterXPosition = 10;
    private static final int counterYPosition = 30;
    public static int counter = 0;


    public void render (Graphics g) {
        g.setFont(levelPassedCounterFont);
        g.setColor(counterFontColor);
        g.drawString(Integer.toString(counter), counterXPosition, counterYPosition);
    }

    public void reset () {
        this.counter = 0;
    }
}
