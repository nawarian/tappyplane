package br.com.codamos;

public abstract class AbstractScene extends GameObject {
    public AbstractScene(Game game) {
        super(game, 0, 0, game.getWidth(), game.getHeight());
    }
}
