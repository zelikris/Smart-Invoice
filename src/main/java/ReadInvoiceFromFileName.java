//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReadInvoiceFromFileName {
//
//    public static List<String> readInvoiceNumsFromFilenames(String path) {
//        File folder = new File(path); //src/main/resources/invoices
//        File[] listOfFiles = folder.listFiles();
//
//        if (listOfFiles == null) {
//            System.out.println("Please provide a valid directory which contains all of the invoices!");
//        }
//        assert listOfFiles != null;
//        List<String> invoices = new ArrayList<>();
//        for (File file : listOfFiles) {
//            if (file.isFile()) {
//                String fileName = file.getName();
//                String invoiceNum = fileName.substring(8, 20);
//                invoices.add(invoiceNum);
//                //TODO: assert invoice num is in this format: #######-####
//
//            } else {
//                System.out.println("Unexpected file found while reading invoice names: " + file.getName());
//            }
//        }
//        return invoices;
//    }
//}
