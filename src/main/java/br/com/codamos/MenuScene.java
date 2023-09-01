package br.com.codamos;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class MenuScene extends AbstractScene {
    private BufferedImage background;
    private Map<Character, BufferedImage> alphabet;

    public MenuScene(Game game) {
        super(game);
    }

    @Override
    public void init() {
        game.resetKeyListeners();

        // Attach just the Menu listener
        game.addKeyListener(new MenuInputHandler(game));

        // Load assets from spritesheet
        BufferedImage sheet = game.spritesheet;
        background = sheet.getSubimage(0, 355, 800, 480);

        alphabet = new HashMap<>();
	    alphabet.put('A', sheet.getSubimage(412, 835, 61,64));
	    alphabet.put('B', sheet.getSubimage(487, 1537, 50,66));
	    alphabet.put('C', sheet.getSubimage(460, 977, 52,66));
	    alphabet.put('D', sheet.getSubimage(432, 1613, 54,66));
	    alphabet.put('E', sheet.getSubimage(511, 1965, 45,64));
	    alphabet.put('F', sheet.getSubimage(512, 963, 44,64));
	    alphabet.put('G', sheet.getSubimage(460, 1107, 52,66));
	    alphabet.put('H', sheet.getSubimage(473, 835, 51,64));
	    alphabet.put('I', sheet.getSubimage(524, 835, 22,64));
	    alphabet.put('J', sheet.getSubimage(512, 1027, 42,66));
	    alphabet.put('K', sheet.getSubimage(432, 1821, 53,64));
	    alphabet.put('L', sheet.getSubimage(512, 899, 44,64));
	    alphabet.put('M', sheet.getSubimage(392, 1967, 66,64));
	    alphabet.put('N', sheet.getSubimage(432, 1679, 53,64));
	    alphabet.put('O', sheet.getSubimage(418, 1284, 60,66));
	    alphabet.put('P', sheet.getSubimage(489, 1427, 48,65));
	    alphabet.put('Q', sheet.getSubimage(418, 1205, 60,79));
	    alphabet.put('R', sheet.getSubimage(478, 1249, 51,65));
	    alphabet.put('S', sheet.getSubimage(511, 1899, 46,66));
	    alphabet.put('T', sheet.getSubimage(460, 1043, 52,64));
	    alphabet.put('U', sheet.getSubimage(485, 1757, 51,66));
	    alphabet.put('V', sheet.getSubimage(400, 913, 61,64));
	    alphabet.put('W', sheet.getSubimage(136, 1320, 76,64));
	    alphabet.put('X', sheet.getSubimage(418, 1409, 58,64));
	    alphabet.put('Y', sheet.getSubimage(432, 1473, 57,64));
	    alphabet.put('Z', sheet.getSubimage(486, 1613, 50,64));
    }

    @Override
    public void tick(long time) {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background, 0, 0, body.width, body.height, null);

        drawText(g, "Press", 30, body.height / 2 - 100);
        drawText(g, "SPACE", 30, body.height / 2 + 50);

        String authorLine = "Tappy plane – by Nawarian (codamos.com.br)";
        int authorLineWidth = g.getFontMetrics().stringWidth(authorLine);
        g.drawString(authorLine, body.width / 2 - authorLineWidth / 2, body.height - 30);

        g.setFont(g.getFont().deriveFont(12.0f));
        String visitGithubLine = "Visit 👉 https://github.com/nawarian/tappyplane";
        int visitGithubLineWidth = g.getFontMetrics().stringWidth(visitGithubLine);
        g.drawString(visitGithubLine, body.width / 2 - visitGithubLineWidth / 2, body.height - 10);
    }

    private void drawText(Graphics g, String input, int x, int y) {
        input = input.toUpperCase();

        BufferedImage tmp;
        BufferedImage[] text = new BufferedImage[input.length()];
        for (int i = 0; i < input.split("").length; i++) {
            tmp = alphabet.get(input.charAt(i));
            if (tmp != null) {
                text[i] = tmp;
            }
        }

        int widthDrawn = 0;
        for (int i = 0; i < text.length; i++) {
            tmp = text[i];
            if (tmp == null) {
                continue;
            }

            g.drawImage(tmp, x + widthDrawn, y, 54, tmp.getHeight(), null);
            widthDrawn += 54;
        }
    }

    @Override
    public void destroy() {
    }
}
