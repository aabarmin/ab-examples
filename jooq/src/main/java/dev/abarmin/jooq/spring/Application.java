package dev.abarmin.jooq.spring;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static dev.abarmin.jooq.db.tables.Person.*;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner runner(DSLContext context) {
        return args -> {
            Result<Record> records = context.select(PERSON.fields())
                    .from(PERSON)
                    .fetch();

            System.out.println(records);
        };
    }
}
