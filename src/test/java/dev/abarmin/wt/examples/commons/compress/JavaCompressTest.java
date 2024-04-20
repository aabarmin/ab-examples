package dev.abarmin.wt.examples.commons.compress;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaCompressTest {
    @Test
    void zip_shouldCreateArchiveAndReadEntries() throws Exception {
        final String fileContent = "Not compressed string";

        final Path content = Files.createTempFile("text", ".txt");
        Files.writeString(content, fileContent);
        final String readString = Files.readString(content);
        assertThat(readString).isEqualTo(fileContent);

        final Path archiveFile = Files.createTempFile("test", ".zip");
        try (final OutputStream outputStream = Files.newOutputStream(archiveFile);
             final ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {

            zipOutputStream.putNextEntry(new ZipEntry(content.toFile().getName()));
            zipOutputStream.write(Files.readAllBytes(content));
            zipOutputStream.closeEntry();
        }

        final ZipFile zipFile = new ZipFile(archiveFile.toFile());
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            final ZipEntry entry = entries.nextElement();
            assertThat(entry.getName()).isEqualTo(content.toFile().getName());

            try (final InputStream entryStream = zipFile.getInputStream(entry)) {
                final String entryContent = IOUtils.toString(entryStream, StandardCharsets.UTF_8);
                assertThat(entryContent).isEqualTo(fileContent);
            }
        }
    }
}
