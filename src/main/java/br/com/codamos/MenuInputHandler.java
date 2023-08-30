package br.com.codamos;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuInputHandler implements KeyListener {
    private Game game;

    public MenuInputHandler(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            game.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
