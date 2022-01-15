import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);  // Documents - имя файла для вставки
        System.out.println("path:");
        File file = new File(scanner.nextLine());
        scanner.close();
//        File file = new File("Documents");
        file = new File(file.getAbsolutePath());

        if (!file.isFile()) {
            System.out.println("there is no file in the specified path");
            return;
        }

        File validDocuments = new File(file.getParent() + File.separator + "Valid documents.txt");
        File invalidDocuments = new File(file.getParent() + File.separator + "Invalid documents.txt");

        try {
            if (validDocuments.createNewFile() && invalidDocuments.createNewFile()) {
                System.out.println("report-files created");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        try (FileWriter writerValidDoc = new FileWriter(validDocuments);
             FileWriter writerInvalidDoc = new FileWriter(invalidDocuments)) {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String doc = scanner.nextLine();
                if (isValidDocument(doc)) {
                    writerValidDoc.write(doc + '\n');
                } else {
                    String docReport = printDocReport(doc);
                    writerInvalidDoc.write(doc + " - " + docReport + '\n');
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

    }

    public static boolean isValidDocument(String doc) {
        if (doc.length() == 15 && isBeginWith(doc)) {
            return true;
        }
        return false;
    }

    public static boolean isBeginWith(String doc) {
        if (doc.indexOf("docnum") == 0 || doc.indexOf("сontract") == 0) {
            return true;
        }
        return false;
    }

    public static String printDocReport(String doc) {
        String docReport = "";
        int cnt = 0;

        if (doc.length() < 15) {
            docReport = "Name less than 15 characters";
            cnt++;
        } else if (doc.length() > 15){
            docReport = "Name more than 15 characters";
            cnt++;
        }

        if (!isBeginWith(doc)) {
            if (cnt == 1) {
                docReport += ", name does not start with \"docum\" or \"сontract\".";
            } else {
                docReport = "Name does not start with \"docum\" or \"сontract\".";
            }
        } else {
            docReport += ".";
        }

        return docReport;
    }

}
