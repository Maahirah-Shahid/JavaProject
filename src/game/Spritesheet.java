package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class for the spritesheet cutter used in the code.
 */
public class Spritesheet extends JPanel {
    private BufferedImage spriteSheet;
    private BufferedImage sprite;

    public Spritesheet() {
        try {
            // Load the sprite sheet
            spriteSheet = ImageIO.read(new File("data/Objects.png"));

            // Define the sub-image (x, y, width, height)
            int x = 62, y = 250;   // Top-left corner of the sprite
            int width = 30, height = 35; // Width and height of the sprite

            // Extract the sprite from the sprite sheet
            sprite = spriteSheet.getSubimage(x, y, width, height);

            File outputfile = new File("RingBox.png");
            ImageIO.write(sprite, "png", outputfile);

            // Print the file path for reference
            System.out.println("File saved at: " + outputfile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Here you would attach the sprite to a box using the BodyImage class.
        // You can use the file "extracted_sprite.png" which is the sprite you've just saved.

        // The actual code to add the sprite to the box would depend on the specifics
        // of the `BodyImage` class and the framework you're using.
    }

    public BufferedImage getSprite() {
        return sprite;
    }
}
