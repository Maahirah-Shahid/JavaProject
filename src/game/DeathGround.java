package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

/**
 * The class for the static body that kills the player upon contact with it.
 */
public class DeathGround extends StaticBody {
    /**
     * The shape of the death ground object.
     */
    private static final Shape shape = new BoxShape(40,0.5f);
    /**
     * The image of the death ground object.
     */
    private static final BodyImage image = new BodyImage("data/Sonic/SonicInvisible.png");

    /**
     * The constructor of the death ground class.
     * <p>
     * This method initialises all the variables of the death ground class.
     *
     * @param  world the world in which the death ground is placed.
     * @param  x the x-coordinate where the death ground will be placed.
     * @param  y the y-coordinate where the death ground will be placed.
     */
    public DeathGround(World world, float x, float y) {
        super(world,shape);
        this.setPosition(new Vec2(x,y));
        this.addImage(image);
    }
}
