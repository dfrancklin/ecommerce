package br.com.company.ecommerce.utils;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import lombok.NonNull;

public class CSVWriter implements Closeable {

    private BufferedWriter writer;

    public CSVWriter(@NonNull String path, @NonNull String filename) throws FileNotFoundException {
        File file = new File(path + File.separator + filename);
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
    }

    public void write(@NonNull String[] fields) throws IOException {
        String line = Arrays.stream(fields)
                .map(this::escape)
                .collect(Collectors.joining(","));

        writer.write(line);
        writer.newLine();
    }

    private String escape(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Input value cannot be null");
        }

        String escapedValue = value.replaceAll("\\R", " ");

        if (value.contains(",") || value.contains("\"") || value.contains("'")) {
            value = value.replace("\"", "\"\"");
            escapedValue = "\"" + value + "\"";
        }

        return escapedValue;
    }

    @Override
    public void close() throws IOException {
        writer.flush();
        writer.close();
    }

}
