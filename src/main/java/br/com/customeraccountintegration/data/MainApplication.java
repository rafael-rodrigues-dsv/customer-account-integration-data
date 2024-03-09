package br.com.customeraccountintegration.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

public class MainApplication {
  private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

  public static void main(String[] args) {
    String fileName = generateOutputFileName();
    int numberOfItems = 1;

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
      for (int i = 0; i < numberOfItems; i++) {
        String line = generateRandomLine();
        writer.write(line);
        writer.newLine();
      }
      System.out.println("Test data generated successfully!");

      // Copie o arquivo gerado para o diretório resources
      copyFileToResources(fileName);
    } catch (IOException e) {
      System.err.println("Error writing to file: " + e.getMessage());
    }
  }

  private static String generateOutputFileName() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    String dateFormatted = dateFormat.format(new Date());
    return "files/output/output_data_" + dateFormatted + ".txt";
  }

  private static String generateRandomLine() {
    Random random = new Random();
    long documentNumber = 10000000000L + (long) (random.nextDouble() * 90000000000L);
    String email = "customer" + (random.nextInt(1000) + 1) + "@email.com";
    String decodedString = readBase64FromFile("files/input/input_base64.txt");
    return documentNumber + ";" + email + ";" + decodedString + "|";
  }

  private static String readBase64FromFile(String filePath) {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(
        MainApplication.class.getClassLoader().getResourceAsStream(filePath), StandardCharsets.UTF_8))) {
      StringBuilder result = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        result.append(line);
      }

      return result.toString();
    } catch (IOException e) {
      e.printStackTrace(); // Handle the exception according to your needs
      return ""; // Return an empty string or handle the error as needed
    }
  }

  private static void copyFileToResources(String sourceFilePath) {
    try (InputStream sourceStream = new FileInputStream(sourceFilePath);
         OutputStream destinationStream = new FileOutputStream("src/main/resources/" + sourceFilePath)) {

      byte[] buffer = new byte[4096];
      int bytesRead;

      while ((bytesRead = sourceStream.read(buffer)) != -1) {
        destinationStream.write(buffer, 0, bytesRead);
      }
    } catch (IOException e) {
      e.printStackTrace(); // Handle the exception according to your needs
    }
  }
}
