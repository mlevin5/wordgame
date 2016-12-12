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

public class Sentence {
    
    private static final int NUM_AV_LETTERS = 6;
    

    private final List<List<String>> displaySentence = Collections.synchronizedList(new ArrayList<List<String>>());
    private final List<List<String>> hiddenSentence = Collections.synchronizedList(new ArrayList<List<String>>());
    private final Set<String> availableLetters = Collections.synchronizedSet(new HashSet<String>());
    private final BufferedWriter writer;

    // Abstraction function

    // Rep invariant

    // Safety from rep exposure

    // Thread safety
    //   - all fields are thread-safe: displaySentence and hiddenSentence are 
    //     synchronized lists, availableLetters is a synchronized set, and 
    //     writer is a thread-safe data type.
    //   -all public methods are synchronized
    //   -all lists are synchronized lists
    //   -private methods use confinement
    
    /**
     * Check the rep invariant of the Sentence class.
     */
    private void checkRep(){
        assert !this.displaySentence.isEmpty();
        assert !this.hiddenSentence.isEmpty();
        assert !this.availableLetters.isEmpty();
        // other things to do later
    }
    /**
     * Generate a random capital letter 
     * @return a random capital letter [A-Z] 
     */
    private String randomLetter(){
        Random r = new Random();
        String randLetter = Character.toString((char) (r.nextInt(26) + 'a')).toUpperCase();
        return randLetter;
    }
    /**
     * Updates the set of Available letters so that there are only ever
     * NUM_AV_LETTERS (global variable for the number of letters that should
     * be in the availableLetters set) letters in the set. Also, makes sure that
     * all the letters needed for the word the user is currently trying to guess
     * are in the availableLetters set. All the other letters are letters from the next
     * word(s) or otherwise random. 
     */
    private void fixAvailableLetters(){
        while(availableLetters.size() < NUM_AV_LETTERS){
            String randLetter1 = randomLetter();
            if(!availableLetters.contains(randLetter1)){
                availableLetters.add(randLetter1);
            }
        }
        while(availableLetters.size() > NUM_AV_LETTERS){
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
    private void nextWord(){
        int indexLastWord = wordToGuess() - 2;
        List<String> wordAsList = hiddenSentence.get(indexLastWord);
        for(int i=0; i<wordAsList.size(); i++){
            availableLetters.remove(wordAsList.get(i));
        }
        if(indexLastWord+1 != hiddenSentence.size()){
            for(int j=0; j< hiddenSentence.get(indexLastWord+1).size(); j++){
                availableLetters.add(hiddenSentence.get(indexLastWord+1).get(j));
            }
        }
        fixAvailableLetters();

    }
    /**
     * Constructor for Sentence that parses the input sentence into 
     * a list of words, where each word is a list of letters (hiddenSentence),
     * makes a display version of hiddenSentence, where each letter is
     * replaced by an underscore (displaySentence), initialize the available
     * letters for the user to use to guess the first word in the sentence (availableLetters)
     * and initializes the file writer (writer). 
     * @param sentence - the input phrase or idiom that the user must guess
     * @param filename - the name of the file to store the user's action data in
     * @throws IOException if there is a problem writing to the file
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
  
    /**
     * Guesses the input word in the next empty spot in the phrase.
     * @param word the word that the user is guessing 
     * @return a string describing different events that can happen. 
     * "wrong size" meant that the input word did not fit in the next 
     * empty spot in the phrase. the display sentence is not changed.
     * "invalid letters" means the user tried to use a letter not in the 
     * available letters set. the display sentence is not changed.
     * "good" means that the input word was valid and the display sentence
     * was changed to reflect the user's guess.
     * @throws IOException
     */
    public synchronized String guessWord(String word) throws IOException {

        int indexWord = wordToGuess() - 1;
        List<String> wordAsList = Arrays.asList(word.toUpperCase().split(""));
        
        if(indexWord==displaySentence.size()){
            writer.write(System.currentTimeMillis()+"\nguessed: "+word+"\ninvalid letter error\n\n");
            return "no more blanks";
        }
        if(wordAsList.size()!=displaySentence.get(indexWord).size()){
            writer.write(System.currentTimeMillis()+"\nguessed: "+word+"\nwrong size error\n\n");
            return "wrong size";
        }
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
    // TODO guessLetter
        public synchronized String guessLetter(String letter) throws IOException {

        int indexWord = wordToGuess() - 1;
        int indexLetter = letterToGuess() -1;
        letter = letter.toUpperCase();
        if(indexWord==displaySentence.size()){
            writer.write(System.currentTimeMillis()+"\nguessed: "+letter+"\ninvalid letter error\n\n");
            return "no more blanks";
        }
        if(!availableLetters.contains(letter)){
            writer.write(System.currentTimeMillis()+"\nguessed: "+letter+"\ninvalid letter error\n\n");
            return "invalid letter";
        }
        displaySentence.get(indexWord).set(indexLetter, letter);
        if(letterToGuess() == 1){
            nextWord();
            return "next word";
        }
        checkRep();
        writer.write(System.currentTimeMillis()+"\nguessed: "+letter+toString()+"\n");
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
    public synchronized int letterToGuess() {
        checkRep();
        if(wordToGuess() != displaySentence.size()+1){
            List<String> word = displaySentence.get(wordToGuess()-1);
            for(int i=0; i<word.size(); i++){
                if(word.get(i).equals("_")){
                    return i+1;
                }
            }
        }
        return 0;
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
    public synchronized void goBackALetter() throws IOException {
        // if going back to another word
        if(letterToGuess() ==1 && wordToGuess() == 1){   
        }
        else if(letterToGuess() == 1 | letterToGuess() == 0){
            List<String> prevWord = displaySentence.get(wordToGuess()-2);
            prevWord.set(prevWord.size()-1, "_");
            fixAvailableLetters();
            writer.write(System.currentTimeMillis()+"\n"+"back"+toString()+"\n");
        }
        // if going back in the middle of a word
        else{
            List<String> currentWord = displaySentence.get(wordToGuess()-1);
            currentWord.set(letterToGuess()-2, "_");
        }
        checkRep();
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
    public synchronized Set<String> getAvailableLetters(){
        return new HashSet<String>(availableLetters);
    }

    public synchronized String toStringGUI(){
        String s = "<html><center>";
        for(List<String> word : displaySentence){
            for(String letter : word){
                s+= letter+" ";
            }
            s+="<br>";
        }
        return s+"</html>";
    }
    /**
     * Prints the display sentence and available letters to string
     * @return a string representation of the word game
     */
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


