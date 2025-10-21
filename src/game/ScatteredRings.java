package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for ScatteredRings.
 */
public class ScatteredRings extends DynamicBody implements ActionListener{
    /**
     * The shape of each scattered ring.
     */
    private static final Shape shape = new BoxShape(0.9f,1,new Vec2(-0.2f,-0.75f));
    /**
     * The time left until the scattered ring disappears.
     */
    private static final int timeLeft = 5000;
    /**
     * The time the scattered ring is not collectible.
     */
    private static final int collectDelay = 2000;
    /**
     * The boolean for whether the scattered ring is collectible or not.
     */
    private boolean collectible = false;
    /**
     * The array storing all the frames of the scattered ring animation.
     */
    private BodyImage[] ringFrames;
    /**
     * The timer for the scattered ring animation.
     */
    private Timer timer;
    /**
     * The current frame of the scattered ring animation.
     */
    private int currentFrame = 0;
    /**
     * The variable for the current image of the scattered ring, added to optimise game performance.
     */
    private BodyImage currentImage = null;

    /**
     * The constructor for the ScatteredRings class.
     * <p>
     * This method is used to initialise all the variables in the ScatteredRings class.
     *
     * @param  world the world the ScatteredRings spawn in.
     * @param  posX the x-coordinate of the ScatteredRings.
     * @param  posY the y-coordinate of the ScatteredRings.
     */
    public ScatteredRings(World world, float posX, float posY) {
        super(world, shape);
        this.setPosition(new Vec2(posX,posY));
        ringFrames = new BodyImage[8];
        this.loadRingFrames();
        timer = new Timer(125, this);
        timer.start();

        currentImage = ringFrames[1];
        this.addImage(currentImage);

        //make bouncy and a bit slippery
        this.setPosition(new Vec2(posX, posY));
        new SolidFixture(this, shape);


        //make collectible after delay
        Timer collectTimer = new Timer(collectDelay, e -> collectible = true);
        collectTimer.setRepeats(false);
        collectTimer.start();

        //auto-destroy after lifetime expires
        Timer lifeTimer = new Timer(timeLeft, e -> {
            if (this.getWorld() != null) {
                this.destroy();
            }
        });
        lifeTimer.setRepeats(false);
        lifeTimer.start();
    }

    /**
     * The method to load scattered ring frames.
     * <p>
     * This method is used to load each frame of the scattered rings animation from their files to the ringFrames array.
     */
    public void loadRingFrames() {
        int realFrameIndex = 0;
        for (int i = 0; i < ringFrames.length; i++) {
            if (i % 2 == 0) {
                ringFrames[i] = new BodyImage("data/Sonic/SonicInvisible.png", 3);
            } else {
                ringFrames[i] = new BodyImage("data/Rings/SmallRing" + realFrameIndex + ".png", 3);
                realFrameIndex++;
            }
        }
    }

    /**
     * The method to check if collectible.
     * <p>
     * This method is used to check if the scattered ring is collectible.
     *
     * @return  returns whether the ring is collectible or not.
     */
    public boolean isCollectible() {
        return collectible;
    }

    /**
     * The method for animating the ring class.
     * <p>
     * This method is used to cycle through each scattered ring frame in order to animate it.
     *
     * @param  e the action event taking place.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int nextFrame = (currentFrame + 1) % ringFrames.length;

        if (ringFrames[nextFrame] != currentImage) {
            removeAllImages();
            addImage(ringFrames[nextFrame]);
            currentImage = ringFrames[nextFrame];
        }
        currentFrame = nextFrame;
    }

    /**
     * The method for destroying the scattered rings.
     * <p>
     * This method is used to destroy the scattered rings and end the timer when it is destroyed for better performance.
     */
    @Override
    public void destroy() {
        if (timer != null) {
            timer.stop();
        }
        super.destroy();
    }
}
