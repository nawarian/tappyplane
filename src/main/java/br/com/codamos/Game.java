package br.com.codamos;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game extends Canvas implements Runnable {
    private JFrame window;
    private int currentFPS = 0;
    public BufferedImage spritesheet;

    // Game modifiers
    public AbstractScene scene;
    public State state = State.Running;
    public boolean debug = false;

    public Game() {
        Dimension d = new Dimension(320, 600);
        this.setSize(d);
        this.setPreferredSize(d);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
        this.setVisible(true);
        this.requestFocus();
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

            if (isPaused()) {
                continue;
            }

            if (!isGameOver()) {
                tick(now);
            }

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
        window.setUndecorated(true);
        window.setVisible(true);
        window.add(this);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.requestFocus();
        window.setLocationRelativeTo(null);

        // Game assets
        File f = new File("./resources/tappyplane/Spritesheet/sheet.png");
        spritesheet = ImageIO.read(f);

        mainMenu();
    }

    public void resetKeyListeners() {
        // Clean all listeners
        KeyListener[] keyListeners = getKeyListeners();
        for (int i = 0; i < keyListeners.length; i++) {
            removeKeyListener(keyListeners[i]);
        }
    }

    private void mainMenu() {
        state = State.Menu;
        scene = new MenuScene(this);
        scene.init();
    }

    public void reset() {
        scene = new GameScene(this);
        scene.init();
        state = State.Running;
    }

    public void tick(long time) {
        scene.tick(time);
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

        // Render scene
        scene.render(g);

        g.dispose();
        bs.show();
    }

    public boolean isPaused() {
        return state.equals(State.Paused);
    }

    public void start() {
        reset();
    }

    public void pause() {
        if (state.equals(State.Running)) {
            state = State.Paused;
        }
    }

    public void resume() {
        if (state.equals(State.Paused)) {
            state = State.Running;
        }
    }

    public void stopGame() {
        state = State.Shutdown;
    }

    public void gameOver() {
        state = State.GameOver;
    }

    public boolean isGameOver() {
        return state.equals(State.GameOver);
    }

    public void teardown() {
        scene.destroy();

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
        GameOver,
        Shutdown,
    }
}
