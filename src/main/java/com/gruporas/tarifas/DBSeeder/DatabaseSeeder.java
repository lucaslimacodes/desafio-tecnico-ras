package com.gruporas.tarifas.DBSeeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseSeeder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("seed.sql");

        if (is == null) {
            throw new IllegalStateException("seed.sql não encontrada");
        }

        String sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        for (String statement : sql.split(";")) {
            if (!statement.trim().isEmpty()) {
                jdbcTemplate.execute(statement);
            }
        }
    }
}
