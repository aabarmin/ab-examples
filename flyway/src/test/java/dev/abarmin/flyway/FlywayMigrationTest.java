package dev.abarmin.flyway;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class FlywayMigrationTest {
    static final String DB_URL = "jdbc:h2:file:./build/test-plain";
    static final String DB_USER = "sa";
    static final String DB_PASSWORD = "sa";

    @BeforeEach
    void removeExistingDatabase() throws Exception {
        Files.deleteIfExists(Path.of("./build/test-plain.mv.db"));
        Files.deleteIfExists(Path.of("./build/test-plain.trace.db"));
    }

    @Test
    void checkMigration() throws Exception {
        Collection<String> beforeMigration = query("show tables", rs -> {
            return rs.getString(1);
        });

        assertThat(beforeMigration).isEmpty();

        Flyway flyway = Flyway.configure()
                .dataSource(DB_URL, DB_USER, DB_PASSWORD)
                .load();

        flyway.migrate();

        Collection<String> afterMigration = query("show tables", rs -> {
            return rs.getString(1);
        });

        assertThat(afterMigration).isNotEmpty();
        assertThat(afterMigration).contains("PERSON");

        Collection<String> names = query("select * from PERSON", rs -> {
            return rs.getString("NAME");
        });

        assertThat(names).contains("Axel", "Mr. Foo");
    }

    Connection getConnection() throws Exception {
        return DriverManager
                .getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    <T> Collection<T> query(String query, WrappedFunction<ResultSet, T> mapper) throws Exception {
        try (Connection connection = getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            Collection<T> result = new ArrayList<>();
            try (resultSet) {
                while (resultSet.next()) {
                    result.add(mapper.apply(resultSet));
                }
            }
            return result;
        }
    }

    interface WrappedFunction<F, T> {
        T apply(F from) throws Exception;
    }
}
