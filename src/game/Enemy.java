package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class establishing enemies.
 */
public class Enemy extends Walker {
    /**
     * The shape used for the Enemy object.
     */
    private static final Shape shape = new BoxShape(1,1, new Vec2(0,0));

    /**
     * The constructor for the Enemy class.
     * <p>
     * This method is used to initialise all of the Enemy class' key variables.
     *
     * @param  world the world in which the enemy is placed.
     * @param  x the x-coordinate where the enemy will be placed.
     * @param  y the y-coordinate where the enemy will be placed.
     */
    public Enemy(World world, float x, float y) {
        super(world, shape);
        this.setPosition(new Vec2(x,y));
    }
}
