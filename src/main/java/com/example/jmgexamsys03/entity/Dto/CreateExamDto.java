package com.example.jmgexamsys03.entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExamDto {
    private String starttime;
    private String endtime;
    private String examname;
    private String exampaper;
}
