package com.example.jmgexamsys03.entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeExamDto {
    private int eid;
    private String starttime;
    private String endtime;
    private String exampaper;
}
