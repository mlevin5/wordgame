package wordgame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Word {
    
    private static final int NUM_AV_LETTERS = 6;
    

    private final List<List<String>> displaySentence = Collections.synchronizedList(new ArrayList<List<String>>());
    private final List<List<String>> hiddenSentence = Collections.synchronizedList(new ArrayList<List<String>>());
    private final List<List<String>> availableLetters = Collections.synchronizedList(new ArrayList<List<String>>());
    private final String sentence;
    
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
    private void initializeAvailableLetters(){
        for(int i=0; i<hiddenSentence.size(); i++){
            List<String> lettersForWord = new ArrayList<String>();
            for(int j=0; j<hiddenSentence.get(i).size(); j++){
                lettersForWord.add(hiddenSentence.get(i).get(j));
            }
            while (lettersForWord.size() < NUM_AV_LETTERS){
                lettersForWord.add(randomLetter());
            }
            Collections.shuffle(lettersForWord);
            availableLetters.add(lettersForWord);
            
        }
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
     */
    public Word(String sentence, String availableLetters){
        this.sentence = sentence.toUpperCase();
        List<String> listOfWords = Arrays.asList(this.sentence.split(" "));
        for(int i=0; i<listOfWords.size(); i++){ 
            List<String> wordAsList = Arrays.asList(listOfWords.get(i).split(""));
            this.hiddenSentence.add(wordAsList);
            List<String> displayWordAsList = new ArrayList<String>();
            for(int j=0; j< wordAsList.size(); j++){
                displayWordAsList.add("_");
            }
            this.displaySentence.add(displayWordAsList);    
        }
        this.availableLetters.add(new ArrayList<String>());
        List<String> listOfAvLetters = Arrays.asList(availableLetters.split(""));
        Collections.shuffle(listOfAvLetters);
        for(String letter : listOfAvLetters){
            this.availableLetters.get(0).add(letter.toUpperCase());
        }
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
    public synchronized String guessWord(String word) {
        int indexWord = wordToGuess() - 1;
        List<String> wordAsList = Arrays.asList(word.toUpperCase().split(""));    
        if(indexWord==displaySentence.size()){
            return "no more blanks";
        }
        if(wordAsList.size()!=displaySentence.get(indexWord).size()){
            return "wrong size";
        }
        for(String letter : wordAsList){
            if(!getAvailableLetters().contains(letter)){
                return "invalid letters";
            }
        }
        for(int i=0; i<wordAsList.size(); i++){
            displaySentence.get(indexWord).set(i,wordAsList.get(i));
        }
        checkRep();
        return "good";
    }
    
    public synchronized String guessLetter(String letter) throws IOException {

        int indexWord = wordToGuess() - 1;
        int indexLetter = letterToGuess() -1;
        letter = letter.toUpperCase();
        if(indexWord==displaySentence.size()){

            return "no more blanks";
        }
       /* if(!getAvailableLetters().contains(letter)){
            System.out.println("il");
            return "invalid letter";
        }*/
        displaySentence.get(indexWord).set(indexLetter, letter);
        System.out.println(displaySentence);
        if(letterToGuess() == 1){
            return "next word";
        }
        checkRep();
        return "good";
    }

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
        checkRep();
    }
    public synchronized String goBackALetter() throws IOException {
        // if going back to another word
        if(letterToGuess() ==1 && wordToGuess() == 1){  
            return "";
        }
        else if(letterToGuess() == 1 | letterToGuess() == 0){
            List<String> prevWord = displaySentence.get(wordToGuess()-2);
            return prevWord.set(prevWord.size()-1, "_");
        }
        // if going back in the middle of a word
        else{
            List<String> currentWord = displaySentence.get(wordToGuess()-1);
            return currentWord.set(letterToGuess()-2, "_");
        }
    }

    public synchronized int curWordLen() {
        checkRep();
        return hiddenSentence.get(wordToGuess()-1).size();
    }
    public synchronized String lastWordCheck(){
        int index = wordToGuess() - 2;
        if(displaySentence.get(index).equals(hiddenSentence.get(index))){
            return "RIGHT";
        }
        return "WRONG";
    }
    public synchronized boolean win() throws IOException {
        checkRep();
        return displaySentence.equals(hiddenSentence);
    }
    public synchronized boolean full() throws IOException {
        checkRep();
        for(List<String> word : displaySentence){
            if(word.contains("_")){
                return false;
            }
        }
        return true;
    }
    public synchronized List<String> getAvailableLetters(){
        return new ArrayList<String>(availableLetters.get(wordToGuess()-1));
    } //6.035 , check out new classes
    public synchronized boolean isSolvable(){
        List<String> word= hiddenSentence.get(0);
        for(String letter : word){
            if(!availableLetters.get(0).contains(letter)){
                return false;
            }
        }
        return true;
    }
    
    public synchronized List<String> getLettersOfCurrentWord(){
        int wordIndex = wordToGuess()-1;
        List<String> displayWord = displaySentence.get(wordIndex);
        List<String> lettersInWord = new ArrayList<String>();
        for(String letter : displayWord){
            if(!letter.equals("_")){
                lettersInWord.add(letter);
            }
        }
        return lettersInWord;
    }
    public synchronized List<String> letterGuessesLeft(){
        List<String> leftover = new ArrayList<String>(getAvailableLetters());
        List<String> lettersOfCurrentWord = new ArrayList<String>(getLettersOfCurrentWord());
        for(String letter : getAvailableLetters()){
            if(lettersOfCurrentWord.contains(letter)){
                lettersOfCurrentWord.remove(letter);
                leftover.remove(letter);
            }
        }
        return leftover;
    }
   // sentence.getAvailableLetters() - sentence
    //.getLettersOfCurrentWord()
    
    public synchronized String getSentence(){
        return this.sentence;
    }

// <font color="red">This is some text!</font> 
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
    public synchronized String toStringGUI(String color){
        String s = "<html><center><font color=\""+color+"\">";
        for(List<String> word : displaySentence){
            for(String letter : word){
                s+= letter+" ";
            }
            s+="<br>";
        }
        return s+"</font></html>";
    }
    
    public synchronized String toStringSimple(){
        String s = "";
        for(List<String> word : displaySentence){
            for(String letter : word){
                s+= letter+" ";
            }
            s+=" ";
        }
        return s;
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
            for(String letter : getAvailableLetters()){
                s+= letter+" ";
            }
        } else{
            s+= "\n"+"Type 'submit' to submit or 'back' to go back a word.";
        }

        s+="\n";
        return s;
    }

}


