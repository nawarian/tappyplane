package br.com.codamos;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Floor extends GameObject {
    private List<GameObject> recs;

    public Floor(Game game, int canvasWidth, int canvasHeight) {
        super(game, 0, (int) (canvasHeight * 0.8), canvasWidth, 32);
    }

    @Override
    public void init() {
        recs = new ArrayList<>();

        boolean up = false;
        for (int i = 0; i < 14; ++i) {
            up = !up;
            recs.add(new FloorRect(
                    game,
                    i * 16,
                    this.body.y + (up ? 0 : 16),
                    16, 16));
        }

        for (int i = 0; i < 15; ++i) {
            up = !up;
            recs.add(new FloorRect(
                    game,
                    (i * 16) + (game.getWidth() / 2),
                    this.body.y + (up ? 16 : 0),
                    16, 16));
        }
    }

    @Override
    public void tick(long time) {
        recs.forEach(r -> r.tick(time));
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(body.x, body.y, body.width, body.height);

        recs.forEach(r -> r.render(g));
    }

    @Override
    public void destroy() {
    }

    private class FloorRect extends GameObject {
        public FloorRect(Game game, int x, int y, int width, int height) {
            super(game, x, y, width, height);
        }

        @Override
        public void init() {
        }

        @Override
        public void tick(long time) {
            body.x--;

            if ((body.x + body.width) < 0) {
                body.x = game.getWidth();
            }
        }

        @Override
        public void render(Graphics g) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(body.x, body.y, body.width, body.height);
        }

        @Override
        public void destroy() {
        }
    }
}
