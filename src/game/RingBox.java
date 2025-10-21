package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * Class for the RingBox item box.
 */
public class RingBox extends StaticBody {
    /**
     * The shape for the ring box.
     */
    private static final Shape shape = new BoxShape(1f,1.2f,new Vec2(0f,0f));
    /**
     * The image for the ring box.
     */
    private static final BodyImage image = new BodyImage("data/RingBox.png", 2.8f);

    /**
     * The constructor for the RingBox class.
     * <p>
     * This method is used to initialise all the variables in the RingBox class.
     *
     * @param  world the world the RingBox spawns in.
     * @param  x the x-coordinate of the RingBox.
     * @param  y the y-coordinate of the RingBox.
     */
    public RingBox(World world, float x, float y) {
        super(world,shape);
        this.setPosition(new Vec2(x, y));
        this.addImage(image);
    }
}
