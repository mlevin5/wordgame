package wordgame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * A mutable data type representing a Minesweeper board.
 * Boards can be constructed with the hidden bombs in random locations
 * or from a text file of a specified format. 
 */
public class Sentence {
    
    private static final int LETTERS = 9;
    

    private final List<List<String>> displaySentence = Collections.synchronizedList(new ArrayList<List<String>>());
    private final List<List<String>> hiddenSentence = Collections.synchronizedList(new ArrayList<List<String>>());
    private final Set<String> availableLetters = Collections.synchronizedSet(new HashSet<String>());
    private final BufferedWriter writer;

    // Abstraction function

    // Rep invariant

    // Safety from rep exposure

    // Thread safety
    //   - all fields are thread-safe: displayCells and hiddenCells are synchronized lists, 
    //     and row and column are primitive, final, and immutable.
    //   -all public methods are synchronized
    //   -all lists are synchronized lists
    //   -private methods use confinement
    
    /**
     * Check the rep invariant of the Board class.
     */
    private void checkRep(){
       // assert !this.displaySentence.isEmpty();
        //assert !this.hiddenSentence.isEmpty();
        // other things to do later
    }
    private String randomLetter(){
        Random r = new Random();
        String randLetter = Character.toString((char) (r.nextInt(26) + 'a')).toUpperCase();
        return randLetter;
    }

    private void fixAvailableLetters(){
        while(availableLetters.size() < LETTERS){
            String randLetter1 = randomLetter();
            if(!availableLetters.contains(randLetter1)){
                availableLetters.add(randLetter1);
            }
        }
        while(availableLetters.size() > LETTERS){
            String randLetter2 = randomLetter();
            if(!hiddenSentence.get(wordToGuess()-1).contains(randLetter2) && availableLetters.contains(randLetter2)){
                availableLetters.remove(randLetter2);
            }
        }
        List<String> wordToCheck = hiddenSentence.get(wordToGuess()-1);
        for(String letter : wordToCheck){
            while(!availableLetters.contains(letter)){
                String randLetter3 = randomLetter();
                if(availableLetters.contains(randLetter3) && !wordToCheck.contains(randLetter3)){
                    availableLetters.remove(randLetter3);
                    availableLetters.add(letter);
                }
            }
        }
    }
 
 
    /**
     * Constructor for Board that makes a minesweeper board with bombs placed
     * randomly. The size is determined by inputs.
     * @param numColumns - the number of columns in the board
     * @param numRows - the number of rows in the board
     * @return a Board object representing a minesweeper board
     * @throws IOException 
     */
    public Sentence(String sentence, String filename) throws IOException{
        this.writer = new BufferedWriter(new FileWriter(filename, true));
        String s = sentence.toUpperCase();
        List<String> listOfWords = Arrays.asList(s.split(" "));
        for(int i=0; i<listOfWords.size(); i++){ 
            List<String> wordAsList = Arrays.asList(listOfWords.get(i).split(""));
            this.hiddenSentence.add(wordAsList);
            List<String> displayWordAsList = new ArrayList<String>();
            for(int j=0; j< wordAsList.size(); j++){
                String letter = wordAsList.get(j);
                if(!this.availableLetters.contains(letter)){
                    this.availableLetters.add(letter);
                }
                displayWordAsList.add("_");
               
            }
            this.displaySentence.add(displayWordAsList);    
        }
        fixAvailableLetters();
        checkRep();
    }
  
    public synchronized String guessWord(String word) throws IOException {

        int indexWord = wordToGuess() - 1;
        List<String> wordAsList = Arrays.asList(word.toUpperCase().split(""));
        
        if(wordAsList.size()!=displaySentence.get(indexWord).size()){
            writer.write(System.currentTimeMillis()+"\nguessed: "+word+"\nwrong size error\n\n");
            return "wrong size";
        }
        // if any letters in word not in available letters
        for(String letter : wordAsList){
            if(!availableLetters.contains(letter)){
                writer.write(System.currentTimeMillis()+"\nguessed: "+word+"\ninvalid letters error\n\n");
                return "invalid letters";
            }
        }
        for(int i=0; i<wordAsList.size(); i++){
            displaySentence.get(indexWord).set(i,wordAsList.get(i));
            availableLetters.remove(wordAsList.get(i));
        }
        if(indexWord+1 != hiddenSentence.size()){
            for(int j=0; j< hiddenSentence.get(indexWord+1).size(); j++){
                availableLetters.add(hiddenSentence.get(indexWord+1).get(j));
            }
        }
        if (wordToGuess() != displaySentence.size()+1){
            fixAvailableLetters();
        }
        checkRep();
        writer.write(System.currentTimeMillis()+"\nguessed: "+word+toString()+"\n");
        return "good";
    }
    //TODO remember end case
    public synchronized int wordToGuess() {
        checkRep();
        for(int i=0; i<displaySentence.size(); i++){
            if(displaySentence.get(i).contains("_")){
                return i+1;
            }
        }
        return displaySentence.size()+1;
    }
    public synchronized void goBackAWord() throws IOException {

        List<String> prevWord = displaySentence.get(wordToGuess()-2);
        for(int i=0;i<prevWord.size();i++){
            prevWord.set(i, "_");
        }
        fixAvailableLetters();
        writer.write(System.currentTimeMillis()+"\n"+"back"+toString()+"\n");
        checkRep();
    }
    public synchronized int length() {
        checkRep();
        return displaySentence.size();
    }
    public synchronized void startGame(String user) throws IOException {
        checkRep();
        writer.write(System.currentTimeMillis()+"\n"+"user "+user+" started"+toString()+"\n");
    }
    public synchronized void closeWriter() throws IOException {
        checkRep();
        writer.close();
    }

    public synchronized int curWordLen() {
        checkRep();
        return hiddenSentence.get(wordToGuess()-1).size();
    }
    public synchronized boolean win() throws IOException {
        checkRep();
        boolean win = displaySentence.equals(hiddenSentence);
        long endTime = System.currentTimeMillis();
        if(win){
            writer.write(endTime+"\nwin!\n\n");
        }else{
            writer.write(endTime+"\n"+"wrong submission\n\n");
        }
        return win;
    }

    /**
     * Prints the blank sentence to string
     * @return a string representation 
     */
    public String toMyString() {
        return displaySentence.toString()+"\n"
                +hiddenSentence.toString()+"\n"
                +availableLetters.toString()+"\n";
    }
    @Override public String toString(){
        String s = "\n";
        for(int i =0; i<displaySentence.size(); i++){
            if(i == wordToGuess()-1){
                s+= "v ";
            } else{
                s+= "  ";
            }
            for(int j=1; j<displaySentence.get(i).size(); j++){
                s+= "  ";
            }
            s+=" ";
        }
        s+="\n";
        for(int i =0; i<displaySentence.size(); i++){
            s+= Integer.toString(i+1)+" ";
            for(int j=1; j<displaySentence.get(i).size(); j++){
                s+= "  ";
            }
            s+=" ";
        }
        s+="\n";
        for(List<String> word : displaySentence){
            for(String letter : word){
                s+= letter+" ";
            }
            s+=" ";
        }
        
        if(wordToGuess() != displaySentence.size() +1){
            s+="\n"+"Available Letters: ";
            for(String letter : availableLetters){
                s+= letter+" ";
            }
        } else{
            s+= "\n"+"Type 'submit' to submit or 'back' to go back a word.";
        }

        s+="\n";
        return s;
    }

}


