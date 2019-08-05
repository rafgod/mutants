package com.mercadolibre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories("com.mercadolibre.repositories")
public class MutantProjectApplication {

    public static void main(String[] args) {
	SpringApplication.run(MutantProjectApplication.class, args);
    }

}
