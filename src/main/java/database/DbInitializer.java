package main.java.database;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;


public class DbInitializer {

    public static void runSqlResource(Connection conn, String resourceFile) throws Exception {

        // e.g. resourceFile = "schema.sql" -> database/schema.sql
        Path sqlFilePath = Paths.get("database", resourceFile);
        System.out.println("Loading SQL file: " + sqlFilePath.toAbsolutePath());

        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(sqlFilePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();

                // skip empty lines and comments
                if (trimmed.isEmpty() || trimmed.startsWith("--") || trimmed.startsWith("/*")) {
                    continue;
                }

                sb.append(line).append("\n");

                // when we hit a ';', we consider that end of a statement
                if (trimmed.endsWith(";")) {
                    String sql = sb.toString().trim();
                    sb.setLength(0); // reset buffer

                    // remove trailing ';'
                    if (sql.endsWith(";")) {
                        sql = sql.substring(0, sql.length() - 1);
                    }

                    if (!sql.isEmpty()) {
                        System.out.println("Executing SQL:\n" + sql);
                        try (Statement st = conn.createStatement()) {
                            st.execute(sql);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading SQL file: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}