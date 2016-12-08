/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package wordgame.server;

import java.io.*;
import java.net.*;
import java.util.*;
import wordgame.Sentence;

/**
 * Multiplayer Minesweeper server.
 */
public class WordGameServer {

    // Abstraction function:
    //  represents a server at a specified port with a different 
    //  thread for each client with a word game represented by 
    //  the Sentence class
    
    // Rep Invariant
    //  sentence is a valid word game sentence
    //  serverSocket should always be open
    
    // Safety from rep exposure
    //  all fields are private
    //  all fields but players is final
    //  all fields are contained within the WordGameServer class
    //  no references to private fields are returned

    // System thread safety argument
    //   - all fields are thread-safe: serverSocket uses confinement, 
    //     sentence was made as a thread-safe data type and also uses
    //     confinement, and players uses confinement as well as 
    //     synchronized locks when being modified.
    //   -all public methods are synchronized
    //   -all lists are synchronized lists
    //   -private methods use confinement

    /** Default server port. */
    private static final int DEFAULT_PORT = 4444;
    /** Socket for receiving incoming connections. */
    private final ServerSocket serverSocket;
    /** Representation of the word game sentence. */
    private final Sentence sentence;
    /** Number of threads or current players in the word game. */
    private Integer players;

    /**
     * Make a WordGameServer that listens for connections on port.
     * 
     * @param port port number, requires 0 <= port <= 65535
     * @param debug debug mode flag
     * @throws IOException if an error occurs opening the server socket
     */
    public WordGameServer(Sentence sentence, int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.sentence = sentence;
        this.players = 0;
        checkRep();
    }
    /**
     * Checks the representation invariant for WordGameServer
     */
    private void checkRep(){
        assert !serverSocket.isClosed();
    }

    /**
     * Run the server, listening for client connections and handling them.
     * Never returns unless an exception is thrown. Capability for multiple clients
     * is here, but given the format of the actual game, not necessary
     * 
     * @throws IOException if the main server socket is broken
     * (IOExceptions from individual clients do *not* terminate serve())
     */
    public void serve() throws IOException {     
        while (true) {
            // block until a client connects
            final Socket socket = serverSocket.accept();
            
            // create a new thread to handle that client
            Thread handler = new Thread(new Runnable() {
                public void run() {
                    try {
                        try {
                            handleConnection(socket);
                        } finally {
                            socket.close();
                        }
                    } catch (IOException ioe) {
                        // this exception wouldn't terminate serve(),
                        // since we're now on a different thread, but
                        // we still need to handle it
                        ioe.printStackTrace();
                    }
                }
            });
            // start the thread
            handler.start();
            synchronized (players){
                players++;
            }
        }
    }

    /**
     * Handle a single client connection. Returns when client disconnects.
     * 
     * @param socket socket where the client is connected
     * @throws IOException if the connection encounters an error or terminates unexpectedly
     */
    private void handleConnection(Socket socket) throws IOException {
        System.err.println("client "+players+" connected.");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println("\nWelcome to the Word Game.\n"
                + "You are trying to guess a common phrase or idiom one word at a time\n"
                + "from the available letters. Every time you move on to a new word, the\n"
                + "available letters will change, but you will always have the letters\n"
                + "that you need at a given moment. To guess a word, type 'guess [word]'.\n"
                + "For example, to guess the word 'the' you would type 'guess the'.\n"
                + "To go back to edit a previous word, type 'back'. Once you think you have\n"
                + "the right phrase, type 'submit' to submit your answer. If you were wrong,\n"
                + "type 'back' to keep trying. If you were right, type 'bye' to disconnect.\n"
                + "To view this help message again at any point, type 'help'.\n"
                + "Now, please input a username by typing 'user [username]' (no spaces).\n");
        try {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                String output = handleRequest(line);
                if (output.equals("bye")) {
                    out.close();
                    in.close();
                } else{
                    out.println(output);
                }
            }
        } finally {
            out.close();
            in.close();
            sentence.closeWriter();
            System.err.println("client "+players+" disconnected.");
            synchronized (players){
                players--;
            }
        }
    }
  
    /**
     * Handler for client input, performing requested operations and returning an output message.
     * 
     * @param input message from client
     * @return message to client, or null if none
     * @throws IOException if the write-to file in sentence object is not found
     */
    private String handleRequest(String input) throws IOException {
        String regex = "(back)|(help)|(bye)|(submit)|(guess [a-zA-Z]+)|(user [a-zA-Z]+)";
        if ( ! input.matches(regex)) {
            // invalid input
            // TODO what should i actually be printing here?
            return "\nInvalid input. Make sure you're not typing a space after your guess\n"
                    + "or in your username. Type 'help' if you need the help instructions.\n";
        }
        String[] tokens = input.split(" ");
        if (tokens[0].equals("back")) {
            sentence.goBackAWord();
            return sentence.toString();
        } else if (tokens[0].equals("help")) {
            return "\nYou are trying to guess the common phrase or idiom one word at a time\n"
                    + "from the available letters. Every time you move on to a new word, the\n"
                    + "available letters will change, but you will always have the letters\n"
                    + "that you need at a given moment. To guess a word, type 'guess [word]'.\n"
                    + "For example, to guess the word 'the' you would type 'guess the'.\n"
                    + "To go back to edit a previous word, type 'back'. Once you think you have\n"
                    + "the right phrase, type 'submit' to submit your answer. If you were wrong,\n"
                    + "type 'back' to keep trying. If you were right, type 'bye' to disconnect.\n";
        } else if (tokens[0].equals("bye")) {
            return "bye";
        }else if (tokens[0].equals("submit")) {
            if(sentence.win()){
                return "\nRight! You win!\n";
            }
            return "\nWrong! Try again!\n";
        } else if(tokens.length > 1){
            if (tokens[0].equals("guess")) {
                String word = tokens[1];
                String indic = sentence.guessWord(word);
                if(indic.equals("invalid letters")){
                    return "\nSome of the letters you used in your guess were not in the available letters, try again.\n";
                } if(indic.equals("wrong size")){
                    return "\nThe word you guessed does not fit in the spaces. "
                            + "The current word has "+ sentence.curWordLen()+" letters in it, try again.\n";
                }
                return sentence.toString();
            } else if(tokens[0].equals("user")){
                String user = tokens[1];
                sentence.startGame(user);
                return sentence.toString();
            }
        }
        // Should never get here
        throw new UnsupportedOperationException();
    }

    /**
     * Start a WordGameServer using the given arguments.
     * @param args arguments (for now there are none)
     */
    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        try {
            runWordGameServer(port);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    /**
     * Start a WordGame running on the specified port, with a random phrase from the ones provided.
     * @param port The network port on which the server should listen, requires 0 <= port <= 65535.
     * @throws IOException if a network error occurs
     */
    public static void runWordGameServer(int port) throws IOException {
        List<String> s = Collections.synchronizedList(new ArrayList<String>());
        s.add("breath of fresh air");
        s.add("break a leg");
        s.add("eat my words");
        s.add("fits like a glove");
        s.add("from the ground up");
        s.add("fruits of your labor");
        s.add("great minds think alike");
        s.add("hold the phone");
        Random r = new Random();
        int randIndex = r.nextInt(s.size());
        Sentence newSentence = new Sentence(s.get(randIndex),"data.txt");
        WordGameServer server = new WordGameServer(newSentence, port);
        server.serve();
    }
}
