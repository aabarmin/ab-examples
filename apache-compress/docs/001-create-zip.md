# Create ZIP archive using Apache Commons Compress

To create an archive create an `ArchiveStreamFactory` and next add files by creating entries. 

```java
        final Path archiveFile = Files.createTempFile("archive", ".zip");
        try (final OutputStream outputStream = Files.newOutputStream(archiveFile);
             final ArchiveOutputStream archiver = new ArchiveStreamFactory()
                     .createArchiveOutputStream(ArchiveStreamFactory.ZIP, outputStream)) {

            final ArchiveEntry entry = archiver.createArchiveEntry(
                    content,
                    content.toFile().getName()
            );
            archiver.putArchiveEntry(entry);
            archiver.write(Files.readAllBytes(content));
            archiver.closeArchiveEntry();
        }
```

# Read files from archive

To read an archive first create a `BufferedReader` and next `ArchiveInputStream`:

```java
        try (final InputStream inputStream = Files.newInputStream(archiveFile);
             final BufferedInputStream bufferedStream = new BufferedInputStream(inputStream);
             final ArchiveInputStream<? extends ArchiveEntry> archiveReader =
                     new ArchiveStreamFactory()
                             .createArchiveInputStream(bufferedStream)) {

            while (true) {
                final ArchiveEntry nextEntry = archiveReader.getNextEntry();
                if (nextEntry == null) {
                    break;
                }
                final String entryContent = IOUtils.toString(archiveReader, StandardCharsets.UTF_8);
            }
        }
```