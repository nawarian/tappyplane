package br.com.codamos;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements KeyListener, MouseListener {
    private Game game;
    private Plane plane;

    public InputHandler(Game game, Plane plane) {
        this.game = game;
        this.plane = plane;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (game.isPaused()) {
                game.resume();
            }

            if (!game.isPaused()) {
                plane.tap();
            }

            if (game.isGameOver()) {
                game.reset();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            game.reset();
            game.stopGame();
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {
            if (!game.isPaused()) {
                game.pause();
            } else {
                game.resume();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
