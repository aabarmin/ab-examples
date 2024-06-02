package dev.abarmin.jooq.basics;

import lombok.SneakyThrows;
import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.function.Consumer;

public abstract class ConnectionSupport {
    private static final String DB_URL = "jdbc:h2:file:./build/test";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "sa";

    @BeforeEach
    void setUp() throws Exception {
        Files.deleteIfExists(Path.of("./build/test.mv.db"));

        Flyway.configure()
                .dataSource(DB_URL, DB_USER, DB_PASSWORD)
                .load()
                .migrate();
    }

    void withContext(Consumer<DSLContext> consumer) {
        withConnection(connection -> {
            final DSLContext dslContext = DSL.using(connection);
            consumer.accept(dslContext);
        });
    }

    @SneakyThrows
    void withConnection(Consumer<Connection> consumer) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            consumer.accept(connection);
        }
    }
}
