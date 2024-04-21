# Archiving and reading archives with plain Java

Create an instance of `ZipOutputStream` to archive files: 

```java
        try (final OutputStream outputStream = Files.newOutputStream(archiveFile);
             final ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {

            zipOutputStream.putNextEntry(new ZipEntry(content.toFile().getName()));
            zipOutputStream.write(Files.readAllBytes(content));
            zipOutputStream.closeEntry();
        }
```

Create an instance of `ZipFile` to read files from archive: 

```java
        final ZipFile zipFile = new ZipFile(archiveFile.toFile());
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            final ZipEntry entry = entries.nextElement();
            // ...
        }
```