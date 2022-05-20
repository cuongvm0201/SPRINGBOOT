package com.example.demo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.demo.model.City;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest{
    private String keyword;
    private City city;
}