package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for rings collectible.
 */
public class Rings extends DynamicBody implements ActionListener {
    /**
     * The shape of the rings.
     */
    private static final Shape shape = new BoxShape(0.9f,1,new Vec2(-0.2f,-0.75f));
    /**
     * The timer used to animate the rings.
     */
    private Timer timer;
    /**
     * The current frame of the animation.
     */
    private int currentFrame = 0;
    /**
     * The array that stores the rings frames.
     */
    private BodyImage[] ringFrames;

    /**
     * The constructor for the Rings class.
     * <p>
     * This method is used to initialise all the variables in the Rings class.
     *
     * @param  world the world the Rings spawn in.
     * @param  posX the x-coordinate of the Rings.
     * @param  posY the y-coordinate of the Rings.
     */
    public Rings(World world, float posX, float posY) {
        super(world, shape);
        ringFrames = new BodyImage[4];
        this.loadRingFrames();
        //this.setAlwaysOutline(true);
        timer = new Timer(125, this);
        timer.start();
        this.setPosition(new Vec2(posX,posY));
    }

    /**
     * The method to load ring frames.
     * <p>
     * This method is used to load each frame of the ring animation from their files to the ringFrames array.
     */
    public void loadRingFrames() {
        for (int i = 0; i < ringFrames.length; i++) {
            ringFrames[i] = new BodyImage("data/Rings/SmallRing" + i + ".png", 3);
        }
    }

    /**
     * The method for the animation.
     * <p>
     * This method is used to cycle through the frames of the ringFrames class to animate the rings.
     *
     * @param  e the action event taking place.
     */
    public void actionPerformed(ActionEvent e) {
        currentFrame = (currentFrame + 1) % ringFrames.length;
        this.removeAllImages();
        this.addImage(ringFrames[currentFrame]);
    }

    /**
     * The method for destroying the ring.
     * <p>
     * This method is used to destroy the ring and end the timer when it is destroyed for better performance.
     */
    @Override
    public void destroy() {
        if (timer != null) {
            timer.stop();
        }
        super.destroy();
    }
}
