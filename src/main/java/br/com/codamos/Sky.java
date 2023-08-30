package br.com.codamos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sky extends GameObject {
    private Random random;
    private List<Cloud> clouds;

    public Sky(Game game) {
        super(game, 0, 0, game.getWidth(), game.getHeight());
    }

    @Override
    public void init() {
        random = new Random();

        clouds = new ArrayList<>();
        for (int i = 0; i < 8; ++i) {
            clouds.add(new Cloud(game, random));
        }
    }

    @Override
    public void tick(long time) {
        clouds.forEach(c -> {
            c.tick(time);

            // Destroy cloud and build new one
            if (c.body.x + c.body.width < 0) {
                c.body.x = game.getWidth();
            }
        });
    }

    @Override
    public void render(Graphics g) {
        clouds.forEach(c -> c.render(g));
    }

    @Override
    public void destroy() {
        clouds.forEach(Cloud::destroy);
    }

    private class Cloud extends GameObject {
        public Cloud(Game game, Random r) {
            super(game, r.nextInt(0, game.getWidth()), r.nextInt(0, (int) (game.getHeight() * 0.25)), (int) (game.getWidth() * 0.05), (int) (game.getHeight() * 0.05));
        }

        @Override
        public void init() {
        }

        @Override
        public void tick(long time) {
            this.body.x -= game.scrollSpeed;
        }

        @Override
        public void render(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillRect(body.x, body.y, body.width, body.height);

            g.setColor(Color.CYAN);
            g.drawRect(body.x, body.y, body.width, body.height);
        }

        @Override
        public void destroy() {
        }
    }
}
