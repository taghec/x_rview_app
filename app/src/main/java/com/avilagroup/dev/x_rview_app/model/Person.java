package com.avilagroup.dev.x_rview_app.model;

/**
 * Created by temp on 20/09/2016.
 */

public class Person
        implements HumanInterface {
    private String gender = "female";
    private int age = 0;
    private String firstname = "firstName";
    private String lastname = "lastName";

    public Person(String firstname, String lastname, int age, String gender) {
        this.gender = gender;
        this.age = age;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void setGender(String gender) {
        this.gender = gender;
    }
}
