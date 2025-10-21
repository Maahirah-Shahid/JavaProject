package game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//class to make sure the game can be controlled once clicked on
/**
 * The class that makes sure the game can be controlled once clicked on.
 */
public class GiveFocus extends MouseAdapter {
    /**
     * The method that gives the game focus when it's clicked onto.
     * <p>
     * This method allows the game to be played by requesting the computer its focus.
     *
     * @param  e the event of a mouse clicking onto the game.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        e.getComponent().requestFocus();
    }
}
