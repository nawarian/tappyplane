package br.com.codamos;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScene extends AbstractScene {
    private Random random;

    public Sky sky;
    public Floor floor;
    public Plane plane;
    public Score score;
    public List<Rock> rocks;
    public float scrollSpeed = 1;

    public GameScene(Game game) {
        super(game);
        random = new Random();
    }

    @Override
    public void init() {
        sky = new Sky(game);
        sky.init();

        floor = new Floor(game);
        floor.init();

        plane = new Plane(game, body.width / 4, body.height / 4);
        plane.init();

        score = new Score(game, 0, 0, body.width, body.height);
        score.init();

        rocks = new ArrayList<>();
        rocks.add(new Rock(game, (int) (body.width * 1.5f)));

        game.resetKeyListeners();
        game.addKeyListener(new InGameInputHandler(game, plane));
    }

    @Override
    public void tick(long time) {
        if (rocks == null) {
            return;
        }

        sky.tick(time);
        floor.tick(time);
        rocks.forEach(obj -> obj.tick(time));
        plane.tick(time);
        score.tick(time);

        // Cleanup rocks
        List<Rock> rocksToBeRemoved = rocks.stream().filter(obj -> obj instanceof Rock && obj.body.x + 110 < 0).toList();
        rocks.removeAll(rocksToBeRemoved);
        score.total += rocksToBeRemoved.size();

        // Build new rocks
        if (rocks.size() < 2) {
            Rock lastRock = rocks.get(rocks.size() - 1);
            float distance = random.nextFloat(4.5f, 5.5f);
            rocks.add(new Rock(game, (int) (lastRock.body.x + lastRock.body.width * distance)));
        }

        // Increase difficulty
        if (score.total > 0 && score.total <= 15) {
            scrollSpeed = 1;
        } else if (score.total > 15 && score.total <= 35) {
            scrollSpeed = 2;
        } else if (score.total > 35 && score.total <= 70) {
            scrollSpeed = 3;
        } else if (score.total > 70 && score.total <= 90) {
            scrollSpeed = 4;
        } else if (score.total > 90) {
            scrollSpeed = 5;
        } else {
            scrollSpeed = 1;
        }
    }

    @Override
    public void render(Graphics g) {
        sky.render(g);
        floor.render(g);
        plane.render(g);
        rocks.forEach(obj -> obj.render(g));

        score.render(g);

        if (game.isGameOver()) {
            BufferedImage gameOver = game.spritesheet.getSubimage(0, 835, 412, 78);
            float scale = 0.75f;
            int gameOverWidth = (int) (gameOver.getWidth() * scale);
            int gameOverHeight = (int) (gameOver.getHeight() * scale);
            int gameOverOffset = body.width - gameOverWidth;
            g.drawImage(gameOver, gameOverOffset / 2, body.height / 2 - gameOverHeight / 2, gameOverWidth, gameOverHeight, null);
        }
    }

    @Override
    public void destroy() {
        sky.destroy();
        floor.destroy();
        plane.destroy();
        rocks.forEach(GameObject::destroy);
    }
}
