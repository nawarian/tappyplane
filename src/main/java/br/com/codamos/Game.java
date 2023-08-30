package br.com.codamos;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
    private JFrame window;
    public boolean running = false;
    private int currentFPS = 0;

    // Game objects
    public Plane plane;

    public Game() {
        Dimension d = new Dimension(800, 600);
        this.setSize(d);
        this.setPreferredSize(d);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
        this.setVisible(true);
    }

    @Override
    public void run() {
        init();

        long now = System.currentTimeMillis();
        long lastFrame = 0;
        long timePerFrame = 1000 / 120; // 1s / 120fps
        long lastFpsCheck = 0;
        int frames = 0;
        while (running) {
            now = System.currentTimeMillis();
            if (now - lastFrame < timePerFrame) {
                continue;
            }

            tick();
            render();
            frames++;

            if (now - lastFpsCheck >= 1000) {
                lastFpsCheck = now;
                currentFPS = frames;
                frames = 0;
            }

            lastFrame = now;
        }

        teardown();
    }

    public void init() {
        window = new JFrame("Tappy Plane");
        window.setVisible(true);
        window.add(this);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        this.requestFocus();

        running = true;

        // Game objects
        plane = new Plane(this, window.getWidth() / 4, window.getHeight() / 2);
        plane.init();

        this.addKeyListener(new InputHandler(this));
    }

    public void tick() {
        plane.tick();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        // Clear screen
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // render game objects
        plane.render(g);

        g.dispose();
        bs.show();
    }

    public void teardown() {
        plane.teardown();

        window.dispose();
    }

    public static void main(String[] argss) {
        Game game = new Game();
        game.run();
    }
}
