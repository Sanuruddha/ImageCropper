/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cropper;

import static cropper.SelectionRectangle.background;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Lock'
 */
public class ControlPane extends JPanel {

    private JButton cropButton, saveasButton, open;
    private int x1, x2, y1, y2;

    public ControlPane(BackgroundPane bp) {
        cropButton = new JButton("Crop");
        saveasButton = new JButton("Save");
        open = new JButton("Open");
        this.add(cropButton);
        this.add(saveasButton);
        this.add(open);
        SelectionPane sp = bp.getPane();
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    cropImage(bp, sp, x1, y1, x2, y2);
                } catch (AWTException ex) {
                    Logger.getLogger(ControlPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        cropButton.addMouseListener(adapter);

        MouseAdapter adapter1 = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    saveImage(bp, sp, x1, y1, x2, y2);
                } catch (AWTException ex) {
                    Logger.getLogger(ControlPane.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ControlPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        saveasButton.addMouseListener(adapter1);

        MouseAdapter adapter2 = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    openImage(bp, sp);
                } catch (IOException ex) {
                    Logger.getLogger(ControlPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        open.addMouseListener(adapter2);
    }

    public void openImage(BackgroundPane bp, SelectionPane sp) throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("C:/Users/shihan/Desktop/new.png"));
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            
            System.out.println(chooser.getSelectedFile().getAbsolutePath());
            background = ImageIO.read(new File(chooser.getSelectedFile().getAbsolutePath()));
            
        }
        System.out.println(Integer.toString(background.getHeight())+" "+Integer.toString(background.getWidth()));
        Dimension d=new Dimension(background.getWidth(),background.getHeight());
        SelectionRectangle.setWindowSize(d);
        bp.repaint();

    }

    public void saveImage(BackgroundPane bp, SelectionPane sp, int x1, int y1, int x2, int y2) throws AWTException, IOException, IOException {
        BufferedImage image;
        Robot robo = new Robot();
        String imgAddress = "C:/Users/shihan/Desktop/new.png";

        image = SelectionRectangle.background;
        ImageIO.write(image, "png", new File(imgAddress));
    }

    public void cropImage(BackgroundPane bp, SelectionPane sp, int x1, int y1, int x2, int y2) throws AWTException {
        BufferedImage image = null;
        try {
            Robot robo = new Robot();

            Rectangle captureSize = sp.getBounds();

            System.out.println(captureSize.toString());

            robo.createScreenCapture(captureSize);
            image = SelectionRectangle.background;
            SelectionRectangle.background = image.getSubimage(captureSize.x, captureSize.y, captureSize.width, captureSize.height);

            //ImageIO.write(image,"png",new File(imgAddress));
            SelectionRectangle.restart();
        } catch (Exception e) {
        }

    }

}
