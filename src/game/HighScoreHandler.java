package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The class that handles the highscores of each player.
 */
public class HighScoreHandler {
    /**
     * The name of the file being read from and written to.
     */
    private String fileName;

    /**
     * The constructor for the highscore handler.
     * <p>
     * This method initialises all the variable of the highscore handler.
     *
     * @param  fileName the name of the file being read from and written to.
     */
    public HighScoreHandler(String fileName) {
        this.fileName = fileName;
    }

    /**
     * The method that writes to the file.
     * <p>
     * This method is used to write to the file passed into the class.
     *
     * @param  name the name of whoever's playing the game currently.
     * @param  score the score of whoever's playing the game currently.
     */
    public void writeHighScore(String name, int score) throws IOException {
        boolean append = true;
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName, append);
            writer.write(name + "," + score + "\n");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    //read from file
    /**
     * The method that reads from the file.
     * <p>
     * This method reads from the file that's passed into the class.
     *
     * @return  returns all the scores from the file.
     */
    public ArrayList<String> readScores() throws IOException {
        ArrayList<String> scores = new ArrayList<>();
        FileReader fr = null;
        BufferedReader reader = null;
        try {
            System.out.println("Reading " + fileName + " ...");
            fr = new FileReader(fileName);
            reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                // file is assumed to contain one name, score pair per line
                scores.add(line);
                line = reader.readLine();
            }
            System.out.println("...done.");
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
        return scores;
    }
}
