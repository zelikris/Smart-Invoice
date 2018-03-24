import java.util.Map;

public class Runner {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Follow this format dude: ");
            System.out.println("java -jar smart-invoice.jar <folder with all invoice pdfs> <excel file>");
        } else {
            String invoicesDir = args[0];
            String excelPath = args[1];

            // read invoiceNum and property name from pdf
            Map<String, String> invoicesToPropertyNames = ReadPdf.readPropertyNamesFromPdfs(invoicesDir);
            ExcelToCsv.excelToCsv(excelPath);
            ReadAndEditCsv.editCsv(invoicesToPropertyNames);
            CsvToXlsx.csvToXLSX();

            System.out.println("\n======================READ THIS=======================");
            System.out.println("Done!");
            System.out.println("Don't worry about the warnings");
            System.out.println("======================READ THIS=======================\n");
        }
    }
}
