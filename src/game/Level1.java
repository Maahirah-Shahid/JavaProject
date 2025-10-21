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
 * The class for the first level of the game.
 */
public class Level1 extends GameLevel {

    /**
     * The background of the first level of the game.
     */
    private Image background;
    /**
     * The image for the ground in the first level of the game.
     */
    private static final BodyImage groundImage= new BodyImage("data/SunsetHillPlatforms/shz/2.png", 2);
    /**
     * The image for the platforms in the first level of the game.
     */
    private static final BodyImage platformImage = new BodyImage("data/SunsetHillPlatforms/shz/30.png", 2);
    /**
     * The image for the walls in the first level of the game.
     */
    private static final BodyImage wallImage = new BodyImage("data/SunsetHillPlatforms/shz/34.png", 2);
    /**
     * The image of the bottom of the tree in the first level of the game.
     */
    private static final BodyImage bottomOfTree = new BodyImage("data/SunsetHillPlatforms/shz/184.png", 2);
    /**
     * The image of the top of the stree in the first level of the game.
     */
    private static final BodyImage topOfTree1 = new BodyImage("data/SunsetHillPlatforms/shz/153.png", 2);
    /**
     * The image of a small tree in the first level of the game.
     */
    private static final BodyImage smallTree = new BodyImage("data/SunsetHillPlatforms/shz/186.png", 2);
    /**
     * The image of the invisible wall in the first level of the game.
     */
    private static final BodyImage invisibleWallImage = new BodyImage("data/Sonic/SonicInvisible.png", 2);
    /**
     * The player of the game.
     */
    private Player player;
    /**
     * The level's background music.
     */
    private SoundClip levelSound;
    /**
     * The boolean declaring the level as complete or not.
     */
    private boolean complete = false;

    /**
     * The construct of the Level1 class.
     * <p>
     * This method is used to initialise all the variables of the level 1 class.
     *
     * @param  game the game in which level 1 takes place in.
     */
    public Level1(Game game) {
        super(game);
        background = new ImageIcon("data/SunsetHillGif.gif").getImage();
        player = this.getPlayer();
        this.levelSound = null;
        try {
            levelSound = new SoundClip("data/SoundEffects/SunsetHillTheme.wav");   // Open an audio input stream
            levelSound.loop();                              // Set it to continous playback (looping)
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println("Error:" + e);
        }

        //position player
        player.setPosition(new Vec2(-61, -9));

        //add trees
        makeTallTree(-65,-7);
        makeSmallTree(-35,-7);
        makeTallTree(42,-7);

        //make the ground
        makeGround(0,-11.5f,80,0.5f,-78);

        //make suspended platform and higher ground
        Springs spring1 = new Springs(this, -18,-9);
        makePlatform(-12f,-2f);
        makeGround(4,-5, 10,0.5f,-8);
        makeGround(22,-1,8,0.5f,-6);
        Shape wall1Shape = new BoxShape(6.5f, 0.5f);
        StaticBody wall1 = new StaticBody(this, wall1Shape);
        wall1.setPosition(new Vec2(29.5f, -7.5f));
        wall1.rotate((float)Math.toRadians(90));
        for (int i = 0; i < 4; i++) {
            new AttachedImage(wall1, wallImage, 2, -(float)Math.toRadians(90), new Vec2(2f,1.5f+(i*4)));
            new AttachedImage(wall1, wallImage, 2, -(float)Math.toRadians(90), new Vec2(-2f,1.5f+(i*4)));
        }
        makePlatform(50,-1);
        Springs spring2 = new Springs(this, 39.8f,2);


        //add rings
        Rings ring1 = new Rings(this, -50,-4);
        ring1.setGravityScale(0);
        Rings ring2 = new Rings(this, -48,-4);
        ring2.setGravityScale(0);
        Rings ring3 = new Rings(this, -46,-4);
        ring3.setGravityScale(0);
        Rings ring4 = new Rings(this, -30,-9);
        Rings ring5 = new Rings(this, -28,-9);
        Rings ring6 = new Rings(this, -26,-9);
        Rings ring7 = new Rings(this, 6,0f);
        ring7.setGravityScale(0);
        Rings ring8 = new Rings(this, 4,0);
        ring8.setGravityScale(0);
        Rings ring9 = new Rings(this, 2,0);
        ring9.setGravityScale(0);
        Rings ring10 = new Rings(this, 50.5f,1);
        Rings ring11 = new Rings(this, 48.5f,1);
        Rings ring12 = new Rings(this, 52.5f,1);
        Rings ring13 = new Rings(this, 50.5f,3);
        Rings ring14 = new Rings(this, 48.5f,3);
        Rings ring15 = new Rings(this, 52.5f,3);

        //add enemies
        Enemy enemy = new Motobug(this, -8, -9);
        Enemy enemy1 = new Motobug(this, 23,2.5f);


        makeWall(-5.5f,-9);
        Shape shape = new BoxShape(4, 0.5f);
        StaticBody invisibleWall = new StaticBody(this, shape);
        invisibleWall.setPosition(new Vec2(-67, -7));
        invisibleWall.rotate((float)Math.toRadians(90));
        new AttachedImage(invisibleWall,invisibleWallImage, 2, 0, new Vec2(0,0));

        //add end ring
        EndRings endRing = new EndRings(this, 70,-4);
    }

    /**
     * The method used to get the boolean complete variable.
     * <p>
     * This method is used to retrieve the complete variable from this class to check whether or not level 1 is complete.
     *
     * @return  returns the boolean variable complete.
     */
    @Override
    public boolean isComplete() {
        return complete;
    }

    /**
     * The method used to get the background of level 1.
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
     * The method used to get the background music of level 1.
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
     * The method used to set level 1 to complete.
     * <p>
     * This method is used to complete level 1 of the game.
     */
    public void setComplete() {
        levelSound.stop();
        player.destroy();
        this.complete = true;
    }

    /**
     * The method used to create a platform in level 1.
     * <p>
     * This method is used to create platforms with a set size in level 1.
     *
     * @param  x the x-coordinate of the platform being created.
     * @param  y the y-coordinate of the platfrom being created.
     */
    public void makePlatform(float x,float y) {
        Shape platformShape = new BoxShape(4, 0.5f);
        StaticBody platform = new StaticBody(this, platformShape);
        platform.setPosition(new Vec2(x, y));
        AttachedImage pTile1 = new AttachedImage(platform, platformImage, 2, 0, new Vec2(-2,-1.48f));
        AttachedImage pTile2 = new AttachedImage(platform, platformImage, 2, 0, new Vec2(2,-1.48f));
    }

    /**
     * The method used to make grounds in level 1.
     * <p>
     * This method is used to create grounds with a customisable height and width in level 1.
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

    /**
     * The method used to make a wall in level 1.
     * <p>
     * This method is used to create a wall in level 1.
     *
     * @param  x the x-coordinate of the wall.
     * @param  y the y-coordinate of the wall.
     */
    public void makeWall(float x, float y) {
        Shape shape = new BoxShape(4, 0.5f);
        StaticBody wall = new StaticBody(this, shape);
        wall.setPosition(new Vec2(x, y));
        wall.rotate((float)Math.toRadians(90));
        for (int i = 0; i < 5; i++) {
            new AttachedImage(wall, wallImage, 2, -(float)Math.toRadians(90), new Vec2(-1.4f,-1.49f-(i*4)));
        }
    }

    /**
     * The method used to make a tall tree in level 1.
     * <p>
     * This method is used to create a tall tree that Sonic can run past in level 1.
     *
     * @param  x the x-coordinate of the tall tree.
     * @param  y the y-coordinate of the tall tree.
     */
    public void makeTallTree(float x, float y) {
        StaticBody tree = new StaticBody(this);
        tree.setPosition(new Vec2(x, y));
        tree.setAlwaysOutline(true);
        new AttachedImage(tree, bottomOfTree, 4, 0, new Vec2(-2, 0));
        new AttachedImage(tree, topOfTree1, 4, 0, new Vec2(-2, 8));
    }

    /**
     * The method used to make a small tree in level 1.
     * <p>
     * This method is used to create a small tree that Sonic can run past in level 1.
     *
     * @param  x the x-coordinate of the small tree.
     * @param  y the y-coordinate of the small tree.
     */
    public void makeSmallTree(float x, float y) {
        StaticBody tree = new StaticBody(this);
        tree.setPosition(new Vec2(x, y));
        tree.setAlwaysOutline(true);
        new AttachedImage(tree, smallTree, 4, 0, new Vec2(-2, 0));
    }

    /**
     * The method used to get the level number.
     * <p>
     * This method is used to retrieve the number of the current level.
     *
     * @return  returns the number 1 for level 1.
     */
    public int getLevelNumber() {
        return 1;
    }
}
