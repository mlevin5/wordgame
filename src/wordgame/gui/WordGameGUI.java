/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordgame.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.XMLFormatter;

import javax.swing.AbstractButton;
import javax.swing.Timer;

import wordgame.Word;

/**
 *
 * @author mango
 */
public class WordGameGUI extends javax.swing.JFrame {
    
    private final static Logger LOGGER = Logger.getLogger(WordGameGUI.class.getName());
    private final static String DEFAULT_FILENAME = "data.xml";
    private final static int TRAINING_TRIALS = 3;
    private final static int MAX_TIME = 60;
    private final static int PENALTY = 10;
    private final static int REWARD = 5;
    
    private int wordIndex = 0;
    private final List<javax.swing.JButton> buttons = Collections.synchronizedList(new ArrayList<javax.swing.JButton>());
    private final List<Word> words = Collections.synchronizedList(new ArrayList<Word>());
    private Word word;
    private boolean trainingDone = false;
    private boolean realDone = false;
    private int counter = 0;
    private Timer Timer;
    private int score = 0;
    
    /**
     * Creates new form WordGameGUI
     * @throws IOException 
     */
    public WordGameGUI() throws IOException {
        FileHandler fh;  
        // This block configure the logger with handler and formatter  
        
        String filename = "Data";
        File f = new File(filename+".log");
        int j = 1;
        while(f.exists() && !f.isDirectory()){
            filename += Integer.toString(j);
            f = new File(filename+".log");
            j++;
        }
        
        fh = new FileHandler(filename+".log");
        
        LOGGER.addHandler(fh);
        XMLFormatter formatter = new XMLFormatter();//SimpleFormatter();  
        fh.setFormatter(formatter);  

        LOGGER.info("Started a new game"); 
        
        // useful info on using logger:
        
        // https://logging.apache.org/log4j/1.2/manual.html
        
        // https://www.loggly.com/ultimate-guide/parsing-java-logs/
        
        words.add(new Word("face","abcdefgh"));
        words.add(new Word("nope","ejklmnop"));
        words.add(new Word("suck","suckyoet"));
        words.add(new Word("suck","suckyoet"));
        words.add(new Word("heck","heckyout"));
        words.add(new Word("meow","meowsuct"));
        words.add(new Word("fuck","fuckyoet"));
        words.add(new Word("cane","caneyobt"));
        word = words.get(0);

        
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        
        buttons.add(jButton1);
        buttons.add(jButton2);
        buttons.add(jButton3);
        buttons.add(jButton4);
        buttons.add(jButton5);
        buttons.add(jButton6);
        buttons.add(jButton7);
        buttons.add(jButton8);
        List<String> availableLetters = word.getAvailableLetters();
        for(int i=0; i<availableLetters.size(); i++){
            buttons.get(i).setText(availableLetters.get(i));
            buttons.get(i).addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        buttonActionPerformed(evt);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        interludePanel = new javax.swing.JLayeredPane();
        startButton = new javax.swing.JButton();
        startPrompt = new javax.swing.JLabel();
        gamePanel = new javax.swing.JPanel();
        wordLabel = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        skipButton = new javax.swing.JButton();
        timerLabel = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        startButton.setText("Start!");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    startButtonActionPerformed(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        startPrompt.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        startPrompt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startPrompt.setText("Welcome to the Word Game");

        wordLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 48)); // NOI18N
        wordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        wordLabel.setText(word.toStringGUI());

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    backButtonActionPerformed(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        skipButton.setText("Skip");
        skipButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    skipButtonActionPerformed(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        timerLabel.setFont(new java.awt.Font("Courier New", 0, 24)); // NOI18N
        timerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timerLabel.setText(Integer.toString(MAX_TIME));

        Timer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int time = MAX_TIME-counter;
                timerLabel.setText(Integer.toString(time));
                if(MAX_TIME == counter)
                    try {
                        showEndOfGame();
         
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }     
                counter++;
            }
        });
        
        

        scoreLabel.setFont(new java.awt.Font("Courier New", 0, 24)); // NOI18N
        scoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreLabel.setText("score: 0");

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(wordLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(gamePanelLayout.createSequentialGroup()
                                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(gamePanelLayout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(gamePanelLayout.createSequentialGroup()
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(gamePanelLayout.createSequentialGroup()
                                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(skipButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addComponent(timerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scoreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scoreLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(wordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(skipButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        interludePanel.setLayer(startButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        interludePanel.setLayer(startPrompt, javax.swing.JLayeredPane.DEFAULT_LAYER);
        interludePanel.setLayer(gamePanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout interludePanelLayout = new javax.swing.GroupLayout(interludePanel);
        interludePanel.setLayout(interludePanelLayout);
        interludePanelLayout.setHorizontalGroup(
            interludePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(interludePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startPrompt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, interludePanelLayout.createSequentialGroup()
                .addContainerGap(102, Short.MAX_VALUE)
                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105))
            .addGroup(interludePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(interludePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        interludePanelLayout.setVerticalGroup(
            interludePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, interludePanelLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(startPrompt, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addGap(80, 80, 80)
                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(interludePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(interludePanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(interludePanel)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(interludePanel)
        );
        gamePanel.setVisible(false);
        timerLabel.setVisible(false);
        scoreLabel.setVisible(false);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void showStartReal() throws IOException{
        trainingDone = true;
        LOGGER.info("staring the real game");
        gamePanel.setVisible(false);
        startPrompt.setVisible(true);
        startButton.setVisible(true);
        
        timerLabel.setVisible(true);
        scoreLabel.setVisible(true);
    }
    private void showEndOfGame() throws IOException{
        LOGGER.info("game ended");
        startPrompt.setText("<html><center>Out of Time!<br>Your final<br>score was: "+score+"</html>");
        startButton.setText("Exit");
        gamePanel.setVisible(false);
        startPrompt.setVisible(true);
        startButton.setVisible(true);
    }
    private void showEndOfGameBeatTheTimer() throws IOException{
        LOGGER.info("game ended");
        startPrompt.setText("<html><center>You completed them all!<br>Your final<br>score was: "+score+"</html>");
        startButton.setText("Exit");
        gamePanel.setVisible(false);
        startPrompt.setVisible(true);
        startButton.setVisible(true);
    }
    private void showWordGame() throws IOException{
        //writer.write(System.currentTimeMillis()+"\n"+sentence.toStringSimple()+"\nnew game begins\n"); 
        gamePanel.setVisible(true);
        startPrompt.setVisible(false);
        startButton.setVisible(false);
    }
    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//GEN-FIRST:event_startButtonActionPerformed
        if(!trainingDone){
            // do the training data
            showWordGame();
            LOGGER.info("Started a new word "+word.getSentence());
            startPrompt.setText("next is the real data");
            
        } else if(!realDone){
            // do the real data
            showWordGame();
            Timer.start();
            LOGGER.info("Started a new word "+word.getSentence());
            realDone = true;
            
        }else{
            LOGGER.info("finished game"); 
            System.exit(0);
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//GEN-FIRST:event_backButtonActionPerformed
        int backWord = word.letterToGuess();
        String removedLetter = word.goBackALetter();
        if(!word.win()){
            if(backWord == 1){
                //writer.write(System.currentTimeMillis()+"\n"+word.toStringSimple()+"\nwent back letter: "+removedLetter+"\n");
                //writer.write("went back to start on word #"+word.wordToGuess()+"\n");
                wordLabel.setText(word.toStringGUI());
                for(int j = 0; j<buttons.size(); j++){
                    buttons.get(j).setText(" ");
                }
                int i = 0;
                for(String newLetter : word.letterGuessesLeft()){
                    buttons.get(i).setText(newLetter);
                    i++;
                }
            }else{
                wordLabel.setText(word.toStringGUI());
                //writer.write(System.currentTimeMillis()+"\n"+word.toStringSimple()+"\nwent back letter: "+removedLetter+"\n");
                for(javax.swing.JButton button : buttons){
                    if(button.getText().equals(" ")){
                        button.setText(removedLetter);
                        LOGGER.info("removed letter "+removedLetter); 
                        break;
                    }
                }
            }
        }
    }//GEN-LAST:event_backButtonActionPerformed

    private void skipButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//GEN-FIRST:event_skipButtonActionPerformed
        AbstractButton button = (AbstractButton) evt.getSource();
        String label = button.getText();
        wordIndex++;
        
        if(label.equals("Skip")){
            if (word.isSolvable()){
                LOGGER.info("skipped word "+word.getSentence()+" and was wrong");
                if(trainingDone){score -= PENALTY;}       
            }else{
                LOGGER.info("skipped word "+word.getSentence()+" and was right");
                if(trainingDone){score += REWARD;}
                }
        }
        if(label.equals("Next!")){
            if(trainingDone){ 
                score += REWARD; 
            }
            skipButton.setText("Skip");
        }
        scoreLabel.setText("score: "+Integer.toString(score));
        
        if(wordIndex == words.size()){
            showEndOfGameBeatTheTimer();
        }else{
            
            word = words.get(wordIndex);
            wordLabel.setText(word.toStringGUI());
            List<String> availableLetters = word.getAvailableLetters();
            for(int i=0; i<availableLetters.size(); i++){
                buttons.get(i).setText(availableLetters.get(i));
            }
            if(wordIndex == TRAINING_TRIALS){
                showStartReal();
            } else if(wordIndex == words.size()){
                showEndOfGame();
            } else{
                LOGGER.info("Started a new word "+word.getSentence());
            }
            // make sure this is just training data 
        }


        
         
    }//GEN-LAST:event_skipButtonActionPerformed

    
    
    private void buttonActionPerformed(java.awt.event.ActionEvent evt) throws IOException, InterruptedException {
        AbstractButton button = (AbstractButton) evt.getSource();
        String letter = button.getText();
    
        if(!letter.equals(" ") && !word.full()){
           
           String indic = word.guessLetter(letter); 
           System.out.println(word.toStringGUI());
           wordLabel.setText(word.toStringGUI());
           button.setText(" ");
           //writer.write(System.currentTimeMillis()+"\n"+word.toStringSimple()+"\nguessed letter: "+letter+"\n");
           LOGGER.info("guessed letter "+letter);
           if(indic.equals("next word")){
  
               //writer.write("guessed word #"+(word.wordToGuess()-1)+": "+word.lastWordCheck()+"\n");
               List<String> availableLetters = word.getAvailableLetters();
               for(int i = 0; i<availableLetters.size(); i++){
                   buttons.get(i).setText(availableLetters.get(i));
               }
           } 

        }
        if(word.full()){
            if(word.win()){
                LOGGER.info("guessed word "+word.getSentence()+" and was right");
              //writer.write(System.currentTimeMillis()+"\n"+word.toStringSimple()+"WINNER!\n"); 
                wordLabel.setText(word.toStringGUI("green"));
                skipButton.setText("Next!");
                
                
            }else{
                //writer.write(System.currentTimeMillis()+"\n"+sentence.toStringSimple()+"\nLOSER!\n");
                wordLabel.setText(word.toStringGUI("red"));
                LOGGER.info("guessed word "+word.getSentence()+" and was wrong");
                /*word.goBackAWord();
                List<String> availableLetters = word.getAvailableLetters();
                for(int i=0; i<availableLetters.size(); i++){
                    buttons.get(i).setText(availableLetters.get(i));
                }*/
              //  wordLabel.setText(word.toStringGUI());
            }
           // writer.write("guessed word #"+(word.wordToGuess()-1)+": "+word.lastWordCheck()+"\n");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WordGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WordGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WordGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WordGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new WordGameGUI().setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JLayeredPane interludePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JButton skipButton;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel startPrompt;
    private javax.swing.JLabel timerLabel;
    private javax.swing.JLabel wordLabel;
    // End of variables declaration//GEN-END:variables
}
