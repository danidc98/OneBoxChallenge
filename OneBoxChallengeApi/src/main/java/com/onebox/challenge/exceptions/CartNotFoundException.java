package com.onebox.challenge.exceptions;

public class CartNotFoundException extends Exception {

    public CartNotFoundException(String id){
        super(String.format("The cart with id %s doesn´t exist in the database." ,id));
    }
}

