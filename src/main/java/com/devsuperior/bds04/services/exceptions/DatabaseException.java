package com.devsuperior.bds04.services.exceptions;

public class DatabaseException extends RuntimeException {


    public DatabaseException(String msg) {
        //Super method passes the message to the superclass constructor
        super(msg);
    }

}
