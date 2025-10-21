package game;

import city.cs.engine.*;
import city.cs.engine.BoxShape;
import city.cs.engine.Shape;
import city.cs.engine.SoundClip;
import city.cs.engine.StaticBody;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The class for the second level of the game.
 */
public class Level2 extends GameLevel{

    /**
     * The background of the second level of the game.
     */
    private Image background;
    /**
     * The level's background music.
     */
    private SoundClip levelSound;
    /**
     * The player of the game.
     */
    private Player player;
    /**
     * The boolean declaring the level as complete or not.
     */
    private Boolean complete = false;
    /**
     * The image for the ground in the second level of the game.
     */
    private BodyImage groundImage = new BodyImage("data/TwinkleSnowPlatforms/tsz/2.png",2);
    /**
     * The image for the underground in the second level of the game.
     */
    private BodyImage underGroundImage = new BodyImage("data/TwinkleSnowPlatforms/tsz/34.png",2);

    /**
     * The constructor for the level 2 class.
     * <p>
     * This method is used to initialise all the variables in level 2.
     *
     * @param  game the game in which level 2 takes place.
     */
    public Level2(Game game) {
        super(game);
        background = new ImageIcon("data/TwinkleSnowGif.gif").getImage();
        player = this.getPlayer();
        this.levelSound = null;
        try {
            levelSound = new SoundClip("data/SoundEffects/TwinkleSnowTheme.wav");   // Open an audio input stream
            levelSound.loop();                              // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println("Error:" + e);
        }

        player.setPosition(new Vec2(-8,10));
        SquidBadnik squidBadnik = new SquidBadnik(this, -2, 17, player);

        makeGround(0,9,20,0.5f,-18);
        StaticBody ground = new StaticBody(this);
        ground.setPosition(new Vec2(0,9));
        for (int i=0;i<10;i++) {
            new AttachedImage(ground, underGroundImage, 2, 0, new Vec2(-18+(i*4),-5.45f));
            new AttachedImage(ground, underGroundImage, 2, 0, new Vec2(-18+(i*4),-9.45f));
        }
        for (int i=0;i<23;i++) {
            new AttachedImage(ground, underGroundImage, 2, 0, new Vec2(-18+(i*4),-12.1f));
            new AttachedImage(ground, underGroundImage, 2, 0, new Vec2(-18+(i*4),-13.45f));
            new AttachedImage(ground, underGroundImage, 2, 0, new Vec2(-18+(i*4),-17.45f));
        }
        for (int i=0;i<3;i++) {
            new AttachedImage(ground, underGroundImage, 2, 0, new Vec2(28.1f+(i*10),-5.45f));
            new AttachedImage(ground, underGroundImage, 2, 0, new Vec2(28.1f+(i*10),-9.45f));
        }
        for (int i=0;i<4;i++) {
            new AttachedImage(ground, underGroundImage, 2, 0, new Vec2(58.1f+(i*4),-9.45f));
        }


        //add spikes
        Spikes spike1 = new Spikes(this,21.5f,0f);
        Spikes spike2 = new Spikes(this,24.5f,0f);
        Spikes spike3 = new Spikes(this,31.5f,0f);
        Spikes spike4 = new Spikes(this,34.5f,0f);
        Spikes spike5 = new Spikes(this,41.5f,0f);
        Spikes spike6 = new Spikes(this,44.5f,0f);
        Spikes spike7 = new Spikes(this,51.5f,0f);
        Spikes spike8 = new Spikes(this,54.5f,0f);

        makeGround(28.1f,9,2,0.5f,0);
        makeGround(38.1f,9,2,0.5f,0);
        makeGround(48.1f,9,2,0.5f,0);
        makeGround(64.1f,5,8,0.5f,-6);

        //add death pit
        DeathGround deathGround = new DeathGround(this,30,0);

        //add rings
        Rings ring1 = new Rings(this, -4,10);
        Rings ring2 = new Rings(this, -2,10);
        Rings ring3 = new Rings(this, 0,10);

        EndRings endRings = new EndRings(this,65,13);

    }


    /**
     * The method used to get the boolean complete variable.
     * <p>
     * This method is used to retrieve the complete variable from this class to check whether or not level 2 is complete.
     *
     * @return  returns the boolean variable complete.
     */
    @Override
    public boolean isComplete() {
        return complete;
    }

    /**
     * The method used to set level 2 to complete.
     * <p>
     * This method is used to complete level 2 of the game.
     */
    public void setComplete() {
        levelSound.stop();
        player.destroy();
        this.complete = true;
    }

    /**
     * The method used to get the background of level 2.
     * <p>
     * This method is used to retrieve the background variable from this class.
     *
     * @return  returns the variable background.
     */
    @Override
    public Image getBackground() {
        return background;
    }

    /**
     * The method used to get the background music of level 2.
     * <p>
     * This method is used to retrieve the levelSound variable from this class.
     *
     * @return  returns the variable levelSound.
     */
    @Override
    public SoundClip getLevelSound() {
        return levelSound;
    }

    /**
     * The method used to get the level number.
     * <p>
     * This method is used to retrieve the number of the current level.
     *
     * @return  returns the number 2 for level 2.
     */
    @Override
    public int getLevelNumber() {
        return 2;
    }

    /**
     * The method used to make grounds in level 2.
     * <p>
     * This method is used to create grounds with a customisable height and width in level 2.
     *
     * @param  x the x-coordinate of the ground being created.
     * @param  y the y-coordinate of the ground being created.
     * @param  width the width of the ground being created.
     * @param  height the height of the ground being created.
     * @param  offsetX the offset of the x-coordinate of the image used for the ground.
     */
    public void makeGround(float x,float y, int width, float height, float offsetX) {
        Shape shape = new BoxShape(width, height);
        StaticBody ground = new StaticBody(this, shape);
        ground.setPosition(new Vec2(x, y));
        for (int i = 0; i < width/2; i++) {
            new AttachedImage(ground, groundImage, 2, 0, new Vec2(offsetX+(i*4),-1.48f));
        }
    }
}
