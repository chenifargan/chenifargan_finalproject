package com.example.chenifargan_finalproject;

public class person {
    private String nameOfPerson;
    private String nameOfContext;

    public person() {
    }

    public person(String nameOfContext, String nameOfPerson) {
        this.nameOfPerson = nameOfPerson;
        this.nameOfContext = nameOfContext;
    }

    public String getNameOfContext() {
        return nameOfContext;
    }

    public person setNameOfContext(String nameOfContext) {
        this.nameOfContext = nameOfContext;
        return this;
    }

    public String getNameOfPerson() {
        return nameOfPerson;
    }

    public person setNameOfPerson(String nameOfPerson) {
        this.nameOfPerson = nameOfPerson;
        return this;
    }
}
