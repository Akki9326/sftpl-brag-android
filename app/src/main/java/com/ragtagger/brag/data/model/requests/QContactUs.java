package com.ragtagger.brag.data.model.requests;

/**
 * Created by alpesh.rathod on 3/15/2018.
 */

public class QContactUs {

    private String name;
    private String email;
    private String message;

    public QContactUs(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }
}
