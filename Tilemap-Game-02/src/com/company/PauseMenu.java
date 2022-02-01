package com.company;

import java.awt.*;

public class PauseMenu implements Entity, Resizable {
    Game game;
    private int titleXPosition;
    private int titleYPosition;
    private int secondaryTitleXPosition;
    private int secondaryTitleYPosition;
    private int screenWidth;
    private int screenHeight;
    private int titleFontSize;
    private int secondaryTitleFontSize;
    private Graphics g;
    private static final String title = "Game Paused";
    private static final String secondaryTitle = "Press Enter to Resume";
    private final Color titleColor = Color.BLACK;
    private final Color secondaryTitleColor = Color.BLACK;
    private Font titleFont;
    private Font secondaryTitleFont;
    private FontMetrics titleFontMetrics;
    private FontMetrics secondaryTitleFontMetrics;


    public PauseMenu (Game game, Graphics g) {
        this.game = game;
        this.g = g;

        windowResizeVariables(this.game.getWidth(), this.game.getHeight());
    }

    public void windowResizeVariables (int currentScreenWidth, int currentScreenHeight) {
        screenWidth = currentScreenWidth;
        screenHeight = currentScreenHeight;
        titleFontSize = screenWidth / 20;
        secondaryTitleFontSize = screenWidth / 20;
        titleFont = new Font("Comics", Font.BOLD, titleFontSize);
        secondaryTitleFont = new Font("Comics", Font.BOLD, secondaryTitleFontSize);
        titleFontMetrics = g.getFontMetrics(titleFont);
        secondaryTitleFontMetrics = g.getFontMetrics(secondaryTitleFont);
        titleXPosition = screenWidth / 2 - titleFontMetrics.stringWidth(title) / 2;
        titleYPosition = screenHeight /  2 - titleFontMetrics.getHeight() / 2;
        secondaryTitleXPosition = screenWidth / 2 - secondaryTitleFontMetrics.stringWidth(secondaryTitle) / 2;
        secondaryTitleYPosition = screenHeight / 2 - secondaryTitleFontMetrics.getHeight() / 2 + screenHeight / 4;
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
        g.setFont(titleFont);
        g.setColor(titleColor);
        g.drawString(title, titleXPosition, titleYPosition);
        g.setFont(secondaryTitleFont);
        g.setColor(secondaryTitleColor);
        g.drawString(secondaryTitle, secondaryTitleXPosition, secondaryTitleYPosition);
    }
}
