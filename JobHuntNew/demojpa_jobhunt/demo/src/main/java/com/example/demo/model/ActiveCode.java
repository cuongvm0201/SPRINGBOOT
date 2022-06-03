package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ActiveCode {
    @Id
    private String id;
    private String user_id;
    private String code;
}
