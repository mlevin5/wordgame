package wordgame;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class SentenceTest {

    @Test
    public void test() throws IOException {
        Sentence sentence = new Sentence("the cow","test.txt");
        sentence.guessLetter("t");
        System.out.println(sentence.toString());
        sentence.guessLetter("h");
        System.out.println(sentence.toString());
        sentence.guessLetter("e");
        System.out.println(sentence.toString());
        sentence.guessLetter("c");
        System.out.println(sentence.toString());
        sentence.guessLetter("o");
        System.out.println(sentence.toString());
        sentence.guessLetter("w");
        System.out.println(sentence.toString());
    }

}
