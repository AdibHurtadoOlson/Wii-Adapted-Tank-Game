package com.company;
import java.awt.*;

public class StartMenu {
    private int titleFontSize = 25;
    private String title = "Press Enter To Start";
    private static final int titleXPosition = 205;
    private static final int titleYPosition = 220;
    private Color titleColor = Color.BLACK;
    private Font titleFont = new Font("Comics", Font.BOLD, titleFontSize);

    public void render (Graphics g) {
        g.setFont(titleFont);
        g.setColor(titleColor);
        g.drawString(title, titleXPosition, titleYPosition);
    }
}
