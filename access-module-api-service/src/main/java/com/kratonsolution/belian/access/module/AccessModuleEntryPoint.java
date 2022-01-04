package com.kratonsolution.belian.access.module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableR2dbcAuditing
@EnableR2dbcRepositories
@EnableTransactionManagement
public class AccessModuleEntryPoint {

    public static void main(String[] args) {
        SpringApplication.run(AccessModuleEntryPoint.class, args);
    }
}
