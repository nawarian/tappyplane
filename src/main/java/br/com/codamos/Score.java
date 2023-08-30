package br.com.codamos;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class Score extends GameObject {
    private BufferedImage[] numbers;
    public int total = 0;

    public Score(Game game, int x, int y, int width, int height) {
        super(game, x, y, width, height);
    }

    @Override
    public void init() {
        numbers = new BufferedImage[]{
                game.spritesheet.getSubimage(432, 1743, 53, 78),
                game.spritesheet.getSubimage(512, 1093, 37, 76),
                game.spritesheet.getSubimage(477, 1350, 51, 77),
                game.spritesheet.getSubimage(485, 1679, 51, 78),
                game.spritesheet.getSubimage(432, 1537, 55, 76),
                game.spritesheet.getSubimage(485, 1823, 50, 76),
                game.spritesheet.getSubimage(432, 1885, 53, 77),
                game.spritesheet.getSubimage(478, 1173, 51, 76),
                game.spritesheet.getSubimage(461, 899, 51, 78),
                game.spritesheet.getSubimage(458, 1962, 53, 77),
        };
    }

    @Override
    public void tick(long time) {
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform original = g2d.getTransform();

        AffineTransform at = new AffineTransform();
        at.scale(0.5f, 0.5f);
        g2d.transform(at);

        int offset;
        List<Integer> digits = Arrays
                .stream(String.format("%03d", total).split(""))
                .map(Integer::parseInt)
                .toList();
        for (int i = 0; i < digits.size(); i++) {
            int n = digits.get(i);

            offset = i * 53;
            g.drawImage(numbers[n], 0 + offset, 0, numbers[n].getWidth(), numbers[n].getHeight(), null);
        }

        g2d.setTransform(original);
    }

    @Override
    public void destroy() {
    }
}
