package tn.uma.isamm.exceptions;


public class EntityConflictException extends RuntimeException {
    public EntityConflictException(String message) {
        super(message);
    }
}