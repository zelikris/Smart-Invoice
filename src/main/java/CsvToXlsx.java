import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class CsvToXlsx {
    public static void csvToXLSX() {
        try {
            String csvFileAddress = "result.csv"; //csv file address
            String xlsxFileAddress = "result.xlsx"; //xlsx file address
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("sheet1");
            String currentLine=null;
            int rowNum=0;
            BufferedReader br = new BufferedReader(new FileReader(csvFileAddress));
            while ((currentLine = br.readLine()) != null) {
                String str[] = currentLine.split(",");
                rowNum++;
                XSSFRow currentRow=sheet.createRow(rowNum);
                for(int i=0;i<str.length;i++){
                    currentRow.createCell(i).setCellValue(str[i]);
                }
            }
            br.close();

            FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            fileOutputStream.close();

            File csv = new File(csvFileAddress);
            String deletionResult = csv.delete() ? "CSV deleted!" :"Failed to delete CSV!";
            System.out.println(deletionResult);

        } catch (Exception ex) {
            System.out.println("There was an issue converting from csv to xlsx...");
            ex.printStackTrace();
        }
    }
}
