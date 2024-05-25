package dev.abarmin.jooq.basics;

import dev.abarmin.jooq.db.tables.records.PersonRecord;
import dev.abarmin.jooq.db.tables.records.TaskRecord;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Test;

import java.util.*;

import static dev.abarmin.jooq.db.Tables.PERSON;
import static dev.abarmin.jooq.db.Tables.TASK;

public class JooqJoinExampleTest extends ConnectionSupport {
    @Test
    void selectPeopleAndCountTasks() {
        withContext(dslContext -> {
            final Result<Record2<String, Integer>> records = dslContext.select(PERSON.NAME, DSL.count(TASK.ID))
                    .from(PERSON)
                    .innerJoin(TASK).on(TASK.PERSON_ID.eq(PERSON.ID))
                    .groupBy(PERSON.NAME)
                    .orderBy(DSL.count(TASK.ID).asc())
                    .fetch();

            System.out.println(records);
        });
    }

    @Test
    void selectPeopleWithTasks() {
        withContext(dslContext -> {
            /*
            select person.*, task.*
            from person
            inner join task on task.person_id = person.id
             */
            List<Field<?>> fields = new ArrayList<>();
            fields.addAll(Arrays.asList(PERSON.fields()));
            fields.addAll(Arrays.asList(TASK.fields()));

            Map<Integer, PersonWithTasks> people = new HashMap<>();
            final List<PersonWithTasks> records = dslContext.select(fields)
                    .from(PERSON)
                    .innerJoin(TASK).on(TASK.PERSON_ID.eq(PERSON.ID))
                    .orderBy(PERSON.ID.asc())
                    .fetch(record -> {
                        final PersonRecord personRecord = record.into(PERSON);
                        final Integer personId = personRecord.getId();

                        final PersonWithTasks personWithTasks = people.computeIfAbsent(personId, id -> new PersonWithTasks(
                                id,
                                personRecord.getName(),
                                new ArrayList<>()
                        ));

                        final TaskRecord taskRecord = record.into(TASK);
                        personWithTasks.addTask(new PersonTask(
                                taskRecord.getId(),
                                taskRecord.getSubject(),
                                taskRecord.getStatus()
                        ));

                        return personWithTasks;
                    });

            System.out.println(people.values());
        });
    }

    record PersonWithTasks(int id, String name, Collection<PersonTask> tasks) {
        void addTask(PersonTask task) {
            tasks.add(task);
        }
    }

    record PersonTask(int it, String subject, String status) {}
}
