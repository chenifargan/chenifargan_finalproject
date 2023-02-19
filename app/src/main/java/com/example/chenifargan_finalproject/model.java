package com.example.chenifargan_finalproject;

import java.util.ArrayList;


public class model {
    private String phonenumber;
    private ArrayList <String> name;
    private ArrayList<person> personArrayList;

    public model(String phonenumber) {
        this.phonenumber = phonenumber;
      //  this.name = new ArrayList<String>();
        this.personArrayList =  new ArrayList<person>();


    }
    public ArrayList<person> addobject(String nameOfPerson, String nameOfContext){
        personArrayList.add(new person(nameOfContext,nameOfPerson));
        return  personArrayList;

    }

    public ArrayList<person> getPersonArrayList() {
        return personArrayList;
    }

    public model setPersonArrayList(ArrayList<person> personArrayList) {
        this.personArrayList = personArrayList;
        return this;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public model setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
        return this;
    }

    public model setName(ArrayList<String> name) {
        this.name = name;
        return this;
    }
    public  ArrayList <String> setNametoArry(String n){
        name.add(n);
        return name;
    }

    public model setPerson(ArrayList<person> arr) {
        this.personArrayList = arr;
        return this;
    }


}
