# Using Flyway migrations

Idea of migration is that DB changes are stored in plain files under version control. When a new migration file appears, 
the application applies the migration that moves the database to the next state.

Flyway stores migrations in the `src/main/resources/db/migration` folder by default. Migration files should follow naming
strategy like `V1__name_of_migration.sql`, pay attention to two underscores. 

## Basic usage without a framework

To execute DB migrations using Flyway it's necessary to create an instance of `Flyway` object and call a `migrate` method
on it: 

```java
        Flyway flyway = Flyway.configure()
                .dataSource(DB_URL, DB_USER, DB_PASSWORD)
                .load();

        flyway.migrate();
```

## Migration using Spring Framework

When Spring Framework founds a DB driver and Flyway dependencies in classpath, it automatically applies migrations
from the `src/main/resources/db/migration` folder. Can be disabled using `spring.flyway.enabled=false` property. 

## Migration using Gradle

The following configuration is required to migrate H2 database: 

```groovy
buildscript {
    dependencies {
        classpath 'com.h2database:h2:2.2.220'
    }
}

plugins {
    id 'org.flywaydb.flyway' version '10.0.0'
}

flyway {
    url = "jdbc:h2:file:${projectDir}/build/test-gradle"
    user = 'sa'
    password = 'sa'
}
```