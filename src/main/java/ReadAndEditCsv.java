import com.opencsv.CSVWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReadAndEditCsv {
    private static final String PATH = "result.csv";

    public static void editCsv(Map<String, String> invoiceToProperty) {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim())
        ) {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            List<String[]> records = new ArrayList<>();
            CSVWriter writer = new CSVWriter(new FileWriter(PATH));

            String[] headerRow = new String[5];
            headerRow[0] = "INV. DATE";
            headerRow[1] = "INVOICE#";
            headerRow[2] = "P.O.#";
            headerRow[3] = "INV. AMOUNT";
            headerRow[4] = "PROPERTY NAME";
            records.add(headerRow);

            for (CSVRecord csvRecord : csvRecords) {
                String[] row = new String[5];

                //"INV. DATE","INVOICE#","P.O.#","INV. AMOUNT","PROPERTY NAME",
                String invoiceDate = csvRecord.get(0);
                String invoiceNum = csvRecord.get(1);
                String poNum = csvRecord.get(2);
                String invoiceAmount = csvRecord.get(3);
                String propertyName = csvRecord.get(4);

                String newPropertyName = invoiceToProperty.get(invoiceNum);
                if (newPropertyName != null) {
                    propertyName = newPropertyName;
                }

                row[0] = (invoiceDate);
                row[1] = (invoiceNum);
                row[2] = (poNum);
                row[3] = (invoiceAmount);
                row[4] = (propertyName);
                records.add(row);
            }
            writer.writeAll(records);
            writer.close();
        } catch (IOException e) {
            System.out.println("\n======================READ THIS=======================");
            System.out.println("Looks like there was an issue with the file I'm trying to output.");
            System.out.println("\n======================READ THIS=======================\n");
            e.printStackTrace();
        }
    }
}
