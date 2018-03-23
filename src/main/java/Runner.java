import java.util.Map;

public class Runner {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Follow this format dude: ");
            System.out.println("java -jar smart-invoice.jar <folder with all invoice pdfs> <excel file>");
            System.exit(1);
        }
        String invoicesDir = args[0];
        String excelPath = args[1];

        // read invoiceNum and property name from pdf
        Map<String, String> invoicesToPropertyNames = ReadPdf.readPropertyNamesFromPdfs(invoicesDir);
        ExcelToCsv.excelToCsv(excelPath);
        ReadAndEditCsv.editCsv(invoicesToPropertyNames);

        System.out.println("Done!");
    }
}
