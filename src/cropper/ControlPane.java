package cropper;

import static cropper.Window.background;
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
import java.io.IOException;
import static java.lang.System.in;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.jpedal.exception.PdfException;


public class ControlPane extends JPanel {

    private JButton cropButton, saveasButton, open;
    private int x1, x2, y1, y2;
    
    //costructor
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
                } catch (COSVisitorException ex) {
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
                } catch (PdfException ex) {
                    Logger.getLogger(ControlPane.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        open.addMouseListener(adapter2);
    }

    public void openImage(BackgroundPane bp, SelectionPane sp) throws IOException, PdfException {
        boolean isPdf = false;
        String fileName;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image or PDF", "jpg", "pdf");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File("C:/Users/shihan/Desktop/new.png"));

        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileName = chooser.getSelectedFile().getName();
            switch (fileName.substring(fileName.lastIndexOf('.') + 1)) {
                case "pdf":
                    isPdf = true;
                    break;
                case "jpg":
                    isPdf = false;
                    break;
                default:
                    break;
            }
            if (isPdf) {
                //background = PDFToImage.converToImage(new File(chooser.getSelectedFile().getAbsolutePath()));
                background=PDFToImage.converToImage(new File(chooser.getSelectedFile().getAbsolutePath()));
            } else {
                background = ImageIO.read(new File(chooser.getSelectedFile().getAbsolutePath()));
            }
        }
        
        Dimension d = new Dimension(background.getWidth(), background.getHeight());
        Window.setWindowSize(d);
        bp.setPreferredSize(d);
        bp.revalidate();
        bp.repaint();

    }

    public void saveImage(BackgroundPane bp, SelectionPane sp, int x1, int y1, int x2, int y2) throws AWTException, IOException, IOException, COSVisitorException {
        BufferedImage image;
        Robot robo = new Robot();
        Boolean flag = false;
        String fileName;
        boolean isPdf = false;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("C:/Users/shihan/Desktop/"));
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            fileName = chooser.getSelectedFile().getName();
            switch (fileName.substring(fileName.lastIndexOf('.') + 1)) {
                case "pdf":
                    isPdf = true;
                    break;
                case "jpg":
                    isPdf = false;
                    break;
                default:
                    break;
            }
            image = Window.background;
            if (!isPdf) {
                ImageIO.write(image, "png", new File(chooser.getSelectedFile().getAbsolutePath()));
                
            } else {
                PDDocument document = new PDDocument();
                float width = image.getWidth();
                float height = image.getHeight();
                PDPage page = new PDPage(new PDRectangle(width, height));
                document.addPage(page);
                PDXObjectImage img = new PDJpeg(document, image);
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.drawImage(img, 0, 0);
                contentStream.close();
                in.close();

                document.save(chooser.getSelectedFile().getAbsolutePath());
                document.close();
            }

        }

        JFrame successWindow = new JFrame("Successful");
        successWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        successWindow.setSize(250, 100);
        successWindow.add(new JLabel("Successfully Saved", JLabel.CENTER));
        successWindow.setLocationRelativeTo(null);
        successWindow.setVisible(true);

    }

    public void cropImage(BackgroundPane bp, SelectionPane sp, int x1, int y1, int x2, int y2) throws AWTException {
        BufferedImage image = null;
        try {
            Robot robo = new Robot();

            Rectangle captureSize = sp.getBounds();

            robo.createScreenCapture(captureSize);
            image = Window.background;
            Window.background = image.getSubimage(captureSize.x, captureSize.y, captureSize.width, captureSize.height);

            //ImageIO.write(image,"png",new File(imgAddress));
            Window.restart();
        } catch (Exception e) {
        }

    }

}
