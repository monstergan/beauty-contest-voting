package com.voter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.voter.mapper"})
public class BeautyContestVotingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeautyContestVotingApplication.class, args);
    }

}
