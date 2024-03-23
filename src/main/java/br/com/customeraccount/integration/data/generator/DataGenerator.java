package br.com.customeraccount.integration.data.generator;

import br.com.customeraccount.integration.data.model.CustomerModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;


public class DataGenerator {
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();

    public void generateAndWriteTestData() {
        String fileName = generateOutputFileName();
        int numberOfItems = 2;

        try {
            createOutputDirectory(); // Cria o diretório de saída

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

                for (int i = 0; i < numberOfItems; i++) {
                    CustomerModel customer = CustomerModel.createRandomCustomer();
                    writer.write(customer.getDocumentNumber() + ";" + customer.getEmail() + ";" + customer.getContract());
                    writer.write("|");
                }
                System.out.println("Test data generated successfully!");
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

    public static void main(String[] args) {
        DataGenerator dataGenerator = new DataGenerator();
        dataGenerator.generateAndWriteTestData();
    }
}