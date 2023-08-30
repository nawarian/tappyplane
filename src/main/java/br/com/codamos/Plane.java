package br.com.codamos;

import java.awt.*;

public class Plane {
    private Game game;
    private int initialX;
    private int initialY;
    private Rectangle body;

    private double gravity = 0.3f;
    private double velY = 0.0f;

    public Plane(Game game, int x, int y) {
        this.game = game;
        initialX = x;
        initialY = y;
    }

    public void init() {
        int width = 88;
        int height = 73;

        body = new Rectangle(initialX - width / 2, initialY - height / 2, width, height);
    }

    public void tick() {
        velY += gravity;

        body.y = (int) Math.min(body.y + velY, game.getHeight() - body.height);
        body.y = Math.max(body.y, 0);
    }

    public void tap() {
        velY = -10;
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(body.x, body.y, body.width, body.height);
    }

    public void teardown() {
    }
}
