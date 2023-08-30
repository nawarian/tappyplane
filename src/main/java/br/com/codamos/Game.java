package br.com.codamos;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class Game extends Canvas implements Runnable {
    private JFrame window;
    public boolean running = false;
    private int currentFPS = 0;

    // Game objects
    public List<GameObject> objects;

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

            tick(now);
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
        objects = new ArrayList<>();
        objects.add(new Floor(this, window.getWidth(), window.getHeight()));
        objects.add(new Sky(this));
        objects.add(new Plane(this, window.getWidth() / 4, window.getHeight() / 2));

        objects.forEach(GameObject::init);
    }

    public void tick(long time) {
        objects.forEach(obj -> obj.tick(time));
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        // Clear screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // render game objects
        objects.forEach(obj -> obj.render(g));

        g.dispose();
        bs.show();
    }

    public void teardown() {
        objects.forEach(GameObject::destroy);

        window.dispose();
    }

    public static void main(String[] argss) {
        Game game = new Game();
        game.run();
    }
}
