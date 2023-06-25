package org.example.error;

public class IncorrectCollectionException extends RuntimeException{
    public IncorrectCollectionException(String message) {
        super(message);
    }
}
