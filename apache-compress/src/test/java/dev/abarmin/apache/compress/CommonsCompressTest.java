package dev.abarmin.apache.compress;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonsCompressTest {
    @Test
    void compress_shouldCreateArchiveAndReadEntries() throws Exception {
        final String fileContent = "Not compressed string";

        final Path content = Files.createTempFile("text", ".txt");
        Files.writeString(content, fileContent);
        final String readString = Files.readString(content);
        assertThat(readString).isEqualTo(fileContent);

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
                assertThat(nextEntry.getName()).isEqualTo(content.toFile().getName());
                final String entryContent = IOUtils.toString(archiveReader, StandardCharsets.UTF_8);
                assertThat(entryContent).isEqualTo(fileContent);
            }
        }
    }
}
