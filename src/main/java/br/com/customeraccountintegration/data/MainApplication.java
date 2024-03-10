package br.com.customeraccountintegration.data;

import br.com.customeraccountintegration.data.generator.DataGenerator;

public class MainApplication {
  public static void main(String[] args) {
    DataGenerator dataGenerator = new DataGenerator();
    dataGenerator.generateAndWriteTestData();
  }
}

