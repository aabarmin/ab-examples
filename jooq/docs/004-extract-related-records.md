# Extract related records

To extract related records it is necessary to use `.innerJoin`-like constructions. The main complexity
is that in case of one-to-many relationship it will be necessary to deduplicate records manually. 

```java
var fields = new ArrayList<Field<?>>();
fields.addAll(Arrays.asList(PERSON.fields()));
fields.addAll(Arrays.asList(TASK.fields()));

var people = new HashMap<Integer, PersonWithTasks>();
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
            final PersonTask personTask = new PersonTask(
                    taskRecord.getId(),
                    taskRecord.getSubject(),
                    taskRecord.getStatus()
            );

            personWithTasks.addTask(personTask);

            return personWithTasks;
        });
```