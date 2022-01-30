package com.company;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable{
    public static final int SQUARE = 64;
    public static final int FACTOR = 10;
    public static final int WIDTH = SQUARE * FACTOR;
    public static final int HEIGHT = SQUARE * FACTOR;
    public static final int SCALE = 1;
    public final String TITLE = "BASIC JAVA GAME";

    public static int playerSpeed = 9;
    public static int playerResetSpeed = 0;

    private boolean running = false;
    private Thread thread;

    private BufferedImage background;

    private Player player;
    private Wall wall;
    private StartMenu start;
    private Counter counter;

    public enum STATE {
        GAME,
        RESTART,
    }

    public static STATE state = STATE.RESTART;

    public void init() {
        requestFocus();
        BufferedImageLoader loader = new BufferedImageLoader("background.png");

        try {
            background = loader.loadImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addKeyListener(new KeyInput(this));

        player = new Player(getWidth() / 2 - 32, getHeight());
        wall = new Wall(0, 0);
        counter = new Counter();
        start = new StartMenu();
    }

    private synchronized void start() {
        if (running){
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        if (!running) {
            return;
        }

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(1);
    }

    @Override
    public void run() {
        init();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                tick();
                updates++;
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        if (state == STATE.GAME) {
            player.tick();
            wall.tick();
            if (wall.collision(player.getBounds(player.getX(), player.getY()))) {
                state = STATE.RESTART;
            }
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(background, 0, 0, null);

        if (state == STATE.GAME) {
            player.render(g);
            wall.render(g);
            counter.render(g);
        }

        if (state == STATE.RESTART) {
            start.render(g);
        }

        g.dispose();
        bs.show();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (state == STATE.GAME) {
            if (key == KeyEvent.VK_RIGHT) {
                player.setVelX(playerSpeed);
            } else if (key == KeyEvent.VK_LEFT) {
                player.setVelX(-playerSpeed);
            } else if (key == KeyEvent.VK_DOWN) {
                player.setVelY(playerSpeed);
            } else if (key == KeyEvent.VK_UP) {
                player.setVelY(-playerSpeed);
            }
        } else if (state == STATE.RESTART) {
            if (key == KeyEvent.VK_ENTER) {
                state = STATE.GAME;
                player.setX(getWidth() / 2 - 32);
                player.setY(getHeight());
                wall.y = 0;
                wall.setVelY(1);
                counter.reset();
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if ( key == KeyEvent.VK_RIGHT) {
            player.setVelX(playerResetSpeed);
        } else if (key == KeyEvent.VK_LEFT) {
            player.setVelX(playerResetSpeed);
        } else if (key == KeyEvent.VK_DOWN) {
            player.setVelY(playerResetSpeed);
        } else if (key == KeyEvent.VK_UP) {
            player.setVelY(playerResetSpeed);
        }
    }

    public static void main(String[] args) {
        Game game = new Game();

        game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
    }

}

