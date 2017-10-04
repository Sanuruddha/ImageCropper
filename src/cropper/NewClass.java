package cropper;

import java.awt.image.BufferedImage;
import java.io.File;
import org.jpedal.examples.images.ConvertPagesToImages;
import org.jpedal.exception.PdfException;

public class NewClass {
    
    
    public static BufferedImage convert(File file) throws PdfException {
        BufferedImage image=null;
        ConvertPagesToImages extract = new ConvertPagesToImages(file.getAbsolutePath());
        if (extract.openPDFFile()) {
            int pageCount = extract.getPageCount();

            image = extract.getPageAsImage(1, true);
        }

        extract.closePDFfile();
        return image;
    }
}