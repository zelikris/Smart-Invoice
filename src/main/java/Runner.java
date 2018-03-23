import java.util.List;
import java.util.Map;

public class Runner {

    public static void main(String[] args) {
        // read invoiceNum from file names
        String invoicesDir = args[0];
        List<String> invoiceNums = ReadInvoiceFromFileName.readInvoiceNumsFromFilenames(invoicesDir);

        // read property name from pdf
        Map<String, String> invoicesToPropertyNames = ReadPdf.readPropertyNamesFromPdfs(invoicesDir);

        // convert .xlsx to .csv
        String excelPath = args[1];
        ExcelToCsv.excelToCsv(excelPath);

        // insert property name in csv
        ReadAndEditCsv.editCsv(invoicesToPropertyNames);
    }
}
