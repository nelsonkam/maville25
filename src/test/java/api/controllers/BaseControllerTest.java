package api.controllers;

import api.DatabaseManager;
import api.MaVilleServer;
import io.javalin.Javalin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;

/**
 * Cette classe abstraite fournit des configurations de base pour les tests des contrôleurs.
 */
public abstract class BaseControllerTest {
    /**
     * Configure l'environnement de test avant tous les tests.
     */
    @BeforeAll
    public static void setUp() {
        System.setProperty("test.database", "true");
    }

    /**
     * Nettoie l'environnement de test après tous les tests.
     */
    @AfterAll
    public static void tearDown() {
        System.clearProperty("test.database");
    }

    /**
     * Vide les tables de la base de données avant chaque test.
     */
    @BeforeEach
    public void clearDatabase() {
        DatabaseManager.getInstance().clearTables();
    }

    /**
     * Crée une instance de l'application Javalin pour les tests.
     *
     * @return L'application Javalin configurée pour les tests.
     */
    Javalin createTestApp() {
        return MaVilleServer.createApp();
    }
}