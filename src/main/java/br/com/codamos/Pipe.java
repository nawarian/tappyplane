package br.com.codamos;

import java.awt.*;

public class Pipe extends GameObject {
    public Pipe(Game game, int x, int y, int width, int height) {
        super(game, x, y, width, height);
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
