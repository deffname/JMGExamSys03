package com.example.jmgexamsys03.entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteStudentDto {
    private Integer[] sidlist;
    private long eid;
    
}
