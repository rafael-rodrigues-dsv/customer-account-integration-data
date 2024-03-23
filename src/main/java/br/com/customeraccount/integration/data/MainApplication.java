package br.com.customeraccount.integration.data;

import br.com.customeraccount.integration.data.generator.DataGenerator;

public class MainApplication {
    public static void main(String[] args) {
        DataGenerator dataGenerator = new DataGenerator();
        dataGenerator.generateAndWriteTestData();
    }
}

