package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for springs.
 */
public class Springs extends StaticBody implements ActionListener {
    /**
     * The shape of the springs.
     */
    private static final Shape shape = new BoxShape(1.2f,1,new Vec2(0.1f,-0.95f));
    /**
     * The image for the spring.
     */
    private static final BodyImage springImage = new BodyImage("data/Springs/Spring0.png", 4);
    /**
     * The timer for the spring animation.
     */
    private Timer timer;
    /**
     * The current frame of the spring animation.
     */
    private int currentFrame = 0;
    /**
     * The array that stores all the frames of the spring animation.
     */
    private BodyImage[] springFrames;

    /**
     * The constructor for the Springs class.
     * <p>
     * This method is used to initialise all the variables in the Springs class.
     *
     * @param  world the world the Springs spawn in.
     * @param  x the x-coordinate of the Springs.
     * @param  y the y-coordinate of the Springs.
     */
    public Springs(World world, float x, float y) {
        super(world, shape);
        this.addImage(springImage);
        this.setPosition(new Vec2(x, y));
        springFrames = new BodyImage[3];
        loadSpringFrames();
        timer = new Timer(125, this);
    }

    /**
     * The method to load spring frames.
     * <p>
     * This method is used to load each frame of the spring animation from their files to the array springFrames.
     */
    public void loadSpringFrames() {
        for (int i = 0; i < springFrames.length; i++) {
            springFrames[i] = new BodyImage("data/Springs/Spring" + i + ".png", 4);
        }
    }

    /**
     * The method to set the spring as being used.
     * <p>
     * This method is used to start the animation of the spring when it is in use.
     */
    public void beingUsed() { //starts the timer when the spring is being used so animation plays
        currentFrame = 0;
        timer.start();
    }

    /**
     * The method for animating the spring.
     * <p>
     * This method is used to animate the spring by cycling through its frames when it's in use.
     *
     * @param  e the action event taking place.
     */
    public void actionPerformed(ActionEvent e) {
        currentFrame = (currentFrame + 1) % springFrames.length;
        this.removeAllImages();
        this.addImage(springFrames[currentFrame]);

        if (currentFrame==springFrames.length-1) { //stops the timer after animation plays once
            timer.stop();
        }
    }
}
