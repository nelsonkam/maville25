package api.controllers;

import api.DatabaseManager;
import api.MaVilleServer;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;

public abstract class BaseControllerTest {
    @BeforeAll
    public void setUp() {
        System.setProperty("test.database", "true");
    }

    @AfterAll
    public void tearDown() {
        System.clearProperty("test.database");
        File[] files = new File(".").listFiles((dir, name) -> name.startsWith("maville-test.db"));
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    @BeforeEach
    public void clearDatabase() {
        DatabaseManager.getInstance().clearTables();
    }

    Javalin createTestApp() {
        return MaVilleServer.createApp();
    }
}
