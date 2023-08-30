package br.com.codamos;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Floor extends GameObject {
    private BufferedImage floorImage;
    private int offset = 0;
    private int offset2 = 0;

    public Floor(Game game) {
        super(game, 0, game.getHeight() - 71, 808, 71);
    }

    @Override
    public void init() {
        floorImage = game.spritesheet.getSubimage(0, 0, 808, 71);

        offset = 0;
        offset2 = body.width;
    }

    @Override
    public void tick(long time) {
        if (offset + body.width < 0) {
            offset = offset2;
            offset2 = offset + body.width;
        }

        offset -= game.scrollSpeed;
        offset2 -= game.scrollSpeed;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(floorImage, offset, game.getHeight() - body.height, body.width, body.height, null);
        g.drawImage(floorImage, offset2, game.getHeight() - body.height, body.width, body.height, null);

        if (game.debug) {
            g.setColor(Color.PINK);
            g.drawRect(body.x, body.y, body.width, body.height);
        }
    }

    @Override
    public void destroy() {
    }
}
