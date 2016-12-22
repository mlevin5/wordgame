package wordgame.gui;

import java.io.IOException;

import javax.swing.*;


/** This is an hybrid application/applet.  It can be run
 * as either a JFrame or JApplet. */
public class SentenceGameApplet extends JApplet {

    /**
     * default serial version
     */
    private static final long serialVersionUID = 1L;

    /** Construct the GUI for an applet. */
    public void init() {
        try {
            SwingUtilities.invokeAndWait( new Runnable() {
                public void run() {
                    SentenceGameGUI gui;
                    try {
                        gui = new SentenceGameGUI();
                        setContentPane(gui.getPanel());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /** Launch the GUI in a frame. */
   public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override 
            public void run() {
                SentenceGameGUI gui;
                try {
                    gui = new SentenceGameGUI();
                    gui.pack();
                    gui.setLocationByPlatform(true);
                    gui.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}