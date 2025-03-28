package com.capitole.inditex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.capitole.inditex.infrastructure")
@EntityScan("com.capitole.inditex.infrastructure")
public class PricingServiceApplication {
  public static void main(String[] args){
    SpringApplication.run(PricingServiceApplication.class, args);
  }
}
