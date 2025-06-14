package dev.abarmin.spring.jdbc;

import dev.abarmin.spring.jdbc.domain.Person;
import dev.abarmin.spring.jdbc.query.FindPeopleQuery;
import dev.abarmin.spring.jdbc.query.FindPersonByIdQuery;
import dev.abarmin.spring.jdbc.query.InsertPersonQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoggingApplicationRunner implements ApplicationRunner {
    private final FindPersonByIdQuery findPerson;
    private final FindPeopleQuery findPeople;
    private final InsertPersonQuery insertPerson;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final Optional<Person> object = findPerson.findPerson(1);
        System.out.println("Person: " + object);

        final Person inserted = insertPerson.insertPerson(new Person("Test person"));
        System.out.println("Inserted: " + inserted);

        findPeople.findAll().forEach(p -> {
            System.out.println("Person: " + p);
        });
    }
}
