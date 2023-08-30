package br.com.codamos;

import java.awt.*;

public class Score extends GameObject {
    public int total = 0;

    public Score(Game game, int x, int y, int width, int height) {
        super(game, x, y, width, height);
    }

    @Override
    public void init() {
    }

    @Override
    public void tick(long time) {
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Score: " + total, 10, 20);
    }

    @Override
    public void destroy() {
    }
}
