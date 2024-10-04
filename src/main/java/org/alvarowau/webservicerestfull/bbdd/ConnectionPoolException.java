package org.alvarowau.webservicerestfull.bbdd;

// Excepción personalizada para problemas con el pool de conexiones
class ConnectionPoolException extends RuntimeException {
    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(String message) {
        super(message);
    }
}
