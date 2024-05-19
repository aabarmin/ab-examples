# Make queries with Jooq

As simple as it only possible when table classes are generated: 

```java
DSLContext dslContext = DSL.using(connection);

Result<Record> records = dslContext.select(PERSON.fields())
        .from(PERSON)
        .fetch();
```

Insert records: 

```java
int affected = dslContext.insertInto(PERSON)
        .set(PERSON.NAME, "John")
        .set(PERSON.ID, 30)
        .execute();
```

Delete records: 

```java
int deleted = dslContext.deleteFrom(PERSON)
        .where(PERSON.NAME.eq("John"))
        .execute();
```