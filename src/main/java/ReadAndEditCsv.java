import com.opencsv.CSVWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadAndEditCsv {
    private static final String PATH = "src/main/resources/test.csv";

    public static void main(String[] args) {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim())
        ) {
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            List<String[]> records = new ArrayList<>();

            String match = "4111939-0001";
            String propertyMatch = "Mall of GA";

            CSVWriter writer = new CSVWriter(new FileWriter("src/main/resources/out_test.csv"));

            for (CSVRecord csvRecord : csvRecords) {
                String[] row = new String[5];

                //"INV. DATE","INVOICE#","P.O.#","INV. AMOUNT","PROPERTY NAME",
                String invoiceDate = csvRecord.get(0);
                String invoiceNum = csvRecord.get(1);
                String poNum = csvRecord.get(2);
                String invoiceAmount = csvRecord.get(3);
                String propertyName = csvRecord.get(4);

                if (invoiceNum.equals(match)) {
                    propertyName = propertyMatch;
                }

                row[0] = (invoiceDate);
                row[1] = (invoiceNum);
                row[2] = (poNum);
                row[3] = (invoiceAmount);
                row[4] = (propertyName);
                records.add(row);
            }

//            for (int i = 0; i < records.size(); i++) {
//                System.out.println(records.get(i)[4]);
//            }
            writer.writeAll(records);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
