package wordgame.gui;

import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/** This is an hybrid application/applet.  It can be run
 * as either a JFrame or JApplet. */
public class WordGameApp extends JApplet {

    /** Construct the GUI for an applet. */
    public void init() {
        try {
            SwingUtilities.invokeAndWait( new Runnable() {
                public void run() {
                    NewJFrame gui;
                    try {
                        gui = new NewJFrame();
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
                NewJFrame gui;
                try {
                    gui = new NewJFrame();
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