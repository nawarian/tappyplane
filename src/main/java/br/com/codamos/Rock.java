package br.com.codamos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Rock extends GameObject {
    private BufferedImage rockTop;
    private BufferedImage rockBottom;

    public Rock(Game game, int x) {
        super(game, x, 0, 45, 239);

        // Top or bottom pipe
        Random r = new Random();
        if (r.nextBoolean()) {
            body.y = game.getHeight() - body.height;
        }

        init();
    }

    @Override
    public void init() {
        rockTop = game.spritesheet.getSubimage(264, 986, 108, 239);
        rockBottom = game.spritesheet.getSubimage(0, 1757, 108, 239);
    }

    @Override
    public void tick(long time) {
        body.x -= game.scrollSpeed;
    }

    @Override
    public void render(Graphics g) {
        BufferedImage sprite = body.y == 0 ? rockTop : rockBottom;

        int centerX = (body.x + body.width) - body.width / 2;
        centerX -= sprite.getWidth() / 2;
        g.drawImage(sprite, centerX, body.y, 108, 239, null);

        if (game.debug) {
            g.setColor(new Color(36, 229, 76));
            g.drawRect(body.x, body.y, body.width, body.height);
        }
    }

    @Override
    public void destroy() {
    }
}
