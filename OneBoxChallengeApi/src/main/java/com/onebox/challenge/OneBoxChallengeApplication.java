package com.onebox.challenge;

import com.fasterxml.jackson.databind.Module;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;


//ComponentScan(
//        basePackages = {"com.onebox.challenge", "com.onebox.challenge.api" , "com.onebox.challenge.configuration", "com.onebox.challenge.service", "com.onebox.challenge.storage.provider"},
//nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
//)
@SpringBootApplication(scanBasePackages = "com.onebox.challenge")
public class OneBoxChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneBoxChallengeApplication.class, args);
    }

    @Bean(name = "com.onebox.challenge.OneBoxChallengeApplication.jsonNullableModule")
    public Module jsonNullableModule() {
        return new JsonNullableModule();

    }

}