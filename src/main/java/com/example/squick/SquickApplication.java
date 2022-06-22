package com.example.squick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SquickApplication {

    public static void main(String[] args) {
        SpringApplication.run(SquickApplication.class, args);
    }
}
