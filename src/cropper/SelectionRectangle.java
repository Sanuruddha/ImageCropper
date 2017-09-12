/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cropper;

import static com.sun.java.accessibility.util.AWTEventMonitor.addComponentListener;
import static cropper.SelectionPane.getScreenViewableBounds;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import static cropper.SelectionPane.getScreenViewableBounds;
import static cropper.SelectionPane.getScreenViewableBounds;
import static cropper.SelectionPane.getScreenViewableBounds;

/**
 *
 * @author shihan
 */
public class SelectionRectangle {
//    public static void main(String[] args) {
//        new SelectionRectangle();
//    }
    static JFrame frame = new JFrame("Image Cropper");
    BackgroundPane bp=null;
    
    public static void setWindowSize(Dimension d){
        frame.setPreferredSize(d);
    }
    
    public SelectionRectangle() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                
                //frame.setUndecorated(true);
                //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                
                try {
                    bp = new BackgroundPane(frame);
                } catch (IOException ex) {
                    Logger.getLogger(SelectionRectangle.class.getName()).log(Level.SEVERE, null, ex);
                }
                frame.add(bp,BorderLayout.CENTER);
                
                
                frame.add(new ControlPane(bp),BorderLayout.PAGE_END);
                
                
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

        });
    }
    
}




