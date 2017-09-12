/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cropper;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @author Lock'
 */
public class ControlPane extends JPanel {

    private JButton button;
    private int x1,x2,y1,y2;
    public ControlPane(BackgroundPane bp) {
        button = new JButton("Crop");
        this.add(button);
        SelectionPane sp=bp.getPane();
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    cropImage(bp,sp,x1,y1,x2,y2);
                } catch (AWTException ex) {
                    Logger.getLogger(ControlPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        button.addMouseListener(adapter);
    }
    
    public void cropImage(BackgroundPane bp,SelectionPane sp,int x1,int y1,int x2,int y2) throws AWTException{
        BufferedImage image=null;
        try
        {
            Robot robo=new Robot();
            
            Rectangle captureSize=sp.getBounds();
            
            System.out.println(captureSize.toString());

            robo.createScreenCapture(captureSize);
            image=bp.getImage();
            BufferedImage croppedImage = image.getSubimage(captureSize.x,captureSize.y, captureSize.width,captureSize.height);
            bp.repaintBackground(croppedImage);
            //ImageIO.write(image,"png",new File(imgAddress));
            bp.disposePane();
        }
        catch(Exception e){}
        
        
    }

}
