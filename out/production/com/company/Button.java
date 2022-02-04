package com.company;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Button implements MouseListener {
    Game game;
    Graphics g;
    private int width;
    private int height;
    private int x;
    private int y;
    private BufferedImage image;
    private String text;
    private String imageLocation;
    private Rectangle imageRect;
    private boolean mouseEnteredButton;

    public Button (int x, int y, int width, int height, String imageLocation, String text, Game game) {
        this.game = game;
        this.g = game.getGraphics();
        this.x = x;
        this.y = y;
        this.text = text;
        this.width = width;
        this.height = height;
        this.imageLocation = imageLocation;
        this.imageRect = new Rectangle(this.x, this.y, this.width, this.height);
        this.mouseEnteredButton = false;

        BufferedImageLoader loader = new BufferedImageLoader(this.imageLocation);

        try {
            this.image = loader.loadImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Rectangle getRect() {
        return imageRect;
    }

    public void setRect(int x, int y, int width, int height) {
        imageRect = new Rectangle(x, y, width, height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (mouseEnteredButton == true) {

        }
        // If mouseEntered and mouseClicked -> Go to specified menu
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Focus somehow
        // Enlarge icon
        // Make icon bolder
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Return icon to normal size
        // Return icon to correct 'boldness'
    }

    public BufferedImage getButtonImage() {
        return this.image;
    }

    public String getButtonText() {
        return this.text;
    }
}
