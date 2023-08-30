package br.com.codamos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Plane extends GameObject {
    private BufferedImage[] planes;
    private int currentFrame = 0;
    private long lastFrame = 0;

    private double gravity = 0.2f;
    private double velY = 0.0f;

    public Plane(Game game, int x, int y) {
        super(game, x, y, 72, 55);

        this.game = game;
    }

    public void init() {
        planes = new BufferedImage[]{
                game.spritesheet.getSubimage(216, 1878, 88, 73),
                game.spritesheet.getSubimage(372, 1059, 88, 73),
                game.spritesheet.getSubimage(372, 986, 88, 73),
        };

        game.addKeyListener(new InGameInputHandler(game, this));
    }

    @Override
    public void tick(long time) {
        GameScene scene = (GameScene) game.scene;
        float animationSpeed = 50f / scene.scrollSpeed;
        if (time - lastFrame >= animationSpeed) {
            currentFrame++;
            lastFrame = time;
        }

        if (scene.scrollSpeed > 3) {
            gravity = 0.4f;
        }

        velY += gravity;

        body.y = (int) Math.min(body.y + velY, game.getHeight() - body.height);
        body.y = Math.max(body.y, 0);

        // Check collision
        if (scene.rocks.stream().filter(rock -> this.collidesWith(rock)).count() > 0) {
            game.gameOver();
        }

        // Check collision with floor (fall)
        Floor f = scene.floor;
        if (this.body.y + this.body.height >= f.body.y) {
            game.gameOver();
        }

        if (currentFrame > planes.length - 1) {
            currentFrame = 0;
        }
    }

    private boolean collidesWith(Rock rock) {
        return this.body.intersects(rock.body);
    }

    public void tap() {
        velY = -5;
    }

    public void render(Graphics g) {
        BufferedImage frame = planes[currentFrame];
        g.drawImage(frame, body.x - 5, body.y - 5, frame.getWidth(), frame.getHeight(), null);

        if (game.debug) {
            g.setColor(Color.RED);
            g.drawRect(body.x, body.y, body.width, body.height);
        }
    }

    @Override
    public void destroy() {
    }
}
