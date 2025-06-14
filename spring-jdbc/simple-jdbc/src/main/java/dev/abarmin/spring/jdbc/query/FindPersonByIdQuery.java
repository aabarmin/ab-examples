package dev.abarmin.spring.jdbc.query;

import dev.abarmin.spring.jdbc.domain.Person;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

@Component
public class FindPersonByIdQuery extends MappingSqlQuery<Person> {

    public FindPersonByIdQuery(DataSource dataSource) {
        super(
                dataSource,
                "SELECT id, name, created_at FROM people WHERE id = ?"
        );
        declareParameter(new SqlParameter("id", Types.INTEGER));
        compile();
    }

    @Override
    protected Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Person(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getTimestamp("created_at").toInstant()
        );
    }

    public Optional<Person> findPerson(int id) {
        return Optional.ofNullable(findObject(id));
    }
}
