package dev.abarmin.jooq.basics;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JooqBasicExampleTest extends ConnectionSupport {
    @Test
    void simpleQuery() throws Exception {
        withConnection(connection -> {
            DSLContext dslContext = DSL.using(connection);

            List<Map<String, Object>> people = dslContext.fetch("select * from PERSON")
                    .map(record -> Map.of(
                            "id", record.get("ID"),
                            "name", record.get("NAME")
                    ));

            assertThat(people).contains(
                    Map.of("id", 1, "name", "Axel"),
                    Map.of("id", 2, "name", "Mr. Foo")
            );
        });
    }

    @Test
    void insertRecordThenDelete() {
        withConnection(connection -> {
            DSLContext dslContext = DSL.using(connection);

            dslContext
                    .insertInto(DSL.table("PERSON"))
                    .set(DSL.field("ID"), 10)
                    .set(DSL.field("NAME"), "Another name")
                    .execute();

            List<Map<String, Object>> records = dslContext.selectFrom("PERSON")
                    .where(DSL.field("NAME").eq("Another name"))
                    .fetch(record -> Map.of(
                            "id", record.get("ID"),
                            "name", record.get("NAME")
                    ));

            assertThat(records).containsExactly(Map.of("id", 10, "name", "Another name"));

            int affected = dslContext.delete(DSL.table("PERSON"))
                    .where(DSL.field("NAME").eq("Another name"))
                    .execute();

            assertThat(affected).isEqualTo(1);
        });
    }
}
