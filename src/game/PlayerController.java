package game;

import city.cs.engine.SoundClip;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Objects;

/**
 * The class for the player controller.
 */
public class PlayerController implements KeyListener {
    /**
     * The player of the game.
     */
    private Player player;
    /**
     * The timer used to change the player's jump state in the game.
     */
    private Timer timer;
    /**
     * The actual game itself.
     */
    private Game game;

    /**
     * The constructor for the PlayerController class.
     * <p>
     * This method is used to initialise all the variables in the player controller class.
     *
     * @param  player the player being controlled.
     * @param  game the game the player is controlled in.
     */
    public PlayerController(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    /**
     * A method to check if a key is typed.
     * <p>
     * This method is used to listen for when a key is typed.
     *
     * @param  e the key event taking place.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * A method to check if a key is pressed.
     * <p>
     * This method is used to listen for when a key is pressed.
     *
     * @param  e the key event taking place.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // other key commands omitted
        if (code == KeyEvent.VK_LEFT) {
            player.setFacingRight(false);
            player.setMovingLeft(true);
            player.startAccelerationTimer();
        } else if (code == KeyEvent.VK_RIGHT) {
            player.setFacingRight(true);
            player.setMovingRight(true);
            player.startAccelerationTimer();
        } else if (code == KeyEvent.VK_UP) {
            player.jump(17);
            player.startJump();
        } else if (code == KeyEvent.VK_DOWN) {
            if (Objects.equals(player.getLinearVelocity(), new Vec2(0, 0))) { //if player is on the ground and not moving, they will crouch
                player.startCrouch();
            } else if (!player.isJumpOn() && player.getLinearVelocity().x > 0) {
                player.inSpindash();
            }
        } else if (code == KeyEvent.VK_SPACE) {
            player.setSpaceHeld(true);
            if (player.isCrouching() && player.isSpaceHeld()) {
                System.out.println("Crouch");
                player.startSpindash();
                game.getSoundManager().getSpindashChargeSound().loop();
            }
        }
    }

    /**
     * A method to check if a key is released.
     * <p>
     * This method is used to listen for when a key is released.
     *
     * @param  e the key event taking place.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        // other key commands omitted
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT) {
            player.stopAcceleration();
        } else if (code == KeyEvent.VK_UP) {
            if (timer!=null && timer.isRunning()) {
                timer.stop();
            }
            //constantly checks player's position to know when to stop jump animation and declare the player to be not jumping
            timer = new Timer(10, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (Objects.equals(player.getLinearVelocity(), new Vec2(0, 0)) || (Objects.equals(player.getLinearVelocity().y, 0f) && !Objects.equals(player.getLinearVelocity().x, 0f))) {
                        player.stopJump();
                    }
                }
            });
            timer.start();
        } else if (code == KeyEvent.VK_DOWN) {
            player.stopCrouch();
        } else if (code == KeyEvent.VK_SPACE) {
            player.setSpaceHeld(false);
            player.stopSpindash();
            game.getSoundManager().getSpindashChargeSound().stop();
            game.getSoundManager().getSpindashReleaseSound().play();
        }
    }

    public void updatePlayer(Player player) {
        this.player = player;
    }
}
