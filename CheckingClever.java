import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class demonstrates the usage of the CleverSIDC data structure by reading data from a file,
 * adding it to the data structure, retrieving keys, and writing them to another file using PrintWriter.
 */
public class CheckingClever {
    public static void main(String[] args) {
        CleverSIDC cleverSIDC = new CleverSIDC();

        try (BufferedReader reader = new BufferedReader(new FileReader("NASTA_test_file2.txt"))) {
            String line;
            // Read data from the file and add it to the CleverSIDC data structure
            while ((line = reader.readLine()) != null) {
                long key = Long.parseLong(line.trim());
                HelperStudent student = new HelperStudent("TestFirstName", "TestLastName",key);
                cleverSIDC.add(key, student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long[] k = cleverSIDC.allKeys();// Retrieve all keys from CleverSIDC
        try (PrintWriter writer = new PrintWriter(new FileWriter("Retrieved.txt"))) {
            // Write keys to another file using PrintWriter
            for (long l : k) {
                writer.println(l);
            }
        } catch (IOException e) {
            System.out.println("Thrown exception while writing to file.");
        }
    }
}
