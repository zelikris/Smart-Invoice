import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;


public class ExcelToCsv {

    private static String echoAsCSV(Sheet sheet) {
        Row row;
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                strBuilder.append("\"");
                String cellVal = row.getCell(j).toString();
                if (j == 3 && cellVal.contains("-")) {
                    cellVal = cellVal.split("-")[1];
                }
                strBuilder.append(cellVal);
                strBuilder.append("\",");
            }
            strBuilder.append("\n");
        }
        return strBuilder.toString();
    }

    private static void output(String toPrint) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("result.csv"));
        writer.write(toPrint);
        writer.close();
    }

    public static void excelToCsv(String excelFilePath) {
        java.util.logging.Logger.getLogger("org.apache").setLevel(java.util.logging.Level.OFF);

        InputStream inp = null;
        try {
            inp = new FileInputStream(excelFilePath); //src/main/resources/sample_excel.xlsx
            Workbook wb = WorkbookFactory.create(inp);

            StringBuilder strBuilder = new StringBuilder();
            for(int i=0;i<wb.getNumberOfSheets();i++) {
                strBuilder.append(echoAsCSV(wb.getSheetAt(i)));
            }
            output(strBuilder.toString());
        } catch (InvalidFormatException | IOException ex) {
            System.out.println("\n======================READ THIS=======================");
            System.out.println("Looks like the excel file you provided is garbage. Get wit it");
            System.out.println("Follow this format dude: ");
            System.out.println("java -jar smart-invoice.jar <folder with all invoice pdfs> <excel file>");
            System.out.println("======================READ THIS=======================\n");
            Logger.getLogger(ExcelToCsv.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inp.close();
            } catch (IOException ex) {
                System.out.println("\n======================READ THIS=======================");
                System.out.println("Looks like the excel file you provided is garbage. Get wit it");
                System.out.println("Follow this format dude: ");
                System.out.println("java -jar smart-invoice.jar <folder with all invoice pdfs> <excel file>");
                System.out.println("======================READ THIS=======================\n");
                Logger.getLogger(ExcelToCsv.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}