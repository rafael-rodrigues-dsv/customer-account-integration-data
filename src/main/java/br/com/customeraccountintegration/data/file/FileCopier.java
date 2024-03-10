package br.com.customeraccountintegration.data.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

public class FileCopier {
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
