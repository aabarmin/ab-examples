# Generate classes with Jooq plugin

Add the following to your `build.gradle`:

```groovy
buildscript {
    dependencies {
        classpath 'com.h2database:h2:2.2.220'
    }
}

plugins {
    id 'org.flywaydb.flyway' version '10.13.0'
    id "org.jooq.jooq-codegen-gradle" version "3.19.8"
}

flyway {
    url = "jdbc:h2:file:${projectDir}/build/test-gradle"
    user = 'sa'
    password = 'sa'
}

jooq {
    configuration {
        jdbc {
            driver = 'org.h2.Driver'
            url = "jdbc:h2:file:${projectDir}/build/test-gradle"
            user = 'sa'
            password = 'sa'
        }
        generator {
            database {
                name = 'org.jooq.meta.h2.H2Database'
                inputSchema = 'PUBLIC'
                excludes = """
					flyway_schema_history
					"""
            }
            target {
                packageName = 'dev.abarmin.jooq.db'
                directory = 'src/main/java'
            }
        }
    }
}

tasks.named("jooqCodegen") {
    dependsOn("flywayMigrate")
}
```

To start the process execute: 

```shell
./gradlew jooqCodegen
```