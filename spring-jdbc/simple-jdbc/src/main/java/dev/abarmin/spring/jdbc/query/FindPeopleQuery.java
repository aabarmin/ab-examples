package dev.abarmin.spring.jdbc.query;

import dev.abarmin.spring.jdbc.domain.Person;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FindPeopleQuery extends MappingSqlQuery<Person> {

    public FindPeopleQuery(DataSource dataSource) {
        super(
                dataSource,
                "SELECT id, name, created_at FROM people"
        );
        compileInternal();
    }
    @Override
    protected Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Person(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getTimestamp("created_at").toInstant()
        );
    }

    public List<Person> findAll() {
        return execute();
    }
}
