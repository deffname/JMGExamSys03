package com.example.jmgexamsys03.entity.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class getEStuResDto {
    private long sid;
    private String sname;
    private String anspaper;

    public getEStuResDto(long sid, String sname, String anspaper) {
        this.sid = sid;
        this.sname = sname;
        this.anspaper = anspaper;
    }
}
