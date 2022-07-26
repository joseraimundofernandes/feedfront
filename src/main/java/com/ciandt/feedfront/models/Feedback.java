package com.ciandt.feedfront.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Feedback implements Serializable {

    private String id;
    public Feedback(LocalDate localDate, Employee autor, Employee proprietario, String lorem_ipsum_feedback) {
    }

    public String getId() {
        return id;
    }
}
