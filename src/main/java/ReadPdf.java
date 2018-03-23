
import java.awt.*;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PageRanges;
import javax.print.attribute.standard.Sides;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.interactive.viewerpreferences.PDViewerPreferences;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public final class ReadPdf {

    public static Map<String, String> readPropertyNamesFromPdfs(String pdfsPath) {
        File folder = new File(pdfsPath);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.out.println("Please provide a valid directory which contains all of the invoices!");
        }

        assert listOfFiles != null;
        Map<String, String> invoiceToPropertyName = new HashMap<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String fileName = file.getName();
                String invoiceNum = fileName.substring(8, 20);

                String filePath = file.getAbsolutePath();

                PDDocument document = null;
                try {
                    document = PDDocument.load( new File(filePath));
                    PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                    stripper.setSortByPosition( true );
                    Rectangle rect = new Rectangle( 270, 160, 275, 15 );
                    stripper.addRegion( "class1", rect );
                    PDPage firstPage = document.getPage(0);
                    stripper.extractRegions(firstPage);


                    String topTwoLines =  stripper.getTextForRegion( "class1" );
                    String[] split = topTwoLines.split("\\n");

                    String propertyName = split[0].split("\\r")[0];

                    invoiceToPropertyName.put(invoiceNum, propertyName);

                    // TODO: include second line if it contains property name
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (document != null) {
                        try {
                            document.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Unexpected file found while reading invoices: " + file.getName());
            }
        }
        return invoiceToPropertyName;
    }
}
