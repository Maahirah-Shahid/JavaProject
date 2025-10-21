package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SoundClip;

/**
 * Class to handle player collisions.
 */
public class PlayerCollisions implements CollisionListener {
    /**
     * The player of the game.
     */
    private Player player;
    /**
     * The actual game itself
     */
    private Game game;
    /**
     * Soundclips used by the collisions
     */
    private SoundClip coinCollect, springSound;

    /**
     * the constructor for the PlayerCollisions class.
     * <p>
     * This method is used to initialise all the variables in player collisions.
     *
     * @param  player the player of the game.
     * @param  game the game itself
     */
    public PlayerCollisions(Player player, Game game) {
        this.player = player;
        this.game = game;
        coinCollect = game.getSoundManager().getCollectSound();
        springSound = game.getSoundManager().getSpringSound();
    }

    /**
     * A method to handle collisions.
     * <p>
     * This method is used to handle all possible collisions with the player and their respective outcomes.
     *
     * @param  e the collision event taking place.
     */
    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Rings) { //if rings collide, player collects a ring
            player.setRings(player.getRings() + 1);
            player.updateScore(player.getScore() + 1000);
            game.getSoundManager().getCollectSound().play();
            e.getOtherBody().destroy();
        } else if (e.getOtherBody() instanceof Springs) { //if spring collides, player will spring up
            ((Springs) e.getOtherBody()).beingUsed();
            game.getSoundManager().getSpringSound().play();
            player.jump(20);
        } else if (e.getOtherBody() instanceof Enemy) {
            if (player.isJumpOn() || player.isSpindashing()){ //if player jumps on enemy, it will be destroyed
                game.getSoundManager().getBreakSound().play();
                e.getOtherBody().destroy();
                player.updateScore(player.getScore() + 2000);
            } else { //if enemy touches player, player loses a life
                if (player.getLives()>0) {
                    if (player.getRings()>0) {
                        game.getSoundManager().getTakeDamageSound().play();
                    }
                    player.takeDamage();
                } else {
                    game.gameOver();
                }
            }
        } else if (e.getOtherBody() instanceof EndRings) {
            game.goToNextLevel();
        } else if (e.getOtherBody() instanceof ScatteredRings) {
            ScatteredRings sRing = (ScatteredRings) e.getOtherBody();
            if (sRing.isCollectible()) {
                player.setRings(player.getRings() + 1);
                game.getSoundManager().getCollectSound().play();
                sRing.destroy();
            }
        } else if (e.getOtherBody() instanceof Spikes) {
            if (player.getLives()>0) {
                if (player.getRings()>0) {
                    game.getSoundManager().getTakeDamageSound().play();
                }
                player.takeDamage();
            } else {
                game.gameOver();
            }
        } else if (e.getOtherBody() instanceof DeathGround) {
            if (player.getLives()==0) {
                game.gameOver();
            }
            player.decreaseLives();
            game.loadGame(game.getLevel().getLevelNumber(), 0, 0, player.getLives());
        } else if (e.getOtherBody() instanceof Projectile) {
            Projectile p = (Projectile) e.getOtherBody();
            if (player.getLives()>0) {
                if (player.getRings()>0) {
                    game.getSoundManager().getTakeDamageSound().play();
                }
                player.takeDamage();
            } else {
                game.gameOver();
            }
            p.destroy();
        } else if (e.getOtherBody() instanceof SquidBadnik) {
            if (player.isJumpOn() || player.isSpindashing()){ //if player jumps on enemy, it will be destroyed
                game.getSoundManager().getBreakSound().play();
                e.getOtherBody().destroy();
                player.updateScore(player.getScore() + 2000);
            } else { //if enemy touches player, player loses a life
                if (player.getLives()>0) {
                    if (player.getRings()>0) {
                        game.getSoundManager().getTakeDamageSound().play();
                    }
                    player.takeDamage();
                } else {
                    game.gameOver();
                }
            }
        } else if (e.getOtherBody() instanceof RingBox) {
            if (player.isJumpOn() || player.isSpindashing()){
                game.getSoundManager().getBreakSound().play();
                e.getOtherBody().destroy();
                player.updateScore(player.getScore() + 12000);
                player.setRings(player.getRings() + 10);
            }
        }
    }
}

