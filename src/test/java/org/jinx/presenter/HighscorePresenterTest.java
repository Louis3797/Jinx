package org.jinx.presenter;

import org.jinx.highscore.HighScoreList;
import org.jinx.view.HighscoreView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class HighscorePresenterTest {

    HighScoreList highScoreList;
    HighscoreView highscoreView;
    HighscorePresenter highscorePresenter;

    @BeforeEach
    void setup() {
        highScoreList = new HighScoreList();
        highscoreView = new HighscoreView();
        highscorePresenter = new HighscorePresenter(highscoreView, highScoreList);
    }

    @Test
    void testReadHighscore() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Highscore.txt"));
        if (reader.readLine() != null) {
            highscorePresenter.readHighscore();
            assertNotEquals(highscoreView.getHighscorePane1().getText(), "");
        }
        else {
            highscorePresenter.readHighscore();
            assertEquals(highscoreView.getHighscorePane1().getText(), "");
        }
    }


}
