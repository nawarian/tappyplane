package br.com.codamos;

import java.awt.*;

public class Plane extends GameObject {
    private int initialX;
    private int initialY;

    private double gravity = 0.3f;
    private double velY = 0.0f;

    private Color color = Color.RED;
    private long blinkUntil = 0;

    public Plane(Game game, int x, int y) {
        super(game, x, y, 0, 0);

        this.game = game;
        initialX = x;
        initialY = y;
    }

    public void init() {
        int width = 88;
        int height = 73;

        body = new Rectangle(initialX - width / 2, initialY - height / 2, width, height);
        game.addKeyListener(new InputHandler(game, this));
    }

    @Override
    public void tick(long time) {
        velY += gravity;

        body.y = (int) Math.min(body.y + velY, game.getHeight() - body.height);
        body.y = Math.max(body.y, 0);

        // Check collision
        if (game.rocks.stream().filter(rock -> this.collidesWith(rock)).count() > 0) {
            color = Color.CYAN;
            blinkUntil = time + 500; // 500ms
        }

        if (blinkUntil < time) {
            color = Color.RED;
        }
    }

    private boolean collidesWith(Rock rock) {
        return this.body.intersects(rock.body);
    }

    public void tap() {
        velY = -10;
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(body.x, body.y, body.width, body.height);
    }

    @Override
    public void destroy() {
    }
}
