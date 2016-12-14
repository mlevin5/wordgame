package wordgame;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class SentenceTest {

    @Test
    public void test() throws IOException {
        Sentence sentence = new Sentence("the cow","test.txt");
        sentence.guessLetter("t");
        sentence.guessLetter("h");
        System.out.println("current words "+sentence.getLettersOfCurrentWord());

    }

}
