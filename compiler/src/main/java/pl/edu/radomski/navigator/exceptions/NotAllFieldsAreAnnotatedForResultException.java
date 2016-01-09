package pl.edu.radomski.navigator.exceptions;

public class NotAllFieldsAreAnnotatedForResultException extends RuntimeException {
    public NotAllFieldsAreAnnotatedForResultException(String message) {
        super(message);
    }
}
