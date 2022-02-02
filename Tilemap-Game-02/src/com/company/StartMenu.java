package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class StartMenu implements Entity, Resizable {
    Game game;
    private Graphics g;

    private int titleXPosition;
    private int titleYPosition;
    private int screenWidth;
    private int screenHeight;
    private int titleFontSize;

    private static final String title = "Press Enter To Start";
    private static final String resume = "Resume";
    private static final String exit = "Exit";

    private Color titleColor = Color.BLACK;
    private Font titleFont;
    private FontMetrics metrics;
    private int totalMenuButtons = 4;
    private String[] menuText = {title, resume, exit};
    private String[] menuImage = {"wall.png", "wall.png", "wall.png"};
    private ArrayList<Button> menuButtons = CreateButton();


    public StartMenu (Game game) {
        this.game = game;
        this.g = game.getGraphics();

        screenWidth = game.getWidth();
        screenHeight = game.getHeight();

        windowResizeVariables(screenWidth, screenHeight);
    }

    public void windowResizeVariables (int currentScreenWidth, int currentScreenHeight) {
        screenWidth = currentScreenWidth;
        screenHeight = currentScreenHeight;
        titleFontSize = screenWidth / 20;
        titleFont = new Font("Comics", Font.BOLD, titleFontSize);
        metrics = g.getFontMetrics(titleFont);
        titleXPosition = screenWidth / 2 - metrics.stringWidth(title) / 2;
        titleYPosition = screenHeight /  2 - metrics.getHeight() / 2;

        for (int buttonCounter = 0; buttonCounter < totalMenuButtons; buttonCounter++) {
            JButton btn = new JButton();
            btn.setText(title);
            btn.setBorderPainted(false);
            btn.setBounds(new Rectangle(titleXPosition, titleYPosition, metrics.stringWidth(title), metrics.getHeight()));
            btn.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setText("Entered");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setText("Exited");
                }
            });
        }
    }

    private ArrayList<Button> CreateButton() {
        ArrayList<Button> menuButtons = new ArrayList<Button>();

        for(int buttonCounter = 0; buttonCounter < 3; buttonCounter++) {
            Font buttonTextFont = new Font("Comics", Font.BOLD, screenWidth / 20);
            FontMetrics buttonTextFontMetrics = g.getFontMetrics(buttonTextFont); 

            menuButtons.add(new Button((screenWidth / 2) - buttonTextFontMetrics.stringWidth(menuText[buttonCounter]),
            (screenHeight / 4) * (buttonCounter + 1) - buttonTextFontMetrics.getHeight() / 2,
            buttonTextFontMetrics.stringWidth(menuText[buttonCounter]),
            screenWidth / 20,
            menuImage[buttonCounter],
            this.game));
        }

        return menuButtons;

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
    }
}
