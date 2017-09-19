package cropper;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFImageWriter;



public class PDFToImage {

    private static final String PASSWORD = "-password";
    private static final String START_PAGE = "-startPage";
    private static final String END_PAGE = "-endPage";
    private static final String IMAGE_FORMAT = "-imageType";
    private static final String OUTPUT_PREFIX = "-outputPrefix";
    private static final String COLOR = "-color";
    private static final String RESOLUTION = "-resolution";

    /**
     * private constructor.
     */
    public PDFToImage() throws IOException {
        
    }
    
    public static BufferedImage converToImage(File file) throws IOException{
        String password = "";
        String pdfFile = null;
        String outputPrefix = null;
        String imageFormat = "jpg";
        int startPage = 1;
        int endPage = Integer.MAX_VALUE;
        String color = "rgb";
        int resolution;
        try {
            resolution = Toolkit.getDefaultToolkit().getScreenResolution();
        } catch (HeadlessException e) {
            resolution = 96;
        }
        
        
        if (pdfFile != null) {
            usage();
        } else {
//            if (outputPrefix == null) {
//                outputPrefix = pdfFile.substring(0, pdfFile.lastIndexOf('.'));
//            }

            PDDocument document = null;
            try {
                document = PDDocument.load(file.getAbsolutePath());

                //document.print();
                if (document.isEncrypted()) {
                    document.decrypt(password); //they supplied the wrong password
                    //they didn't supply a password and the default of "" was wrong.
                }
                int imageType = 24;
                if ("bilevel".equalsIgnoreCase(color)) {
                    imageType = BufferedImage.TYPE_BYTE_BINARY;
                } else if ("indexed".equalsIgnoreCase(color)) {
                    imageType = BufferedImage.TYPE_BYTE_INDEXED;
                } else if ("gray".equalsIgnoreCase(color)) {
                    imageType = BufferedImage.TYPE_BYTE_GRAY;
                } else if ("rgb".equalsIgnoreCase(color)) {
                    imageType = BufferedImage.TYPE_INT_RGB;
                } else if ("rgba".equalsIgnoreCase(color)) {
                    imageType = BufferedImage.TYPE_INT_ARGB;
                } else {
                    System.err.println("Error: the number of bits per pixel must be 1, 8 or 24.");
                    System.exit(2);
                }

                //Make the call
                PDFImageWriter imageWriter = new PDFImageWriter();
                boolean success = imageWriter.writeImage(document, imageFormat, password,
                        startPage, endPage, "test", imageType, resolution);
                if (!success) {
                    System.err.println("Error: no writer found for image format '"
                            + imageFormat + "'");
                    System.exit(1);
                }
            } catch (Exception e) {
                System.err.println(e);
            } finally {
                if (document != null) {
                    document.close();
                }
            }
        }
        return ImageIO.read(new File("C:/Users/shihan/Documents/NetBeansProjects/Cropper/test1.jpg"));
    }
    
    private static void usage() {
        System.err.println("Usage: java org.apache.pdfbox.PDFToImage [OPTIONS] <PDF file>\n"
                + "  -password  <password>          Password to decrypt document\n"
                + "  -imageType <image type>        (" + getImageFormats() + ")\n"
                + "  -outputPrefix <output prefix>  Filename prefix for image files\n"
                + "  -startPage <number>            The first page to start extraction(1 based)\n"
                + "  -endPage <number>              The last page to extract(inclusive)\n"
                + "  -color <string>                The color depth (valid: bilevel, indexed, gray, rgb, rgba)\n"
                + "  -resolution <number>           The bitmap resolution in dpi\n"
                + "  <PDF file>                     The PDF document to use\n"
        );
        System.exit(1);
    }

    private static String getImageFormats() {
        StringBuffer retval = new StringBuffer();
        String[] formats = ImageIO.getReaderFormatNames();
        for (int i = 0; i < formats.length; i++) {
            retval.append(formats[i]);
            if (i + 1 < formats.length) {
                retval.append(",");
            }
        }
        return retval.toString();
    }
}
