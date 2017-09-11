/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cropper;

import com.sun.xml.internal.bind.v2.model.core.Adapter;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.EventListener;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static jdk.nashorn.internal.objects.NativeArray.map;

public class ImageViewer {

    BufferedImage img = null;
    int imageHeight = 0,  imageWidth = 0;
    ImageIcon imgIcon;
    JFrame frame=new JFrame();
    
    public ImageViewer() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                try {
                    img = ImageIO.read(getClass().getResource("/dog.jpg"));
                    imageHeight = img.getHeight();
                    imageWidth = img.getWidth();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }

                paint(img,imageWidth,imageHeight);
                

            }
        });
        
        
    }
    
    public void paint(Image img,int width,int height){
         
             imgIcon = new ImageIcon(img);
                JLabel lbl = new JLabel();
                lbl.setIcon(imgIcon);
                frame.setSize(width, height);
                frame.getContentPane().add(lbl,BorderLayout.CENTER);
                frame.pack();
                frame.setVisible(true);
                
        }

    
   
}
