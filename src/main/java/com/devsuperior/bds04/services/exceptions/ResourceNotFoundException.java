package com.devsuperior.bds04.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg) {
        //Super method passes the message to the superclass constructor
        super(msg);
    }

}
