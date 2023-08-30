package br.com.codamos;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Canvas implements Runnable {
    private JFrame window;
    private Random random;
    private int currentFPS = 0;
    public BufferedImage spritesheet;

    // Game modifiers
    public State state = State.Running;
    public int scrollSpeed = 1;
    public boolean debug = true;

    // Game objects
    public List<GameObject> objects;
    public List<Rock> rocks;
    public Score score;

    public Game() {
        Dimension d = new Dimension(320, 600);
        this.setSize(d);
        this.setPreferredSize(d);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
        this.setVisible(true);
        this.requestFocus();

        random = new Random();
    }

    @Override
    public void run() {
        try {
            init();
        } catch (IOException e) {
            System.out.println("Could not load game assets: " + e.getMessage());
        }

        long now = System.currentTimeMillis();
        long lastFrame = 0;
        long timePerFrame = 1000 / 120; // 1s / 120fps
        long lastFpsCheck = 0;
        int frames = 0;
        while (!state.equals(State.Shutdown)) {
            now = System.currentTimeMillis();
            if (now - lastFrame < timePerFrame) {
                continue;
            }

            if (state.equals(State.Paused)) {
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

    public void init() throws IOException {
        // Window
        window = new JFrame("Tappy Plane");
        window.setVisible(true);
        window.add(this);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.requestFocus();

        state = State.Running;

        // Game assets
        File f = new File("./resources/tappyplane/Spritesheet/sheet.png");
        spritesheet = ImageIO.read(f);

        // Game objects
        objects = new ArrayList<>();
        objects.add(new Sky(this));
        objects.add(new Floor(this));
        objects.add(new Plane(this, window.getWidth() / 4, window.getHeight() / 2));

        rocks = new ArrayList<>();
        rocks.add(new Rock(this, (int) (this.getWidth() * 1.5f)));

        score = new Score(this, 0, 0, getWidth(), getHeight());
        score.init();

        objects.forEach(GameObject::init);
    }

    public void tick(long time) {
        objects.forEach(obj -> obj.tick(time));
        rocks.forEach(obj -> obj.tick(time));
        score.tick(time);

        // Cleanup rocks
        List<Rock> rocksToBeRemoved = rocks.stream().filter(obj -> obj instanceof Rock && obj.body.x + 110 < 0).toList();
        rocks.removeAll(rocksToBeRemoved);
        score.total += rocksToBeRemoved.size();

        // Build new rocks
        if (rocks.size() < 2) {
            Rock lastRock = rocks.get(rocks.size() - 1);
            float distance = random.nextFloat(4.5f, 5.5f);
            rocks.add(new Rock(this, (int) (lastRock.body.x + lastRock.body.width * distance)));
        }

        // Increase difficulty
        if (score.total > 0 && score.total <= 15) {
            scrollSpeed = 1;
        } else if (score.total > 15 && score.total <= 35) {
            scrollSpeed = 2;
        } else if (score.total > 35 && score.total <= 70) {
            scrollSpeed = 3;
        } else if (score.total > 70 && score.total <= 90) {
            scrollSpeed = 4;
        } else if (score.total > 90) {
            scrollSpeed = 5;
        } else {
            scrollSpeed = 1;
        }
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
        rocks.forEach(obj -> obj.render(g));
        score.render(g);

        g.dispose();
        bs.show();
    }

    public boolean isPaused() {
        return state.compareTo(State.Paused) == 0;
    }

    public void pause() {
        if (state.compareTo(State.Running) == 0) {
            state = State.Paused;
        }
    }

    public void resume() {
        if (state.compareTo(State.Paused) == 0) {
            state = State.Running;
        }
    }

    public void stopGame() {
        state = State.Shutdown;
    }

    public void teardown() {
        objects.forEach(GameObject::destroy);
        rocks.forEach(GameObject::destroy);
        score.destroy();

        window.dispose();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }

    public enum State {
        Menu,
        Running,
        Paused,
        Shutdown,
    }
}
