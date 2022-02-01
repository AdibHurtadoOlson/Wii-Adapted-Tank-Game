package com.company;
import java.awt.*;

public class Counter implements Entity, Resizable {
    private int screenWidth;
    private int screenHeight;
    private int fontSize;
    private final Color counterFontColor = Color.BLACK;
    private Font counterFont;
    private int counterXPosition;
    private int counterYPosition;
    public String counterText;
    private int counter;

    Game game;
    Graphics g;
    private FontMetrics counterFontMetrics;

    public Counter (Game game, Graphics g, int counterStart) {
        this.game = game;
        this.g = g;
        this.counter = counterStart;

        windowResizeVariables(this.game.getWidth(), this.game.getHeight());
    }

    public void reset () {
        this.counter = 0;
    }

    public void addToCounter (int numberToAdd) { this.counter += numberToAdd; }

    public void windowResizeVariables (int currentScreenWidth, int currentScreenHeight) {
        screenWidth = currentScreenWidth;
        screenHeight = currentScreenHeight;
        fontSize = screenWidth / 30;
        counterFont = new Font("Comics", Font.BOLD, fontSize);
        counterText = Integer.toString(this.counter);
        counterFontMetrics = g.getFontMetrics(counterFont);
        counterXPosition = screenWidth / 2 - counterFontMetrics.stringWidth(counterText) / 2;
        counterYPosition = screenHeight /  9 - counterFontMetrics.getHeight() / 2;
    }

    @Override
    public void scale (int pastScreenWidth, int pastScreenHeight, Graphics g) {
        Dimension screenSize = this.game.getSize();
        int currentScreenWidth = (int)screenSize.getWidth();
        int currentScreenHeight = (int)screenSize.getHeight();

        if (pastScreenWidth != currentScreenWidth || pastScreenHeight != currentScreenHeight) {
            windowResizeVariables(currentScreenWidth, currentScreenHeight);
        }
    }

    @Override
    public void tick () {
        scale(screenWidth, screenHeight, g);
    }

    @Override
    public void render (Graphics g) {
        g.setFont(counterFont);
        g.setColor(counterFontColor);
        g.drawString(Integer.toString(this.counter), counterXPosition, counterYPosition);

    }
}