package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;

/**
 * Class for the spikes.
 */
public class Spikes extends StaticBody {
    /**
     * Shape of the spikes.
     */
    private static final Shape shape = new BoxShape(1.5f,1.3f,new Vec2(0,0.2f));
    /**
     * Image of the spikes.
     */
    private static final BodyImage image = new BodyImage("data/Spikes/Spike2.png",4);

    /**
     * The constructor for the Spikes class.
     * <p>
     * This method is used to initialise all the variables in the Spikes class.
     *
     * @param  world the world the Spikes spawn in.
     * @param  posX the x-coordinate of the Spikes.
     * @param  posY the y-coordinate of the Spikes.
     */
    public Spikes(World world, float posX, float posY) {
        super(world,shape);
        this.addImage(image);
        this.setPosition(new Vec2(posX,posY));
    }
}
