//
//package cropper;
//
//import java.applet.Applet;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Toolkit;
//import java.awt.Image;
//import java.net.URL;
//
//public class ImageCropper extends Applet {
//
//    /**
//     * Initialization method that will be called after the applet is loaded into
//     * the browser.
//     */
//    Image im=null;
//    public void init() {
//        // TODO start asynchronous download of heavy resources
//    }
//
//    // TODO overwrite start(), stop() and destroy() methods
//    
//    public void paint(Graphics g){
//        Graphics2D g2d=(Graphics2D)g;
//        this.setSize(400,640);
//        if(im==null)
//            im=loadImage("example.png");
//        g2d.drawImage(im, 200, 200, this);
//    }
//    
//    public Image loadImage(String path){
//        URL url=ImageCropper.class.getResource(path);
//        try{
//        im=Toolkit.getDefaultToolkit().getImage(url);
//        }
//        catch(Exception e){
//            System.out.println("Error: "+e.getMessage());
//        }
//        return im;
//    }
//    
//}
