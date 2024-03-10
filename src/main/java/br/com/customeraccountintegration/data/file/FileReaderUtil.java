package br.com.customeraccountintegration.data.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileReaderUtil {
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
