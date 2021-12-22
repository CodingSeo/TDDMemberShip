package com.codingseo.tddmembership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TddMemberShipApplication {

    public static void main(String[] args) {
        SpringApplication.run(TddMemberShipApplication.class, args);
    }

}
