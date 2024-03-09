package br.com.customeraccountintegration.data;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

public class MainApplication {
  public static void main(String[] args) {
    DataGenerator dataGenerator = new DataGenerator();
    dataGenerator.generateAndWriteTestData();
  }
}

class DataGenerator {
  private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

  public void generateAndWriteTestData() {
    String fileName = generateOutputFileName();
    int numberOfItems = 1;

    try {
      createOutputDirectory(); // Cria o diretório de saída

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        for (int i = 0; i < numberOfItems; i++) {
          String line = generateRandomLine();
          writer.write(line);
          writer.newLine();
        }
        System.out.println("Test data generated successfully!");

        // Copie o arquivo gerado para o diretório resources
        FileCopier.copyFileToResources(fileName);
      }
    } catch (IOException e) {
      System.err.println("Error writing to file: " + e.getMessage());
    }
  }

  private String generateOutputFileName() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    String dateFormatted = dateFormat.format(new Date());
    return "src/main/resources/files/output/output_data_" + dateFormatted + ".txt";
  }

  private void createOutputDirectory() throws IOException {
    Path outputDir = Paths.get("src/main/resources/files/output/");
    Files.createDirectories(outputDir);
  }

  private String generateRandomLine() {
    Random random = new Random();
    long documentNumber = 10000000000L + (long) (random.nextDouble() * 90000000000L);
    String email = "customer" + (random.nextInt(1000) + 1) + "@email.com";
    String decodedString = FileReaderUtil.readBase64FromFile("files/input/input_base64.txt");
    return documentNumber + ";" + email + ";" + decodedString + "|";
  }
}

class FileReaderUtil {
  public static String readBase64FromFile(String filePath) {
    try (InputStream inputStream = FileReaderUtil.class.getClassLoader().getResourceAsStream(filePath)) {
      if (inputStream != null) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
          StringBuilder result = new StringBuilder();
          String line;

          while ((line = reader.readLine()) != null) {
            result.append(line);
          }

          return result.toString();
        }
      } else {
        // Tenta ler como caminho absoluto
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
          StringBuilder result = new StringBuilder();
          String line;

          while ((line = reader.readLine()) != null) {
            result.append(line);
          }

          return result.toString();
        }
      }
    } catch (IOException e) {
      e.printStackTrace(); // Handle the exception according to your needs
      return "";
    }
  }
}

class FileCopier {
  public static void copyFileToResources(String sourceFilePath) {
    try (InputStream sourceStream = new FileInputStream(sourceFilePath);
         OutputStream destinationStream = new FileOutputStream("src/main/resources/" + Paths.get(sourceFilePath).getFileName())) {

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
