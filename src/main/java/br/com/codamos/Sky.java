package br.com.codamos;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sky extends GameObject {
    private BufferedImage background;

    public Sky(Game game) {
        super(game, 0, 0, game.getWidth(), game.getHeight());
    }

    @Override
    public void init() {
        background = game.spritesheet.getSubimage(0, 355, 800, 480);
    }

    @Override
    public void tick(long time) {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background, 0, 0, body.width, body.height, null);
    }

    @Override
    public void destroy() {
    }
}
