/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cropper;

import static com.idrsolutions.pdf.acroforms.xfa.XScriptNode.page;
import java.awt.image.BufferedImage;
import java.io.File;
import org.jpedal.examples.images.ConvertPagesToImages;
import org.jpedal.exception.PdfException;
import static org.omg.IOP.IORHelper.extract;

/**
 *
 * @author shihan
 */
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