package br.com.customeraccountintegration.data.model;

import br.com.customeraccountintegration.data.file.FileReaderUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {
  private String documentNumber;
  private String email;
  private String contract;

  public static CustomerModel createRandomCustomer() {
    Random random = new Random();
    long documentNumber = 10000000000L + (long) (random.nextDouble() * 90000000000L);
    String email = "customer" + (random.nextInt(1000) + 1) + "@email.com";

    return CustomerModel.builder()
        .documentNumber(String.valueOf(documentNumber))
        .email(email)
        .contract(FileReaderUtil.readBase64FromFile("files/input/input_base64.txt"))
        .build();
  }
}
