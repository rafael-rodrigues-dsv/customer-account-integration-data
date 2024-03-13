package br.com.customeraccountintegration.data.generator;

import br.com.customeraccountintegration.data.file.FileReaderUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

public class DataGenerator {
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
        //FileCopier.copyFileToResources(fileName);
      }
    } catch (IOException e) {
      System.err.println("Error writing to file: " + e.getMessage());
    }
  }

  private String generateOutputFileName() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    String dateFormatted = dateFormat.format(new Date());
    return "src/main/resources/files/output/generated_data_" + dateFormatted + ".txt";
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
