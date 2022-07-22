package com.alkemy.disney.exception;

public enum ErrorEnum {

    ID_MOVIE_NOT_VALID("Id movie not valid"),
    ID_CHARACTER_NOT_VALID("Id character not valid"),
    ID_GENRE_NOT_VALID("Id genre not valid"),
    USER_ALREADY_EXIST("User already exist"),
    USER_OR_PASSWORD_INCORRECT("Incorrect username or password"),
    USER_OR_PASSWORD_NOT_FOUND("Username or password not found"),
    PARAM_NOT_FOUND("Param not found"),
    ERROR_TO_SEND_EMAIL("Error trying to send the email");

    private final String message;

    ErrorEnum(String message){this.message = message;}


    public String getMessage() {return this.message;}
}
