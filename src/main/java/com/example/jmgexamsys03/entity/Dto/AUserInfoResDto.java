package com.example.jmgexamsys03.entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AUserInfoResDto {
    private long uid;
    private String uname;
    private String urole;
}
