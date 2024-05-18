package dev.abarmin.jooq.basics;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static dev.abarmin.jooq.db.tables.Person.PERSON;
import static org.assertj.core.api.Assertions.assertThat;

class JooqGeneratedTablesTest extends ConnectionSupport {
    @Test
    void selectRecords() {
        withConnection(connection -> {
            DSLContext dslContext = DSL.using(connection);

            Result<Record> records = dslContext.select(PERSON.fields())
                    .from(PERSON)
                    .fetch();

            System.out.println(records);

            Result<Record1<Integer>> idOnly = dslContext.select(PERSON.ID)
                    .from(PERSON)
                    .fetch();

            System.out.println(idOnly);

            Result<Record> sortedRecords = dslContext.select(PERSON.fields())
                    .from(PERSON)
                    .orderBy(PERSON.NAME.desc())
                    .fetch();

            System.out.println(sortedRecords);
        });
    }

    @Test
    void insertThenDeleteRecords() {
        withConnection(connection -> {
            DSLContext dslContext = DSL.using(connection);

            int affected = dslContext.insertInto(PERSON)
                    .set(PERSON.NAME, "John")
                    .set(PERSON.ID, 30)
                    .execute();

            assertThat(affected).isEqualTo(1);

            List<Map<String, Serializable>> people = dslContext.select(PERSON.fields())
                    .from(PERSON)
                    .where(PERSON.NAME.eq("John"))
                    .fetch(record -> Map.of(
                            "id", record.get(PERSON.ID),
                            "name", record.get(PERSON.NAME)
                    ));

            assertThat(people).contains(Map.of(
                    "id", 30,
                    "name", "John"
            ));

            int deleted = dslContext.deleteFrom(PERSON)
                    .where(PERSON.NAME.eq("John"))
                    .execute();

            assertThat(deleted).isEqualTo(1);
        });
    }
}
