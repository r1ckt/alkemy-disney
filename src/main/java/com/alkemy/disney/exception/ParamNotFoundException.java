package com.alkemy.disney.exception;

public class ParamNotFoundException extends RuntimeException {
    public ParamNotFoundException(String msg) {
        super(msg);
    }
}
