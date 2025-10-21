package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for the panel that shows up on screen.
 */
public class SecondPanel {
    /**
     * The main panel.
     */
    private JPanel mainPanel;
    /**
     * The quit button.
     */
    private JButton Quit;
    /**
     * The highscores button.
     */
    private JButton highScoresButton;
    /**
     * The next level button.
     */
    private JButton NextLevel;
    /**
     * The save button.
     */
    private JButton Save;
    /**
     * The load button.
     */
    private JButton Load;
    /**
     * The game button.
     */
    private Game game;
    /**
     * The player score variable.
     */
    private int score = 0;
    /**
     * The level the player is on.
     */
    private int level = 0;
    /**
     * The player rings variable.
     */
    private int rings = 0;
    /**
     * The player lives variable.
     */
    private int lives = 0;

    /**
     * The constructor for the SecondPanel class.
     * <p>
     * This method is used to initialise all the variables in the SecondPanel class.
     *
     * @param  game the game the panel is in.
     */
    public SecondPanel(Game game) {
        this.game = game;
        Quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        NextLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.goToNextLevel();
            }
        });
        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<String> scores = game.getScoreHandler().readScores();
                    JFrame frame = new JFrame("Top Scores");
                    JPanel panel = new JPanel();
                    panel.setLayout(new BorderLayout());
                    JList list = new JList<>(scores.toArray());
                    try {
                        Font sonicFont = Font.createFont(Font.TRUETYPE_FONT, new File("data/Font/sonic-advance-3.ttf")).deriveFont(18f);
                        list.setFont(sonicFont);
                    } catch (FontFormatException | IOException ex) {
                        ex.printStackTrace();
                        System.out.println("Failed to load font: " + ex);
                        list.setFont(new Font("SansSerif", Font.BOLD, 18));
                    }
                    list.setOpaque(false);
                    list.setBackground(new Color(0, 0, 0, 0));
                    list.setForeground(Color.BLACK);
                    //create scroll
                    JScrollPane scrollPane = new JScrollPane(list);
                    scrollPane.setOpaque(false);
                    scrollPane.getViewport().setOpaque(false);
                    scrollPane.setBorder(null);
                    //add to frame
                    panel.add(scrollPane, BorderLayout.CENTER);
                    frame.add(panel);
                    frame.setSize(300,300);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setVisible(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        Save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score = game.getLevel().getPlayer().getScore();
                level = game.getLevel().getLevelNumber();
                rings = game.getLevel().getPlayer().getRings();
                lives = game.getLevel().getPlayer().getLives();
                System.out.println("save clicked");
            }
        });
        Load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (level != 0) {
                    game.loadGame(level,score,rings,lives);
                }
                System.out.println("load clicked");
            }
        });
    }

    /**
     * The method used to get the main panel.
     * <p>
     * This method is used to retrieve the main panel.
     *
     * @return  returns the main panel.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
