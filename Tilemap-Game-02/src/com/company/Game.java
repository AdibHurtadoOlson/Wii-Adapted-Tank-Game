package com.company;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable{
    public static int screenWidth = 640;
    public static int screenHeight = 640;
    public final String TITLE = "Tilemap Game";

    public static int playerSpeed = 9;
    public static int playerResetSpeed = 0;

    private boolean running = false;
    private Thread thread;

    private BufferedImage background;

    private Player player;
    private TileMapCreator tileMap;
    private StartMenu start;
    private Counter counter;
    private PauseMenu pause;

    public enum STATE {
        GAME,
        MENU,
        PAUSE,
        RESTART,
        MENU_TO_GAME,
    }

    public static STATE state = STATE.MENU;

    public void init() {
        requestFocus();
        BufferedImageLoader loader = new BufferedImageLoader("background.png");

        try {
            background = loader.loadImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addKeyListener(new KeyInput(this));
        this.addMouseListener(new MouseInput());
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null) {
            createBufferStrategy(3);
        }

        tileMap = new TileMapCreator(screenWidth, screenHeight, this);
        player = new Player(tileMap.getPlayerStarts().get(0).getX(), tileMap.getPlayerStarts().get(0).getY(), this, getGraphics(this.getBufferStrategy()));
        counter = new Counter(this, getGraphics(this.getBufferStrategy()), 0);
        start = new StartMenu(this);
        pause = new PauseMenu(this, getGraphics(this.getBufferStrategy()));
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
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                tick();
                delta--;
            }

            render();

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }
        stop();
    }

    private void tick () {
        if (state == STATE.GAME) {
            player.tick();
            counter.tick();
            counter.addToCounter(1); // Change to be "When enemy tank dies"
//            if (tileMap.collision(player.getBounds(player.getX(), player.getY()))) {
//                // state = STATE.RESTART <- Would restart game if player collides with tile
//            }
        }

        if (state == STATE.MENU) {
            start.tick();
        }

        if (state == STATE.PAUSE) {
            pause.tick();
        }
    }

    private Graphics getGraphics (BufferStrategy bs) {
        return bs.getDrawGraphics();
    }

    private void render () {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = getGraphics(bs);

        g.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        if (state == STATE.GAME) {
            for (Tile tile : tileMap.getTileMap()) {
                tile.render(g);
            }
            tileMap.render();
            player.render(g);
            counter.render(g);
        }

        if (state == STATE.MENU) {
            start.render(g);
        }

        if (state == STATE.RESTART) {
            state = STATE.MENU;
            counter.reset();
        }

        if (state == STATE.PAUSE) {
            pause.render(g);
        }

        if (state == STATE.MENU_TO_GAME) {
            tileMap.render();

            for (Tile tile : tileMap.getTileMap()) {
                tile.render(g);
            }

            state = STATE.GAME;
        }

        g.dispose();
        bs.show();
    }

    public int returnKeyCode (KeyEvent e) {
        return e.getKeyCode();
    }

    public void playerMovement (int key) {
        if (key == KeyEvent.VK_RIGHT) {
            player.setVelX(playerSpeed);
        } else if (key == KeyEvent.VK_LEFT) {
            player.setVelX(-playerSpeed);
        } else if (key == KeyEvent.VK_DOWN) {
            player.setVelY(playerSpeed);
        } else if (key == KeyEvent.VK_UP) {
            player.setVelY(-playerSpeed);
        }
    }

    public void keyPressed (KeyEvent e) {
        int key = returnKeyCode(e);

        if (state == STATE.GAME) {
            playerMovement(key);

            if (key == KeyEvent.VK_R) {
                state = STATE.PAUSE;
            }

        } else if (state == STATE.MENU) {
            if (key == KeyEvent.VK_ENTER) {
                player.setX(tileMap.getPlayerStarts().get(0).getX()); // 0 is, currently, just the first player
                player.setY(tileMap.getPlayerStarts().get(0).getY()); // 0 is, currently, just the first player
                state = STATE.MENU_TO_GAME;
            }
        } else if (state == STATE.PAUSE) {
            if (key == KeyEvent.VK_ENTER) {
                state = STATE.GAME;
            }

            if (key == KeyEvent.VK_ESCAPE) {
                state = STATE.RESTART;
            }
        }
    }

    public void keyReleased (KeyEvent e) {
        int key = returnKeyCode(e);

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

    public static void main (String[] args) {
        Game game = new Game();

        game.setPreferredSize(new Dimension(screenWidth, screenHeight));
        game.setMinimumSize(new Dimension(200, 200));
        game.setMaximumSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height));

        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
    }

}

