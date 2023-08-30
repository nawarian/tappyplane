package br.com.codamos;

import java.awt.*;

public abstract class GameObject {
    protected Game game;
    protected Rectangle body;

    public GameObject(Game game, int x, int y, int width, int height) {
        this.game = game;
        this.body = new Rectangle(x, y, width, height);
    }

    public abstract void init();
    public abstract void tick(long time);
    public abstract void render(Graphics g);
    public abstract void destroy();
}
