package dev.abarmin.spring.jdbc.query;

import dev.abarmin.spring.jdbc.domain.Person;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

@Component
public class InsertPersonQuery {

    private final SimpleJdbcInsert jdbcInsert;

    public InsertPersonQuery(DataSource dataSource) {
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("people")
                .usingGeneratedKeyColumns("id")
                .usingColumns("name", "created_at");
    }

    public Person insertPerson(Person person) {
        final int generatedId = jdbcInsert
                .execute(new BeanPropertySqlParameterSource(person));
        return person.withId(generatedId);
    }
}
