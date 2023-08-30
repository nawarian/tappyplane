package br.com.codamos;

import java.awt.*;
import java.util.Random;

public class Rock extends GameObject {
    private final int width = 108;
    private final int height = 239;

    public Rock(Game game, int x) {
        super(game, x, 0, 0, 0);

        body.width = width;
        body.height = height;

        // Top or bottom pipe
        Random r = new Random();
        if (r.nextBoolean()) {
            body.y = game.getHeight() - body.height;
        }
    }

    @Override
    public void init() {
    }

    @Override
    public void tick(long time) {
        body.x--;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(16, 204, 65));
        g.fillRect(body.x, body.y, body.width, body.height);

        g.setColor(new Color(18, 108, 39));
        g.drawRect(body.x, body.y, body.width, body.height);
    }

    @Override
    public void destroy() {
    }
}
