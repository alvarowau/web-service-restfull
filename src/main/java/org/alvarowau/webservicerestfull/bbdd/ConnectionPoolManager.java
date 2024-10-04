package org.alvarowau.webservicerestfull.bbdd;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionPoolManager {

    private static final Logger logger = Logger.getLogger(ConnectionPoolManager.class.getName());
    private static BasicDataSource dataSource;

    // Constructor privado para evitar instanciación
    private ConnectionPoolManager() {
        throw new ConnectionPoolException("No se puede instanciar esta clase.");
    }

    static {
        try {
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3307/crud");
            dataSource.setUsername("root");
            dataSource.setPassword("rootroot");
            dataSource.setMaxIdle(3);
            dataSource.setMaxWait(Duration.ofSeconds(5));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al inicializar el pool de conexiones", e);
            throw new ConnectionPoolException("No se pudo inicializar el pool de conexiones", e);
        }
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener una conexión del pool", e);
            throw new ConnectionPoolException("Error al obtener una conexión del pool", e);
        }
    }

    public static void shutdown() {
        try {
            if (dataSource != null) {
                dataSource.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al cerrar el pool de conexiones", e);
        }
    }
}
