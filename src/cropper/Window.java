/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cropper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Window {

    static JFrame frame = new JFrame("Image Cropper");
    static BackgroundPane bp = null;
    static ControlPane cp = null;
    public static BufferedImage background;
    static private int height, width;
    public static JScrollPane jScrollPane;
    public static JPanel container = null;

    public Window() throws IOException {
        init();
    }
//dispose all components

    public static void disposeAll() {
        bp.disposeAll();
        frame.remove(bp);
        frame.remove(cp);
        frame.getContentPane().remove(jScrollPane);

    }

    
    public static void init() {
        
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
                //main frame 
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                
                //jscroll container
                
                container = new JPanel();
                
                
                jScrollPane = new JScrollPane(container);

                jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                frame.getContentPane().add(jScrollPane);

                try {
                    bp = new BackgroundPane(frame);
                } catch (IOException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //scrollable background pane
                container.add(bp, BorderLayout.CENTER);

                cp = new ControlPane(bp);

                frame.add(cp, BorderLayout.PAGE_END);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

        });

    }

    public static void setWindowSize(Dimension d) {
        frame.setPreferredSize(d);
    }
    
    //dispose all and reinit
    public static void restart() {
        disposeAll();
        init();

    }
}
