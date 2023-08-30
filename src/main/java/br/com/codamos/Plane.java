package br.com.codamos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Plane extends GameObject {
    private BufferedImage[] planes;
    private int currentFrame = 0;
    private long lastFrame = 0;
    private int initialX;
    private int initialY;

    private double gravity = 0.3f;
    private double velY = 0.0f;

    private Color color = Color.RED;
    private long blinkUntil = 0;

    public Plane(Game game, int x, int y) {
        super(game, x, y, 0, 0);

        this.game = game;
        initialX = x;
        initialY = y;
    }

    public void init() {
        int width = 88;
        int height = 73;

        planes = new BufferedImage[] {
                game.spritesheet.getSubimage(216, 1878, 88, 73),
                game.spritesheet.getSubimage(372, 1059, 88, 73),
                game.spritesheet.getSubimage(372, 986, 88, 73),
        };

        body = new Rectangle(initialX - width / 2, initialY - height / 2, width, height);
        game.addKeyListener(new InputHandler(game, this));
    }

    @Override
    public void tick(long time) {
        float animationSpeed = 50f / game.scrollSpeed;
        if (time - lastFrame >= animationSpeed) {
            currentFrame++;
            lastFrame = time;
        }

        velY += gravity;

        body.y = (int) Math.min(body.y + velY, game.getHeight() - body.height);
        body.y = Math.max(body.y, 0);

        // Check collision
        if (game.rocks.stream().filter(rock -> this.collidesWith(rock)).count() > 0) {
            color = Color.CYAN;
            blinkUntil = time + 500; // 500ms
        }

        // Check collision with floor (fall)
        Floor f = (Floor) game.objects.stream().filter(obj -> obj instanceof Floor).findFirst().orElseThrow();
        if (this.body.y + this.body.height >= f.body.y) {
            color = Color.BLUE;
            blinkUntil = time + 500; // 500ms
        }

        if (blinkUntil < time) {
            color = Color.RED;
        }

        if (currentFrame > planes.length - 1) {
            currentFrame = 0;
        }
    }

    private boolean collidesWith(Rock rock) {
        return this.body.intersects(rock.body);
    }

    public void tap() {
        velY = -10;
    }

    public void render(Graphics g) {
        g.drawImage(planes[currentFrame], body.x, body.y, body.width, body.height, null);

        if (game.debug) {
            g.setColor(color);
            g.drawRect(body.x, body.y, body.width, body.height);
        }
    }

    @Override
    public void destroy() {
    }
}
