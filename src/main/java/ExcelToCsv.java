import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class ExcelToCsv {

    public static String echoAsCSV(Sheet sheet) {
        Row row = null;
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                strBuilder.append("\"");
                strBuilder.append(row.getCell(j));
                strBuilder.append("\",");
            }
            strBuilder.append("\n");
        }
        return strBuilder.toString();
    }

    public static void output(String toPrint) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("test.csv"));
        writer.write(toPrint);
        writer.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        InputStream inp = null;
        try {
            inp = new FileInputStream("src/main/resources/sample_excel.xlsx");
            Workbook wb = WorkbookFactory.create(inp);

            StringBuilder strBuilder = new StringBuilder();
            for(int i=0;i<wb.getNumberOfSheets();i++) {
                System.out.println(wb.getSheetAt(i).getSheetName());
                strBuilder.append(echoAsCSV(wb.getSheetAt(i)));
            }
            output(strBuilder.toString());
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ExcelToCsv.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelToCsv.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExcelToCsv.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inp.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelToCsv.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}