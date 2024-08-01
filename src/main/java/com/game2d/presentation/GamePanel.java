package com.game2d.presentation;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Screen settings
    private final int originalTileSize = 16; //16*16 pixels
    private final int scale = 2;
    private final int tileSize = originalTileSize * scale; //48*48 pixels
    private final int maxScreenColumn = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenColumn; // 768 pixels
    private final int screenHeight = tileSize * maxScreenRow; // 576 pixels


    // FPS
    final int FPS = 60;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread = null;

    // Set players default position
    int playerX = 100;
    int playerY = 100;
    int speedPlayer = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.CYAN);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            double drawInterval = (double) 1000000000 / FPS; // We can draw in the screen every 0.016666 second
            double nextDrawTime = System.nanoTime() + drawInterval;

            // 1.UPDATE: Update information such as character positions
            update();

            // 2. DRAW: Draw the screen the update information
            repaint();


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                Thread.sleep((long) remainingTime);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        if (keyHandler.upPressed) { // keyHandler.upPressed == true

            playerY -= speedPlayer;
        } else if (keyHandler.downPressed) {
            playerY += speedPlayer;
        } else if (keyHandler.leftPressed) {
            playerX -= speedPlayer;
        } else if (keyHandler.rightPressed) {
            playerX += speedPlayer;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; // Casting, because, Graphics2D has some functions than Graphics
        g2.setColor(Color.black);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose(); // The progrman can works whitout this, but is a good practice
    }


}
