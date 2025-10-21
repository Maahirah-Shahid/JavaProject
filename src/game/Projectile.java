package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;

/**
 * The class for the projectiles.
 */
public class Projectile extends DynamicBody {
    /**
     * The shape for the projectile.
     */
    private static final Shape shape = new CircleShape(0.3f);

    /**
     * The constructor for the projectile class.
     * <p>
     * This method is used to initialise all the variables in the projectile class.
     *
     * @param  world the world the projectile spawns in.
     * @param  x the x-coordinate of the projectile.
     * @param  y the y-coordinate of the projectile.
     */
    public Projectile(World world, float x, float y) {
        super(world, shape);
        setPosition(new Vec2(x, y));
        setLinearVelocity(new Vec2(0, -10)); // Move downward
        setBullet(true); // Ensure accurate collision for fast motion

        // Destroy after 3 seconds
        Timer destroyTimer = new Timer(3000, e -> {
            if (getWorld() != null) {
                destroy();
            }
        });
        destroyTimer.setRepeats(false);
        destroyTimer.start();
    }
}
