package com.meteor.springbatch.chunkpractice.practice01;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Member {

    private Long idx;
    private String nickName;
    private String userName;
    private Long point;
    private LocalDate createDate;

}
