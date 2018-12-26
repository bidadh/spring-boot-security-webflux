package com.ideabaker.samples.springsecurity.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class Movie {

  @Id
  private Long id;
  private String title;
  private String genre;

}
