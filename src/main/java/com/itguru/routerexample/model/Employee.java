package com.itguru.routerexample.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {

  @Id
  private String id;

  private String firstName;

  private String lastName;

  private String email;


}
