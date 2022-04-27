package org.joksin.playsamples.database;

import lombok.extern.slf4j.Slf4j;
import play.db.Database;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Statement;

@Slf4j
@Singleton
public class DatabaseInitializer {

    private static final String INIT_SQL
            = "CREATE TABLE IF NOT EXISTS USER (\n"
            + "ID INT PRIMARY KEY,\n"
            + "USERNAME VARCHAR(40),\n"
            + "PASSWORD VARCHAR(40),\n"
            + "ADDRESS VARCHAR(50),\n"
            + "CITY VARCHAR(40)\n"
            + ");";

    @Inject
    public DatabaseInitializer(Database db) {
        log.info("Initializing database ...");
        db.withConnection(connection -> {
            log.info("Database.withConnection() block");
            try (Statement statement = connection.createStatement()) {
                statement.execute(INIT_SQL);
            }
        });
    }


}
