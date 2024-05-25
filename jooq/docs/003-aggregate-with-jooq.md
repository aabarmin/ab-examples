# Aggregations with Jooq

Ideally, would be better to generate supplementary classes first and next use them in queries like this: 

```java
dslContext.select(PERSON.NAME, DSL.count(TASK.ID))
    .from(PERSON)
    .innerJoin(TASK).on(TASK.PERSON_ID.eq(PERSON.ID))
    .groupBy(PERSON.NAME)
    .orderBy(DSL.count(TASK.ID).asc())
    .fetch();
```