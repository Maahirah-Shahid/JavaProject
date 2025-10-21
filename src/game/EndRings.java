package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for the ring that transitions the player to the next level.
 */
public class EndRings extends StaticBody implements ActionListener {
    /**
     * The shape used for the EndRings object.
     */
    private static final Shape shape = new BoxShape(1.5f,1.5f,new Vec2(0f,0f));
    /**
     * The timer used for the animation.
     */
    private Timer timer;
    /**
     * The variable storing the current frame of animation.
     */
    private int currentFrame = 0;
    /**
     * The array storing all the end ring animation frames.
     */
    private BodyImage[] endRingFrames;

    /**
     * The constructor for the EndRings class.
     * <p>
     * This method is used to initialise all of the EndRings class' key variables.
     *
     * @param  world the world in which the end ring is placed.
     * @param  posX the x-coordinate where the end ring will be placed.
     * @param  posY the y-coordinate where the end ring will be placed.
     */
    public EndRings(World world, float posX, float posY) {
        super(world, shape);
        endRingFrames = new BodyImage[9];
        this.loadEndRingFrames();
        timer = new Timer(125, this);
        timer.start();
        this.setPosition(new Vec2(posX,posY));
    }

    /**
     * The method for loading the end ring frames.
     * <p>
     * This method is used to load in each frame of the end ring sprite from the files to the endRingFrames array.
     */
    public void loadEndRingFrames() {
        for (int i = 0; i < endRingFrames.length; i++) {
            endRingFrames[i] = new BodyImage("data/Rings/EndRing" + i + ".png", 3);
        }
    }

    /**
     * The method for animating end rings.
     * <p>
     * This method is used to cycle through the frames in endRingFrames in order to animate it.
     *
     * @param  e the event taking place, in this case, an animation.
     */
    public void actionPerformed(ActionEvent e) {
        currentFrame = (currentFrame + 1) % endRingFrames.length;
        this.removeAllImages();
        this.addImage(endRingFrames[currentFrame]);
    }
}
