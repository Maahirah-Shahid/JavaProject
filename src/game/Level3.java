package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The class for the third level of the game.
 */
public class Level3 extends GameLevel{
    /**
     * The background of the third level of the game.
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
    private boolean complete = false;
    /**
     * The image for the ground in the third level of the game.
     */
    private static final BodyImage groundImage = new BodyImage("data/Route99Platforms/r99z/2.png",3);
    /**
     * The image for the left edge in the third level of the game.
     */
    private static final BodyImage edgeImage = new BodyImage("data/Route99Platforms/r99z/1.png",3);
    /**
     * The image for the right edge in the third level of the game.
     */
    private static final BodyImage rightEdgeImage = new BodyImage("data/Route99Platforms/r99z/3.png",3);
    /**
     * The image for the invisible wall in the third level of the game.
     */
    private static final BodyImage invisibleWallImage = new BodyImage("data/Sonic/SonicInvisible.png",3);
    /**
     * The image for the wall in the third level of the game.
     */
    private static final BodyImage wallImage = new BodyImage("data/Route99Platforms/r99z/38.png",3);
    /**
     * The image for the platform in the third level of the game.
     */
    private static final BodyImage platformImage = new BodyImage("data/Route99Platforms/r99z/46.png",3);

    /**
     * The constructor for the level 3 class.
     * <p>
     * This method is used to initialise all the variables in level 3.
     *
     * @param  game the game in which level 3 takes place.
     */
    public Level3(Game game) {
        super(game);
        background = new ImageIcon("data/Route99Gif.gif").getImage();
        player = this.getPlayer();
        this.levelSound = null;
        try {
            levelSound = new SoundClip("data/SoundEffects/Route99Theme.wav");   // Open an audio input stream
            levelSound.loop();                              // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println("Error:" + e);
        }

        player.setPosition(new Vec2(-8,10));
        makeGround(0,9,21,0.5f,-18);
        Shape higherGroundShape = new BoxShape(6,0.5f);
        StaticBody higherGround = new StaticBody(this,higherGroundShape);
        higherGround.setPosition(new Vec2(27,13.5f));
        new AttachedImage(higherGround,edgeImage,2,0,new Vec2(-3,-2.48f));
        new AttachedImage(higherGround,groundImage,2,0,new Vec2(3,-2.48f));
        Shape highestGroundShape = new BoxShape(6,0.5f);
        StaticBody highestGround = new StaticBody(this,higherGroundShape);
        highestGround.setPosition(new Vec2(39,18f));
        new AttachedImage(highestGround,edgeImage,2,0,new Vec2(-3,-2.48f));
        new AttachedImage(highestGround,rightEdgeImage,2,0,new Vec2(3,-2.48f));
        new AttachedImage(highestGround,wallImage,2,0,new Vec2(6,-8.48f));
        makePlatform(63,25);
        makePlatform(85,27);
        makePlatform(105,25);
        makeGround(66,7.2f,21,0.5f,-18);
        makeGround(87,7.2f,21,0.5f,-18);
        Shape endGroundShape = new BoxShape(15,0.5f);
        StaticBody endGround = new StaticBody(this,endGroundShape);
        endGround.setPosition(new Vec2(123f,11.9f));
        new AttachedImage(endGround,edgeImage,2,0,new Vec2(-12,-2.48f));
        new AttachedImage(endGround,groundImage,2,0,new Vec2(-6,-2.48f));
        new AttachedImage(endGround,groundImage,2,0,new Vec2(0,-2.48f));
        new AttachedImage(endGround,groundImage,2,0,new Vec2(6,-2.48f));
        new AttachedImage(endGround,groundImage,2,0,new Vec2(12,-2.48f));

        Springs spring = new Springs(this,43,20.5f);
        for (int i=0;i<21;i++) {
            new Spikes(this,46.5f+(i*3),8.8f);
        }

        makeInvisibleWall(22,10);
        makeInvisibleWall(34, 14.5f);
        makeInvisibleWall(44, 14.5f);

        RingBox ringBox = new RingBox(this, 2,10.75f);
        Enemy enemy = new Motobug(this,125,12);
        EndRings endRing = new EndRings(this,128,20);
    }

    /**
     * The method used to get the boolean complete variable.
     * <p>
     * This method is used to retrieve the complete variable from this class to check whether or not level 3 is complete.
     *
     * @return  returns the boolean variable complete.
     */
    @Override
    public boolean isComplete() {
        return complete;
    }

    /**
     * The method used to get the background of level 3.
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
     * The method used to get the background music of level 3.
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
     * @return  returns the number 3 for level 3.
     */
    @Override
    public int getLevelNumber() {
        return 3;
    }

    /**
     * The method used to make grounds in level 3.
     * <p>
     * This method is used to create grounds with a customisable height and width in level 3.
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
        new AttachedImage(ground,groundImage,2,0,new Vec2(offsetX,-2.48f));
        new AttachedImage(ground,groundImage,2,0,new Vec2(offsetX+6,-2.48f));
        new AttachedImage(ground,groundImage,2,0,new Vec2(offsetX+(6*2),-2.48f));
        new AttachedImage(ground,groundImage,2,0,new Vec2(offsetX+(6*3),-2.48f));
        new AttachedImage(ground,groundImage,2,0,new Vec2(offsetX+(6*4),-2.48f));
        new AttachedImage(ground,groundImage,2,0,new Vec2(offsetX+(6*5),-2.48f));
        new AttachedImage(ground,groundImage,2,0,new Vec2(offsetX+(6*6),-2.48f));
    }

    /**
     * The method used to make invisible wall in level 3.
     * <p>
     * This method is used to create invisible walls with a customisable height and width in level 3.
     *
     * @param  x the x-coordinate of the invisible wall being created.
     * @param  y the y-coordinate of the invisible wall being created.
     */
    public void makeInvisibleWall(float x, float y) {
        Shape shape = new BoxShape(4, 0.5f);
        StaticBody invisibleWall = new StaticBody(this, shape);
        invisibleWall.setPosition(new Vec2(x, y));
        invisibleWall.rotate((float)Math.toRadians(90));
        new AttachedImage(invisibleWall,invisibleWallImage, 2, 0, new Vec2(0,0));
    }

    /**
     * The method used to make platforms in level 3.
     * <p>
     * This method is used to create platforms with a customisable height and width in level 3.
     *
     * @param  x the x-coordinate of the platform being created.
     * @param  y the y-coordinate of the platform being created.
     */
    public void makePlatform(float x,float y) {
        Shape platformShape = new BoxShape(3, 0.5f);
        StaticBody platform = new StaticBody(this, platformShape);
        platform.setPosition(new Vec2(x, y));
        AttachedImage pTile1 = new AttachedImage(platform, platformImage, 2, 0, new Vec2(0,-2.48f));
    }
}
