package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for the squid badniks.
 */
public class SquidBadnik extends StaticBody{
    /**
     * The player of the game.
     */
    private final Player player;
    /**
     * The world the squidbadnik spawns in.
     */
    private final World world;
    /**
     * The timers used to shoot in this class.
     */
    private Timer shootTimer, firstTimer;
    /**
     * The boolean to check if the shooting has been performed.
     */
    private boolean performed = false;
    /**
     * The shape of the squidbadnik.
     */
    private static final Shape shape = new BoxShape(1,1, new Vec2(0,0));
    /**
     * The image for the squidbadnik.
     */
    private static final BodyImage image = new BodyImage("data/Badniks/Squid.png",2.8f);

    /**
     * The constructor for the SquidBadnik class.
     * <p>
     * This method is used to initialise all the variables in the SquidBadnik class.
     *
     * @param  world the world the SquidBadnik spawn in.
     * @param  x the x-coordinate of the SquidBadnik.
     * @param  y the y-coordinate of the SquidBadnik.
     * @param  player the player of the game.
     */
    public SquidBadnik(World world, int x, int y, Player player) {
        super(world,shape);
        this.world = world;
        this.player = player;
        this.addImage(image);

        firstTimer = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!performed) {
                    checkAndShoot();
                } else {
                    firstTimer.stop();
                }
            }
        });
        firstTimer.start();

        setPosition(new Vec2(x, y));

        //check player position every 1 second
        shootTimer = new Timer(1000, e -> checkAndShoot());
        shootTimer.start();
    }

    /**
     * The method to check if the player is under the badnik.
     * <p>
     * This method is used to check whether or not the player is beneath the badnik and thus indicate it to shoot.
     */
    private void checkAndShoot() {
        Vec2 playerPos = player.getPosition();
        Vec2 enemyPos = this.getPosition();

        //shoot if the player is directly beneath
        if (playerPos.x > enemyPos.x - 1 && playerPos.x < enemyPos.x + 1 && playerPos.y < enemyPos.y) {
            shootProjectile();
            performed = true;
        }
    }

    /**
     * The method to shoot projectiles.
     * <p>
     * This method is used to actually shoot a projectile at the player.
     */
    private void shootProjectile() {
        Vec2 spawnPos = new Vec2(getPosition().x, getPosition().y - 1);
        new Projectile(world, spawnPos.x, spawnPos.y);
    }

    /**
     * The method to stop shooting.
     * <p>
     * This method is used to stop the badnik from shooting.
     */
    public void stopShooting() {
        if (shootTimer != null) shootTimer.stop();
    }

    /**
     * The method for destroying the squidbadnik.
     * <p>
     * This method is used to destroy the squidbadnik and end all timers when it is destroyed for better performance.
     */
    @Override
    public void destroy() {
        stopShooting();
        if (firstTimer != null) firstTimer.stop();
        super.destroy();
    }
}
