
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public final class ReadPdf {

    public static Map<String, String> readPropertyNamesFromPdfs(String pdfsPath) {
        java.util.logging.Logger.getLogger("org.apache.pdfbox").setLevel(java.util.logging.Level.SEVERE);

        File folder = new File(pdfsPath);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.out.println("\n======================READ THIS=======================");
            System.out.println("Please provide a valid directory which contains all of the invoices!");
            System.out.println("Follow this format dude: ");
            System.out.println("java -jar smart-invoice.jar <folder with all invoice pdfs> <excel file>");
            System.out.println("======================READ THIS=======================\n");
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
                    System.out.println("\n======================READ THIS=======================");
                    System.out.println("Damn... looks like there was an issue reading from one of the pdf files. " +
                            "Call me and tell me to look at ReadPdf.java");
                    System.out.println("\n======================READ THIS=======================\n");
                    e.printStackTrace();
                } finally {
                    if (document != null) {
                        try {
                            document.close();
                        } catch (IOException e) {
                            System.out.println("\n======================READ THIS=======================");
                            System.out.println("There was an issue closing one of the pdfs I was reading...");
                            System.out.println("======================READ THIS=======================\n");

                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("\n======================READ THIS=======================");
                System.out.println("Unexpected file found while reading invoices: " + file.getName());
                System.out.println("I'll just skip it for now");
                System.out.println("======================READ THIS=======================\n");
            }
        }
        return invoiceToPropertyName;
    }
}
