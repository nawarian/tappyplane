package br.com.codamos;

import java.awt.*;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class MenuScene extends AbstractScene {
    public MenuScene(Game game) {
        super(game);
    }

    @Override
    public void init() {
        game.resetKeyListeners();

        // Attach just the Menu listener
        game.addKeyListener(new MenuInputHandler(game));
    }

    @Override
    public void tick(long time) {
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Press ENTER to start!", body.width / 2 - 50, body.height / 2);
    }

    @Override
    public void destroy() {
    }
}
