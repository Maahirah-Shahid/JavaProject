package game;

import city.cs.engine.UserView;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * The class that allows the user view of the game.
 */
public class GameView extends UserView {

    /**
     * The background of the game.
     */
    private Image background;
    /**
     * The player of the game.
     */
    private Player player;
    /**
     * The actual game.
     */
    private Game game;
    /**
     * The font used for everything written in the game.
     */
    private Font sonicFont;

    /**
     * The constructor for game view.
     * <p>
     * This method is used to initialise all the variables of game view.
     *
     * @param  world the world the user is viewing.
     * @param  width the width of the view.
     * @param  height the height of the view.
     * @param  player the player of the view.
     * @param  game the game of the view.
     */
    public GameView(GameLevel world, int width, int height, Player player, Game game) {
        super(world, width, height);
        background = new ImageIcon("data/SunsetHillGif.gif").getImage();
        this.player = player;
        this.game = game;
        try {
            sonicFont = Font.createFont(Font.TRUETYPE_FONT, new File("data/Font/sonic-advance-3.ttf")).deriveFont(18f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(sonicFont); // Optional: register globally
        } catch (IOException | FontFormatException e) {
            System.out.println("Failed to load font: " + e);
            sonicFont = new Font("SansSerif", Font.BOLD, 18); // Fallback
        }
    }

    /**
     * The method that paints the background of the view.
     * <p>
     * This method is used to paint anything you want to add to the background of the game.
     *
     * @param  g the graphics variable used to add to the background.
     */
    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(game.getLevel().getBackground(), 0, 0, this);
        g.setFont(sonicFont);
        g.setColor(Color.white);
        g.drawString("Rings: "+player.getRings(), 10, 30);
        g.drawString("Lives: "+player.getLives(), 10, 50);
        g.drawString("Score: "+player.getScore(), 10, 70);
        //has the screen follow the player when they move and jump too high
        if (player.getPosition().y>-3) {
            this.setView(new Vec2(player.getPosition().x, player.getPosition().y), 20);
        } else {
            this.setView(new Vec2(player.getPosition().x, -3), 20);
        }
    }

    /**
     * The method to update the player.
     * <p>
     * This method is used to update the player into the one you pass into it.
     *
     * @param  player the player needed to be updated.
     */
    public void updatePlayer(Player player) {
        this.player = player;
    }
}
