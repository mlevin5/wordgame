/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordgame.gui;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractButton;

import wordgame.Sentence;

/**
 *
 * @author mango
 */
public class SentenceGameGUI extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_FILENAME = "data.txt";
    
    private List<Sentence> sentences = Collections.synchronizedList(new ArrayList<Sentence>());
    private final List<javax.swing.JButton> buttons = Collections.synchronizedList(new ArrayList<javax.swing.JButton>());
    private final List<Sentence> finalSentences = Collections.synchronizedList(new ArrayList<Sentence>());
    private final BufferedWriter writer;
    private Sentence sentence;
    /**
     * Creates new form SentenceGameGUI
     */
    public SentenceGameGUI()throws IOException{
        this.writer = new BufferedWriter(new FileWriter(DEFAULT_FILENAME, true));
        List<String> phrases = new ArrayList<String>();
        phrases.add("breath of fresh air");
        phrases.add("break a leg");
        phrases.add("eat my words");
        phrases.add("fits like a glove");
        phrases.add("from the ground up");
        phrases.add("fruits of your labor");
        phrases.add("great minds think alike");
        phrases.add("hold the phone");
        for(String sentence : phrases){
            sentences.add(new Sentence(sentence));
            finalSentences.add(new Sentence(sentence));
        }
        Random r = new Random();
        int randIndex = r.nextInt(sentences.size());
        sentence = sentences.remove(randIndex);
        writer.write("\nNEW SESSION\n\n");
        writer.write(System.currentTimeMillis()+"\n"+sentence.toStringSimple()+"\nnew game begins\n");
        
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        buttons.add(jButton1);
        buttons.add(jButton2);
        buttons.add(jButton3);
        buttons.add(jButton4);
        buttons.add(jButton5);
        buttons.add(jButton6);
        
        /// ayayyaa
        
        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(sentence.toStringGUI());
        
        jPanel1 = new javax.swing.JPanel();
        
        List<String> availableLetters = sentence.getAvailableLetters();

        int i = 0;
        for(String newLetter : availableLetters){
            buttons.get(i).setText(newLetter);
            buttons.get(i).addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try {
                        buttonActionPerformed(evt);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            i++;
        }
        jPanel1 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Word Game");
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
 
            }
        });

        jButton11.setText("Back");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jButtonBackActionPerformed(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        jButton12.setText("Submit");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jButtonSubmitActionPerformed(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jButton7 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        jButton7.setText("Play again!");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    jButtonNewGameActionPerformed(evt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("YOU WON!!!");
        
        // yayayyya
        
        
        
        initComponents();
    }


    private void initComponents() {

       



        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                .addGap(55, 55, 55))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLayeredPane1.setLayer(jButton7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addGap(67, 67, 67))
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
            .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jLabel3.setVisible(false);
        jButton7.setVisible(false);
        pack();
    }

    private void winCase() throws IOException{
        jPanel1.setVisible(false);
        jLabel3.setVisible(true);
        jButton7.setVisible(true);
    }
    
    private void newGameCase() throws IOException{
        writer.write(System.currentTimeMillis()+"\n"+sentence.toStringSimple()+"\nnew game begins\n");
        jPanel1.setVisible(true);
        jLabel3.setVisible(false);
        jButton7.setVisible(false);
    }
    
    // generalize to all buttons
    private void buttonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {                                         
        // add letter to blank phrase
        AbstractButton button = (AbstractButton) evt.getSource();
        String letter = button.getText();
        if(!letter.equals(" ") && !sentence.full()){
           String indic = sentence.guessLetter(letter); 
           jLabel2.setText(sentence.toStringGUI());
           button.setText(" ");
           writer.write(System.currentTimeMillis()+"\n"+sentence.toStringSimple()+"\nguessed letter: "+letter+"\n");
           if(indic.equals("next word")){
               writer.write("guessed word #"+(sentence.wordToGuess()-1)+": "+sentence.lastWordCheck()+"\n");
               List<String> availableLetters = sentence.getAvailableLetters();
               for(int i = 0; i<availableLetters.size(); i++){
                   buttons.get(i).setText(availableLetters.get(i));
               }
           } 

        }
        if(sentence.full()){
            writer.write("guessed word #"+(sentence.wordToGuess()-1)+": "+sentence.lastWordCheck()+"\n");
        }
    }
    
    // BACK button
    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        // do i want to go back a word or back a letter?
        int backWord = sentence.letterToGuess();
        String removedLetter = sentence.goBackALetter();

        if(backWord == 1){
            writer.write(System.currentTimeMillis()+"\n"+sentence.toStringSimple()+"\nwent back letter: "+removedLetter+"\n");
            writer.write("went back to start on word #"+sentence.wordToGuess()+"\n");
            jLabel2.setText(sentence.toStringGUI());
            for(int j = 0; j<buttons.size(); j++){
                buttons.get(j).setText(" ");
            }
            int i = 0;
            for(String newLetter : sentence.letterGuessesLeft()){
                buttons.get(i).setText(newLetter);
                i++;
            }
        }else{
            jLabel2.setText(sentence.toStringGUI());
            writer.write(System.currentTimeMillis()+"\n"+sentence.toStringSimple()+"\nwent back letter: "+removedLetter+"\n");
            for(javax.swing.JButton button : buttons){
                if(button.getText().equals(" ")){
                    button.setText(removedLetter);
                    break;
                }
            }
        }
        
    }
// SUBMIT button
    private void jButtonSubmitActionPerformed(java.awt.event.ActionEvent evt) throws IOException {//GEN-FIRST:event_jButton12ActionPerformed
        // either you win! or you lose!
        if(sentence.win()){
            writer.write(System.currentTimeMillis()+"\n"+sentence.toStringSimple()+"WINNER!\n");
            if(sentences.isEmpty()){
                for(int i=0; i<finalSentences.size();i++){
                    sentences.add(new Sentence (finalSentences.get(i).getSentence()));
                }
                jLabel3.setText("<html><center>You beat<br>the Word Game!</html>");
                jButton7.setText("Start Over?");
            }
            winCase();
            
        }else{
            jLabel2.setText("<html><center>YOU LOSE!<br>hit back<br>to try<br>again</html>");
            writer.write(System.currentTimeMillis()+"\n"+sentence.toStringSimple()+"\nLOSER!\n");
        }
        
    }
    
    private void jButtonNewGameActionPerformed(java.awt.event.ActionEvent evt) throws IOException{
        Random r = new Random();
        int randIndex = r.nextInt(sentences.size());
        sentence = sentences.remove(randIndex);
        
        jLabel2.setText(sentence.toStringGUI());
        
        List<String> availableLetters = sentence.getAvailableLetters();
        int i = 0;
        for(String newLetter : availableLetters){
            buttons.get(i).setText(newLetter);
            i++;
        }
        newGameCase();
    }

    public javax.swing.JPanel getPanel(){
        return jPanel1;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SentenceGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SentenceGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SentenceGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SentenceGameGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new SentenceGameGUI().setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;

}
