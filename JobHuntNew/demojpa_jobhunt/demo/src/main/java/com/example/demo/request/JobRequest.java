package com.example.demo.request;


import com.example.demo.model.City;

import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
  private String id;
  private String emp_id;
  private String title;
  private String description;
  private City city;
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequest{

                private String id;

                private String emp_id;

                @NotBlank(message = "Title cannot null") 
                private String title;

                @NotBlank(message = "Description cannot null") 
                private String description;

                @NotNull(message = "City cannot null") 
                private City city;
              }