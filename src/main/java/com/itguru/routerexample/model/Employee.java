package com.itguru.routerexample.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Employee {

  @Id
  private String id;

  private String firstName;

  private String lastName;

  private String email;


}
