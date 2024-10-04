package org.alvarowau.webservicerestfull.repository.exception;

public class PersonRepositoryException extends RuntimeException {
    public PersonRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
    public PersonRepositoryException(String message) {
        super(message);
    }
}
